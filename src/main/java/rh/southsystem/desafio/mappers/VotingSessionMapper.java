package rh.southsystem.desafio.mappers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.model.VotingSession;

@Mapper(imports = { LocalDateTime.class, ChronoUnit.class })
public interface VotingSessionMapper {

    VotingSessionMapper INSTANCE = Mappers.getMapper(VotingSessionMapper.class);

    @Mapping(target = "endSession",
             expression = "java( LocalDateTime.now().plusMinutes(sDto.getMinutesDuration()) )")
    VotingSession fromDTO(VotingSessionDTO sDto);

    @Mapping(target = "minutesDuration",
             expression = "java( ChronoUnit.MINUTES.between(LocalDateTime.now(), s.getEndSession()) )")
    VotingSessionDTO fromEntity(VotingSession s);

}
