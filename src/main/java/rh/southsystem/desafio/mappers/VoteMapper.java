package rh.southsystem.desafio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.VoteDTO;
import rh.southsystem.desafio.model.Vote;

@Mapper()
public interface VoteMapper {
    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    Vote fromDTO(VoteDTO aDto);

    VoteDTO fromEntity(Vote a);
}
