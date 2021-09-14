package rh.southsystem.desafio.controller;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AgendaDTO> list() {
        var modelList = agendaRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> modelMapper.map(entity, AgendaDTO.class))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    @PostMapping
    @ResponseStatus()
    AgendaDTO add(@RequestBody AgendaDTO newAgendaDTO) {
        var newAgenda = modelMapper.map(newAgendaDTO, Agenda.class); // Transforming DTO in Entity
        agendaRepo.save(newAgenda);
        return modelMapper.map(newAgenda, AgendaDTO.class);
    }

}