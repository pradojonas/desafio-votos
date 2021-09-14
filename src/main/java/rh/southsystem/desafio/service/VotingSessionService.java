package rh.southsystem.desafio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.model.VotingSession;
import rh.southsystem.desafio.repository.AgendaRepository;
import rh.southsystem.desafio.repository.VotingSessionRepository;

@Service
public class VotingSessionService {

    @Autowired
    private ModelMapper      modelMapper;
    @Autowired
    private VotingSessionRepository sessionRepo;
    @Autowired
    private AgendaRepository agendaRepo;

    public List<VotingSession> list() {
        var modelList = sessionRepo.findAll();
//        var dtoList   = modelList.stream()
//                                 .map(entity -> modelMapper.map(entity, VotingSessionDTO.class))
//                                 .collect(Collectors.toList());
        return modelList;
    }

    public VotingSessionDTO add(@RequestBody VotingSessionDTO newVotingSessionDTO) {
        
        var agendaOpt = agendaRepo.findById(newVotingSessionDTO.getIdAgenda());
        if (!agendaOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid agenda for Voting Session.");
        }
        
        var newVotingSession = modelMapper.map(newVotingSessionDTO, VotingSession.class); // Transforming DTO in Entity
        newVotingSession.setAgenda(agendaOpt.get());
        this.validateVotingSession(newVotingSession);
        // TODO: Handle constraints errors
        sessionRepo.save(newVotingSession);
        return modelMapper.map(newVotingSession, VotingSessionDTO.class);
    }
    
    private void validateVotingSession(VotingSession newVotingSession) {
        if (newVotingSession.getAgenda() == null || newVotingSession.getAgenda().getId() == null)
            throw new IllegalArgumentException("VotingSession requires a valid Agenda.");
        if (newVotingSession.getEndSession() == null || newVotingSession.getEndSession().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("VotingSession requires a valid endSession.");
    }

}
