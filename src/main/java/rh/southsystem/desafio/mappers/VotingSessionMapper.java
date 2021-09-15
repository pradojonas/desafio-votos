package rh.southsystem.desafio.mappers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.model.VotingSession;
import rh.southsystem.desafio.repository.AgendaRepository;

@Mapper()
public interface VotingSessionMapper {
    VotingSessionMapper INSTANCE = Mappers.getMapper(VotingSessionMapper.class);

    @Mapping(source = "minutesDuration", target = "endSession", qualifiedByName = "durationMinutesToDateTime")
    VotingSession fromDTO(VotingSessionDTO sDto);

    @Mapping(source = "endSession",
             target = "minutesDuration",
             qualifiedByName = "dateTimeToRemainingMinutes")
    @Mapping(source = "agenda", target = "idAgenda", qualifiedByName = "getIdAgenda")
    VotingSessionDTO fromEntity(VotingSession s);

    @Named("durationMinutesToDateTime")
    public static LocalDateTime durationMinutesToDateTime(Long minutesDuration) {
        return LocalDateTime.now().plusMinutes(minutesDuration);
    }

    @Named("dateTimeToRemainingMinutes")
    public static Long dateTimeToRemainingMinutes(LocalDateTime endTime) {
        // Minus minutes to round up the remaining time
        return ChronoUnit.MINUTES.between(LocalDateTime.now().minusMinutes(1), endTime);
    }

    @Named("getIdAgenda")
    public static Long getIdAgenda(Agenda entity) {
        return entity.getId();
    }

}
