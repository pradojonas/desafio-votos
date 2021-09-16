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
import org.springframework.web.server.ResponseStatusException;

import rh.southsystem.desafio.dto.VotingSessionPostDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.service.VotingSessionService;

@RestController
@RequestMapping("/session")
public class VotingSessionController {

    @Autowired
    VotingSessionService service;

    @GetMapping
    public List<VotingSessionPostDTO> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VotingSessionPostDTO add(@RequestBody VotingSessionPostDTO newVotingSessionDTO) throws MappedException {
        return service.create(newVotingSessionDTO);
    }
}