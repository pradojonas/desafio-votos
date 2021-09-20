package rh.southsystem.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.service.VotingSessionService;

@RestController
@RequestMapping("/v1/session")
public class VotingSessionController {

    @Autowired
    VotingSessionService service;

    @ApiOperation(value="Lists all Voting Sessions", response = VotingSessionDTO.class)
    @GetMapping
    List<VotingSessionDTO> list() {
        return service.list();
    }

    @ApiOperation(value="Creates a Voting Session for an Agenda", response = VotingSessionDTO.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VotingSessionDTO add(@RequestBody VotingSessionDTO newVotingSessionDTO) throws MappedException {
        return service.create(newVotingSessionDTO);
    }
}