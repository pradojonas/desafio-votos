package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.config.ApplicationProperties;
import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.exceptions.CustomException;
import rh.southsystem.desafio.mappers.VoteMapper;
import rh.southsystem.desafio.model.Associate;
import rh.southsystem.desafio.model.Vote;
import rh.southsystem.desafio.model.VotingSession;
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

    public VoteDTO vote(VoteDTO newVoteDTO) throws CustomException {

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
                                           newVote.getAssociate().getId(),
                                           newVote.getVotingSession().getId()).toString();
            throw new CustomException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return VoteMapper.INSTANCE.fromEntity(newVote);
    }

    private void save(Vote newVote) {
        this.validateVote(newVote);
        voteRepo.save(newVote);
    }

    private void validateVote(Vote newVote) {
        // Entity is ready to persist
        if (newVote.getVote() == null)
            throw new IllegalArgumentException("Property 'vote' can't be null; please, answer with one of: ['SIM', 'NAO', '0', '1']");

        // TODO: Check if VotingSession is open

        appProps.getCpfApiUrl();

        // TODO: Check if CPF has already voted

        // TODO: Check if CPF can vote using API

    }
}