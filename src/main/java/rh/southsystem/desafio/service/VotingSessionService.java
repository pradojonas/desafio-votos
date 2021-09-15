package rh.southsystem.desafio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.VotingSessionDTO;
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

    public List<VotingSessionDTO> list() {
        var modelList = sessionRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VotingSessionMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VotingSessionDTO add(VotingSessionDTO newVotingSessionDTO) throws CustomException {

        VotingSession newVotingSession = VotingSessionMapper.INSTANCE.fromDTO(newVotingSessionDTO); // Transforming DTO in Entity
        newVotingSession.setAgenda(agendaService.getById(newVotingSessionDTO.getIdAgenda()));
        this.validateVotingSession(newVotingSession);
        // TODO: Handle constraints errors
        try {
            sessionRepo.save(newVotingSession);
        } catch (DataIntegrityViolationException e) {
            VotingSession currentSession = sessionRepo.findByAgendaId(newVotingSessionDTO.getIdAgenda());
            var           message        = "There's already a voting session for this agenda (sessionId = "
                                           + currentSession.getId() + ").";
            throw new CustomException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VotingSessionMapper.INSTANCE.fromEntity(newVotingSession);
    }

    private void validateVotingSession(VotingSession newVotingSession) {
        if (newVotingSession.getEndSession() == null
            || newVotingSession.getEndSession().isBefore(LocalDateTime.now()))
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
