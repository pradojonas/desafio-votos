package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.common.base.Strings;

import rh.southsystem.desafio.dto.AgendaDTO;
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

    public AgendaDTO add(AgendaDTO newAgendaDTO) {
        var newAgenda = AgendaMapper.INSTANCE.fromDTO(newAgendaDTO); // Transforming DTO in Entity
        this.validateAgenda(newAgenda);
        agendaRepo.save(newAgenda);
        return AgendaMapper.INSTANCE.fromEntity(newAgenda);
    }

    private void validateAgenda(Agenda newAgenda) {
        if (Strings.isNullOrEmpty(newAgenda.getDescription()))
            throw new IllegalArgumentException("Agenda requires a description.");
    }

    public Agenda getById(Long idAgenda) {
        if (idAgenda == null)
            throw new IllegalArgumentException("Null id for VotingSession.");

        var entity = agendaRepo.findById(idAgenda)
                               .orElseThrow(() -> new IllegalArgumentException("Find Agenda requires a valid value (id = "
                                                                               + idAgenda + ")"));
        return entity;
    }

}
