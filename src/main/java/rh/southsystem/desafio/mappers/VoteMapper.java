package rh.southsystem.desafio.mappers;

import java.time.LocalDateTime;

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
    Vote fromDTO(VoteDTO aDto);

    @Mapping(source = "associate.cpf", target = "cpf")
    @Mapping(source = "votingSession.id", target = "idVotingSession")
    VoteDTO fromEntity(Vote a);

    @Named("now")
    public static LocalDateTime durationMinutesToDateTime(DecisionEnum dummyParameter) {
        return LocalDateTime.now();
    }
}
