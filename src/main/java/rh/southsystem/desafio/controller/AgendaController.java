package rh.southsystem.desafio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.repository.AgendaRepository;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private ModelMapper      modelMapper;
    @Autowired
    private AgendaRepository agendaRepo;

    @GetMapping
    public List<AgendaDTO> list() {
        try {
            var modelList = agendaRepo.findAll();
            var dtoList   = modelList.stream()
                                     .map(entity -> modelMapper.map(entity, AgendaDTO.class))
                                     .collect(Collectors.toList());
            return dtoList;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AgendaDTO add(@RequestBody AgendaDTO newAgendaDTO) {
        try {
            // TODO: Handle input errors
            var newAgenda = modelMapper.map(newAgendaDTO, Agenda.class); // Transforming DTO in Entity
            // TODO: Handle constraints errors
            agendaRepo.save(newAgenda);
            return modelMapper.map(newAgenda, AgendaDTO.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generic error", e);
        }
    }

}