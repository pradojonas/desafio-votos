package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.mappers.VoteMapper;
import rh.southsystem.desafio.model.Associate;
import rh.southsystem.desafio.model.Vote;
import rh.southsystem.desafio.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private VotingSessionService voteService;

    @Autowired
    private AssociateService associateService;

    public List<VoteDTO> list() {
        var modelList = voteRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VoteMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VoteDTO vote(VoteDTO newVoteDTO) {

        // TODO: Check CPF using API

        Vote newVote = VoteMapper.INSTANCE.fromDTO(newVoteDTO); // Transforming DTO in Entity
        validateVote(newVote);

        newVote.setVotingSession(voteService.getByID(newVoteDTO.getIdVotingSession()));
        try {
            newVote.setAssociate(associateService.getByCPF(newVoteDTO.getCpf()));
        } catch (EntityNotFoundException e) {
            Associate newAssociate = associateService.createAssociate(newVoteDTO.getCpf());
            newVote.setAssociate(newAssociate);
        }

        this.validateVote(newVote);
        voteRepo.save(newVote);
        return VoteMapper.INSTANCE.fromEntity(newVote);
    }

    private void validateVote(Vote newVote) {
        // Entity is ready to persist
        if (newVote.getVote() == null)
            throw new IllegalArgumentException("Property 'vote' can't be null; please, answer with one of: ['SIM', 'NAO', '0', '1']");

        // TODO: Check if VotingSession is open

        // TODO: Check if CPF has already voted

    }
}