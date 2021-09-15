package rh.southsystem.desafio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.exceptions.DesafioException;
import rh.southsystem.desafio.mappers.VoteMapper;
import rh.southsystem.desafio.model.Vote;
import rh.southsystem.desafio.repository.AgendaRepository;
import rh.southsystem.desafio.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository   voteRepo;
    @Autowired
    private AgendaRepository agendaRepo;

    public List<VoteDTO> list() {
        var modelList = voteRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VoteMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public VoteDTO vote(VoteDTO newVoteDTO) throws DesafioException {
        return newVoteDTO;
    }

    private void validateVote(Vote newVote) {
        // TODO: Check if session is open
        // throw new IllegalArgumentException("Vote requires a valid endSession.");
    }

}
