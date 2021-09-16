package rh.southsystem.desafio.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.config.ApplicationProperties;
import rh.southsystem.desafio.dto.VotingSessionPostDTO;
import rh.southsystem.desafio.exceptions.CustomException;
import rh.southsystem.desafio.mappers.VotingSessionMapper;
import rh.southsystem.desafio.model.VotingSession;
import rh.southsystem.desafio.repository.VotingSessionRepository;

@Service
public class VotingSessionService {

    @Autowired
    private VotingSessionRepository sessionRepo;
    @Autowired
    private AgendaService           agendaService;
    @Autowired
    private ApplicationProperties   appProps;

    public List<VotingSessionPostDTO> list() {
        var modelList = sessionRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VotingSessionMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VotingSessionPostDTO create(VotingSessionPostDTO newVotingSessionDTO) throws CustomException {

        VotingSession newVotingSession = VotingSessionMapper.INSTANCE.fromDTO(newVotingSessionDTO); // Transforming DTO in Entity
        newVotingSession.setAgenda(agendaService.getById(newVotingSessionDTO.getIdAgenda()));
        try {
            this.save(newVotingSession);
        } catch (DataIntegrityViolationException e) {
            VotingSession currentSession = sessionRepo.findByAgendaId(newVotingSessionDTO.getIdAgenda());
            var           message        = "There's already a voting session for this agenda (sessionId = "
                                           + currentSession.getId() + ").";
            throw new CustomException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VotingSessionMapper.INSTANCE.fromEntity(newVotingSession);
    }

    private void save(VotingSession newVotingSession) {
        this.validateVotingSession(newVotingSession);
        // TODO: Handle constraints errors
        sessionRepo.save(newVotingSession);
    }

    private void validateVotingSession(VotingSession newVotingSession) {
        if (newVotingSession.getEndSession() == null)
            // Setting default duration if necessary
            newVotingSession.setEndSession(Instant.now().plusSeconds(appProps.getSessionDurationSeconds()));
        if (newVotingSession.getEndSession().isBefore(Instant.now()))
            throw new IllegalArgumentException("VotingSession requires a valid endSession.");
    }

    public VotingSession getByID(Long idVotingSession) {
        if (idVotingSession == null)
            throw new IllegalArgumentException("Null id for VotingSession.");

        var entity = sessionRepo.findById(idVotingSession)
                                .orElseThrow(() -> new IllegalArgumentException("Find VotingSession requires a valid value (id = "
                                                                                + idVotingSession + ")"));
        return entity;
    }

}
