package rh.southsystem.desafio.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.config.ApplicationProperties;
import rh.southsystem.desafio.dto.VotingSessionPostDTO;
import rh.southsystem.desafio.exceptions.MappedException;
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

    public VotingSessionPostDTO create(VotingSessionPostDTO newVotingSessionDTO) throws MappedException {

        VotingSession newVotingSession = VotingSessionMapper.INSTANCE.fromDTO(newVotingSessionDTO); // Transforming DTO in Entity
        newVotingSession.setAgenda(agendaService.getById(newVotingSessionDTO.getIdAgenda()));
        try {
            this.validateVotingSession(newVotingSession);
            sessionRepo.save(newVotingSession);
        } catch (DataIntegrityViolationException e) {
            VotingSession currentSession = sessionRepo.findByAgendaId(newVotingSessionDTO.getIdAgenda());
            var           message        = "There's already a voting session for this agenda (sessionId = "
                                           + currentSession.getId() + ").";
            throw new MappedException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VotingSessionMapper.INSTANCE.fromEntity(newVotingSession);
    }

    private void validateVotingSession(VotingSession newVotingSession) throws MappedException {
        if (newVotingSession.getEndSession() == null)
            // Setting default duration if necessary
            newVotingSession.setEndSession(Instant.now().plusSeconds(appProps.getSessionDurationSeconds()));
        if (newVotingSession.getEndSession().isBefore(Instant.now()))
            throw new MappedException("VotingSession requires minutesDuration greater or equal 1.",
                                      HttpStatus.BAD_REQUEST);
    }

    public VotingSession getByID(Long idVotingSession) throws MappedException {
        if (idVotingSession == null)
            throw new MappedException("Null id for VotingSession.", HttpStatus.BAD_REQUEST);

        var entity = sessionRepo.findById(idVotingSession)
                                .orElseThrow(() -> new MappedException("Find VotingSession requires a valid value (id = "
                                                                       + idVotingSession + ").",
                                                                       HttpStatus.BAD_REQUEST));
        return entity;
    }

    // Runs every 30 seconds
    @Scheduled(cron = "*/30 * * * * *")
    private void closeVotingSessions() throws MappedException {
        System.out.println("Checking open sessions at " + Instant.now());
        var sessionsToClose = this.findExpiredOpenSessions();
        for (VotingSession votingSession : sessionsToClose) {
            votingSession.setClosed(true);
            System.out.println(String.format("Closing session (id = %s)", votingSession.getId()));
            sessionRepo.save(votingSession);
        }
    }

    private List<VotingSession> findExpiredOpenSessions() {
        return sessionRepo.findExpiredOpenSessions();
    }
}
