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

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService service;

    @GetMapping
    public List<VoteDTO> list() {
        try {
            return service.list();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    VoteDTO vote(@RequestBody VoteDTO newVoteDTO) {
        try {
            return service.vote(newVoteDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }

}