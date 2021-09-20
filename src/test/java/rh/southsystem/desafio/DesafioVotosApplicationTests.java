package rh.southsystem.desafio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.enums.DecisionEnum;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "dev")
class DesafioVotosApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    final static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @Order(1)
    void createVoteAndDependencies() throws Exception {
        var newAgenda = new AgendaDTO();
        newAgenda.setDescription("Agenda created by SpringBootTest");
        // 1. Creating Agenda
        MvcResult requestResult = this.mockMvc.perform(post("/v1/agenda").content(asJsonString(newAgenda))
                                                                         .contentType(MediaType.APPLICATION_JSON)
                                                                         .accept(MediaType.APPLICATION_JSON))
                                              .andExpect(status().isCreated())
                                              .andReturn();
        AgendaDTO agendaDTO     = parseResponse(requestResult, AgendaDTO.class);
        assertNotNull(agendaDTO.getId());

        this.mockMvc.perform(get("/v1/agenda")).andExpect(status().isOk()).andReturn();

        var newSession = new VotingSessionDTO();
        newSession.setIdAgenda(agendaDTO.getId());
        newSession.setMinutesDuration(-1L);
        this.mockMvc.perform(post("/v1/session").content(asJsonString(newSession))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError()); // invalid minutesDuration

        // 2. Creating Voting Session
        newSession.setMinutesDuration(5L);
        requestResult = this.mockMvc.perform(post("/v1/session").content(asJsonString(newSession))
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .accept(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isCreated())
                                    .andReturn();

        VotingSessionDTO sessionDTO = parseResponse(requestResult, VotingSessionDTO.class);
        assertNotNull(sessionDTO.getId());

        // ERROR: Session already created for session
        this.mockMvc.perform(post("/v1/session").content(asJsonString(newSession))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());

        this.mockMvc.perform(get("/v1/session")).andExpect(status().isOk()).andReturn();

        var newVote = new VoteDTO();
        newVote.setIdVotingSession(0L);
        newVote.setCpf("00000000000");
        newVote.setVote(DecisionEnum.SIM);

        // ERROR: Invalid VotingSession
        this.mockMvc.perform(post("/v1/vote").content(asJsonString(newVote))
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        newVote.setIdVotingSession(sessionDTO.getId());
        // ERROR: Invalid CPF
        this.mockMvc.perform(post("/v1/vote").content(asJsonString(newVote))
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        // 3. Creating Vote [Expecting (201 - CREATED) OR (403 - Associate is UNABLE_TO_VOTE)]
        newVote.setCpf("05178471164"); // Valid CPF
        this.mockMvc.perform(post("/v1/vote").content(asJsonString(newVote))
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)).andDo(print());
    }

    public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
        try {
            String contentAsString = result.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, responseClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(final Object obj) {
        try {

            final String jsonContent = MAPPER.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
