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

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.exceptions.DesafioException;
import rh.southsystem.desafio.service.VotingSessionService;

@RestController
@RequestMapping("/session")
public class VotingSessionController {

    @Autowired
    VotingSessionService service;

    @GetMapping
    public List<VotingSessionDTO> list() {
        try {
            return service.list();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VotingSessionDTO add(@RequestBody VotingSessionDTO newVotingSessionDTO) {
        try {
            return service.add(newVotingSessionDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DesafioException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }

}