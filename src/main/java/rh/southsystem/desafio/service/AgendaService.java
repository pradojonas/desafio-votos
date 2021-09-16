package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.exceptions.MappedException;
import rh.southsystem.desafio.mappers.AgendaMapper;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.repository.AgendaRepository;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepo;

    public List<AgendaDTO> list() {
        var modelList = agendaRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> AgendaMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public AgendaDTO add(AgendaDTO newAgendaDTO) throws MappedException {
        var newAgenda = AgendaMapper.INSTANCE.fromDTO(newAgendaDTO); // Transforming DTO in Entity
        this.validateAgenda(newAgenda);
        agendaRepo.save(newAgenda);
        return AgendaMapper.INSTANCE.fromEntity(newAgenda);
    }

    private void validateAgenda(Agenda newAgenda) throws MappedException {
        if (Strings.isNullOrEmpty(newAgenda.getDescription()))
            throw new MappedException("Agenda requires a description.", HttpStatus.BAD_REQUEST);
    }

    public Agenda getById(Long idAgenda) throws MappedException {
        if (idAgenda == null)
            throw new MappedException("Null id for VotingSession.", HttpStatus.BAD_REQUEST);

        var entity = agendaRepo.findById(idAgenda)
                               .orElseThrow(() -> new MappedException("Find Agenda requires a valid value (id = "
                                                                      + idAgenda + ")",
                                                                      HttpStatus.BAD_REQUEST));
        return entity;
    }

}
