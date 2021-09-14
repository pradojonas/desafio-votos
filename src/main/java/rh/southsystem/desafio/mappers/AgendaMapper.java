package rh.southsystem.desafio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.model.Agenda;

@Mapper
public interface AgendaMapper {

    AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

    AgendaDTO fromEntity(Agenda a);

    Agenda fromDTO(AgendaDTO aDto);

}
