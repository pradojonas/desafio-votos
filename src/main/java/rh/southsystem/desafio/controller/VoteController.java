package rh.southsystem.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.dto.VotingSessionPostDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.service.VoteService;

@RestController
@RequestMapping("/v1/vote")
public class VoteController {

    @Autowired
    VoteService service;

    @ApiOperation(value="Lists all Votes", response = VoteDTO.class)
    @GetMapping
    public List<VoteDTO> list() {
        return service.list();
    }
    
    @ApiOperation(value="Lists all Votes for a specific Voting Session", response = VoteDTO.class)
    @GetMapping("session/{idSession}")
    public List<VoteDTO> listByVotingSession(@PathVariable long idSession) {
        return service.listByVotingSession(idSession);
    }

    @ApiOperation(value="Registers a vote in a Voting Session from an Associate", response = VoteDTO.class)
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    VoteDTO vote(@RequestBody VoteDTO newVoteDTO) throws MappedException {
        return service.vote(newVoteDTO);
    }

}