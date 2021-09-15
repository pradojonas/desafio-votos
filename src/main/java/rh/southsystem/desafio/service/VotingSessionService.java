package rh.southsystem.desafio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.exceptions.DesafioException;
import rh.southsystem.desafio.mappers.VotingSessionMapper;
import rh.southsystem.desafio.model.VotingSession;
import rh.southsystem.desafio.repository.AgendaRepository;
import rh.southsystem.desafio.repository.VotingSessionRepository;

@Service
public class VotingSessionService {

    @Autowired
    private VotingSessionRepository sessionRepo;
    @Autowired
    private AgendaRepository        agendaRepo;

    public List<VotingSessionDTO> list() {
        var modelList = sessionRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VotingSessionMapper.INSTANCE.entityToPostDTO(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VotingSessionDTO add(@RequestBody VotingSessionDTO newVotingSessionDTO) throws DesafioException {

        VotingSession newVotingSession = VotingSessionMapper.INSTANCE.dtoToEntity(newVotingSessionDTO,
                                                                                  agendaRepo); // Transforming DTO in Entity
        this.validateVotingSession(newVotingSession);
        // TODO: Handle constraints errors
        try {
            sessionRepo.save(newVotingSession);
        } catch (DataIntegrityViolationException e) {
            VotingSession currentSession = sessionRepo.findByEmailAddress(newVotingSessionDTO.getIdAgenda());
            var           message        = "There's already a voting session for this agenda (sessionId = "
                                           + currentSession.getId() + ").";
            throw new DesafioException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VotingSessionMapper.INSTANCE.entityToPostDTO(newVotingSession);
    }

    private void validateVotingSession(VotingSession newVotingSession) {
        if (newVotingSession.getAgenda() == null
            || newVotingSession.getAgenda().getId() == null)
            throw new IllegalArgumentException("VotingSession requires a valid Agenda.");
        if (newVotingSession.getEndSession() == null
            || newVotingSession.getEndSession().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("VotingSession requires a valid endSession.");
    }

}
