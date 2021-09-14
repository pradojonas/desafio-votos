package rh.southsystem.desafio.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.model.VotingSession;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        Converter<Long, LocalDateTime> minutesToDateTimeConverter = ctx -> LocalDateTime.now()
                                                                                        .plusMinutes(ctx.getSource());
        Converter<LocalDateTime, Long> dateTimeToMinutesConverter = ctx -> ChronoUnit.MINUTES.between(LocalDateTime.now(),
                                                                                                      ctx.getSource());

        // TODO: Como implementar?
        // PropertyMap<VotingSession, VotingSessionDTO> dateTimeToMinutes = new PropertyMap<VotingSession, VotingSessionDTO>() {
        // protected void configure() {
        // map().setMinutesDuration();
        // }
        // };

        modelMapper
        .addMappings(new PropertyMap<VotingSessionDTO, VotingSession>() {
            protected void configure() {
                using(minutesToDateTimeConverter).map(source.getMinutesDuration(),
                                                      destination.getEndSession());
            }
        })
//        .addMappings(new PropertyMap<VotingSession, VotingSessionDTO>() {
//            protected void configure() {
//                using(dateTimeToMinutesConverter).map(source.getEndSession(),
//                                                      destination.getMinutesDuration());
//            }
//        })
        ;
        return modelMapper;
    }
}