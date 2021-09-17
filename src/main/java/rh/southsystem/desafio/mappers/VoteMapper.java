package rh.southsystem.desafio.mappers;

import java.time.Instant;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.enums.DecisionEnum;
import rh.southsystem.desafio.model.Vote;

@Mapper()
public interface VoteMapper {
    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    @Mapping(source = "vote", target = "dateVote", qualifiedByName = "now")
    @Mapping(target = "associate", ignore = true) // Handled in Service Class
    @Mapping(target = "votingSession", ignore = true) // Handled in Service Class
    Vote fromDTO(VoteDTO aDto);

    @Mapping(source = "associate.cpf", target = "cpf")
    @Mapping(source = "votingSession.id", target = "idVotingSession")
    VoteDTO fromEntity(Vote a);

    @Named("now")
    public static Instant durationMinutesToDateTime(DecisionEnum dummyParameter) {
        return Instant.now();
    }

    List<VoteDTO> fromEntityList(List<Vote> votes);
}
