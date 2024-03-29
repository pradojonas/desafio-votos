package rh.southsystem.desafio.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.enums.DecisionEnum;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.mappers.VoteMapper;
import rh.southsystem.desafio.model.Associate;
import rh.southsystem.desafio.model.Vote;
import rh.southsystem.desafio.repository.VoteRepository;
import rh.southsystem.desafio.service.external.CpfCheckerService;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private VotingSessionService sessionService;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private CpfCheckerService cpfApiService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<VoteDTO> list() {
        var modelList = voteRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> VoteMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public List<VoteDTO> listByVotingSession(Long idVotingSession) {
        List<Vote> votes = voteRepo.findByVotindSession(idVotingSession);
        return VoteMapper.INSTANCE.fromEntityList(votes);
    }

    public List<DecisionEnum> countVotesForSession(Long idVotingSession) {
        return voteRepo.countVotesForSession(idVotingSession);
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
        this.save(newVote);
        return VoteMapper.INSTANCE.fromEntity(newVote);
    }

    private void save(Vote newVote) throws MappedException {
        this.validateVote(newVote);
        try {
            voteRepo.save(newVote);
            LOGGER.info(String.format("Vote successfully registered for session %d.",
                                      newVote.getVotingSession().getId()));
        } catch (DataIntegrityViolationException e) {
            String message = String.format("There's already a vote for this associate (cpf = %s) in this session (id = %s).",
                                           newVote.getAssociate().getCpf(),
                                           newVote.getVotingSession().getId());
            throw new MappedException(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void validateVote(Vote newVote) throws MappedException {
        // Entity should be ready to persist
        if (newVote.getVote() == null)
            throw new MappedException("Null value for vote; please, answer with one of these: ['SIM', 'NAO', '0', '1'].",
                                      HttpStatus.BAD_REQUEST);
        if (newVote.getVotingSession().getEndSession().isBefore(Instant.now()))
            throw new MappedException(String.format("Voting Session is already closed (id = %s).",
                                                    newVote.getVotingSession().getId()), HttpStatus.GONE);

        cpfApiService.validateCpfUsingAPI(newVote.getAssociate().getCpf());
    }
}