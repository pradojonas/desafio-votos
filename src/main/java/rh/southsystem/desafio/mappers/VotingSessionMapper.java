package rh.southsystem.desafio.mappers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VotingSessionPostDTO;
import rh.southsystem.desafio.model.Agenda;
import rh.southsystem.desafio.model.VotingSession;

@Mapper(componentModel = "spring")
public interface VotingSessionMapper {
    VotingSessionMapper INSTANCE       = Mappers.getMapper(VotingSessionMapper.class);
    static final long   ROUND_DURATION = 60;

    @Mapping(target = "agenda", ignore = true) // Handled in Service Class
    @Mapping(source = "minutesDuration", target = "endSession", qualifiedByName = "setEndSession")
    VotingSession fromDTO(VotingSessionPostDTO sDto);

    @Named("setEndSession")
    public static Instant setEndSession(Long minutesDuration) {
        // Null case treated in VotingSessionService.validateVotingSession()
        var endSession = minutesDuration == null ? null : Instant.now().plusSeconds(minutesDuration * 60);
        return endSession;
    }

    @Mapping(source = "endSession", target = "minutesDuration", qualifiedByName = "instantToRemainingMinutes")
    @Mapping(source = "agenda", target = "idAgenda", qualifiedByName = "getIdAgenda")
    VotingSessionPostDTO fromEntity(VotingSession s);

    @Named("instantToRemainingMinutes")
    public static Long instantToRemainingMinutes(Instant sourceEndTime) {
        // Rounding up the remaining time
        return ChronoUnit.MINUTES.between(Instant.now(), sourceEndTime.plusSeconds(ROUND_DURATION));
    }

    @Named("getIdAgenda")
    public static Long getIdAgenda(Agenda sourceEntity) {
        return sourceEntity.getId();
    }

}
