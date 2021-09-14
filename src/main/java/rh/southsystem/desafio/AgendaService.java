package rh.southsystem.desafio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.common.base.Strings;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.repository.AgendaRepository;

@Service
public class AgendaService {

    @Autowired
    private ModelMapper      modelMapper;
    @Autowired
    private AgendaRepository agendaRepo;

    public List<AgendaDTO> list() {
        var modelList = agendaRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> modelMapper.map(entity, AgendaDTO.class))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public AgendaDTO add(@RequestBody AgendaDTO newAgendaDTO) {
        var newAgenda = modelMapper.map(newAgendaDTO, Agenda.class); // Transforming DTO in Entity
        this.validateAgenda(newAgenda);
        // TODO: Handle constraints errors
        agendaRepo.save(newAgenda);
        return modelMapper.map(newAgenda, AgendaDTO.class);
    }
    
    private void validateAgenda(Agenda newAgenda) {
        if (Strings.isNullOrEmpty(newAgenda.getDescription()))
            throw new IllegalArgumentException("Agenda requires a description.");
    }

}
