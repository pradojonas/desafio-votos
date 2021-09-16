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

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.service.AgendaService;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    AgendaService service;

    @GetMapping
    public List<AgendaDTO> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AgendaDTO add(@RequestBody AgendaDTO newAgendaDTO) throws MappedException {
        return service.add(newAgendaDTO);
    }
}