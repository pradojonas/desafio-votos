package rh.southsystem.desafio.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.model.Agenda;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(AgendaDTO.class, Agenda.class)
//                   .<String>addMapping(src -> src.getDescription(),
//                                       (dest, value) -> dest.setDescription(value));
        return modelMapper;
    }
}