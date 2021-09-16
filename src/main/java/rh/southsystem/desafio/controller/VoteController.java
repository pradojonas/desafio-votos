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

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService service;

    @GetMapping
    public List<VoteDTO> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    VoteDTO vote(@RequestBody VoteDTO newVoteDTO) throws MappedException {
        return service.vote(newVoteDTO);
    }

}