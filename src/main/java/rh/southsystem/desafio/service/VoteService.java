package rh.southsystem.desafio.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import rh.southsystem.desafio.config.ApplicationProperties;
import rh.southsystem.desafio.dto.CpfApiDTO;
import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.enums.CanVoteEnum;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.mappers.VoteMapper;
import rh.southsystem.desafio.model.Associate;
import rh.southsystem.desafio.model.Vote;
import rh.southsystem.desafio.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private VotingSessionService sessionService;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private ApplicationProperties appProps;

    public List<VoteDTO> list() {
        var modelList = voteRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VoteMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VoteDTO vote(VoteDTO newVoteDTO) throws MappedException {

        Vote newVote = VoteMapper.INSTANCE.fromDTO(newVoteDTO); // Transforming DTO in Entity
        newVote.setVotingSession(sessionService.getByID(newVoteDTO.getIdVotingSession()));
        try {
            newVote.setAssociate(associateService.getByCPF(newVoteDTO.getCpf()));
        } catch (EntityNotFoundException e) {
            Associate newAssociate = associateService.createAssociate(newVoteDTO.getCpf());
            newVote.setAssociate(newAssociate);
        }
        try {
            this.save(newVote);
        } catch (DataIntegrityViolationException e) {
            String message = String.format("There's already a vote for this associate (cpf = %s) in this session (id = %s)",
                                           newVote.getAssociate().getCpf(),
                                           newVote.getVotingSession().getId());
            throw new MappedException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VoteMapper.INSTANCE.fromEntity(newVote);
    }

    private void save(Vote newVote) throws MappedException {
        this.validateVote(newVote);
        voteRepo.save(newVote);
    }

    private void validateVote(Vote newVote) throws MappedException {
        // Entity should be ready to persist
        if (newVote.getVote() == null)
            throw new MappedException("Null value for vote; please, answer with one of these: ['SIM', 'NAO', '0', '1']",
                                      HttpStatus.BAD_REQUEST);
        if (newVote.getVotingSession().getEndSession().isBefore(Instant.now()))
            throw new MappedException(String.format("Voting Session is already closed (id = %s)",
                                                    newVote.getVotingSession().getId()), HttpStatus.GONE);

        this.validateCpfUsingAPI(newVote.getAssociate().getCpf());

    }

    // Check if CPF can vote using API
    private void validateCpfUsingAPI(String cpf) throws MappedException {
        String urlWithParameters = appProps.getCpfApiUrl().replace("{cpf}", cpf);

        try {
            WebClient webClient = WebClient.builder()
                                           .baseUrl(urlWithParameters)
                                           .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                                          MediaType.APPLICATION_JSON_VALUE)
                                           .build();
            CpfApiDTO result    = webClient.get()
                                           .retrieve()
                                           .onStatus(obtainedCode -> obtainedCode.equals(HttpStatus.NOT_FOUND),
                                                     request -> Mono.error(new MappedException(String.format("The CPF '%s' is invalid",
                                                                                                             cpf),
                                                                                               HttpStatus.BAD_REQUEST)))
                                           .bodyToMono(CpfApiDTO.class)
                                           .timeout(Duration.ofSeconds(appProps.getCpfApiTimeout()))
                                           .block();
            System.out.println(cpf + ": " + result.getStatus());
            // TODO: Replace prints with logger
            if (result.getStatus() != CanVoteEnum.ABLE_TO_VOTE)
                throw new MappedException(String.format("This associate (CPF = %s) is unable to vote.", cpf),
                                          HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            // Thrown by 'block()'
            var cause = Exceptions.unwrap(e);
            if (cause instanceof WebClientRequestException
                || cause instanceof TimeoutException)
                throw new MappedException(String.format("The CPF API (%s) is unavailable.",
                                                        urlWithParameters), HttpStatus.SERVICE_UNAVAILABLE);
            throw e;
        }
    }
}