package rh.southsystem.desafio.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.repository.AgendaRepository;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepo;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Agenda> list() {
        return agendaRepo.findAll();
    }

    @PostMapping
    @ResponseStatus()
    Agenda add(@RequestBody AgendaDTO newAgendaDTO) {
        var newAgenda = modelMapper.map(newAgendaDTO, Agenda.class); // Transforming DTO in Entity
        return agendaRepo.save(newAgenda);
    }

}