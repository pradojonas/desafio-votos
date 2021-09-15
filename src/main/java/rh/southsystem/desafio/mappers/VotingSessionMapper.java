package rh.southsystem.desafio.mappers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VotingSessionDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.model.VotingSession;

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
    public static Instant durationMinutesToDateTime(Long minutesDuration) {
        // TODO: Check if minutesDuration is negative
        return Instant.now().plusSeconds(60 * minutesDuration);
    }

    @Named("dateTimeToRemainingMinutes")
    public static Long dateTimeToRemainingMinutes(Instant endTime) {
        // Minus minutes to round up the remaining time
        return ChronoUnit.MINUTES.between(Instant.now().minusSeconds(60), endTime);
    }

    @Named("getIdAgenda")
    public static Long getIdAgenda(Agenda entity) {
        return entity.getId();
    }

}
