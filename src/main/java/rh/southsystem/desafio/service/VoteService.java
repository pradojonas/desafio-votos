package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.mappers.VoteMapper;
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
            newVote.setAssociate(associateService.createAssociate(newVoteDTO.getCpf()));
        }

        this.validateVote(newVote);

        // TODO: Persist Vote

        return null;
    }

    private void validateVote(Vote newVote) {
        // Entity should be ready to persist
        if (newVote.getVote() == null)
            throw new IllegalArgumentException("Property 'vote' can't be null; please, answer with one of: ['SIM', 'NAO', '0', '1']");

    }

}
