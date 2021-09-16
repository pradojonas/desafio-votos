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

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.service.AgendaService;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    AgendaService service;

    @GetMapping
    public List<AgendaDTO> list() {
        try {
            return service.list();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AgendaDTO add(@RequestBody AgendaDTO newAgendaDTO) {
        try {
            return service.add(newAgendaDTO);
        } catch (IllegalArgumentException e) {
            // TODO: Use @RestControllerAdvice in all Controllers for Exception Handle (https://www.bezkoder.com/spring-boot-restcontrolleradvice/#RestControllerAdvice_with_ResponseEntity)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error");
        }
    }
}