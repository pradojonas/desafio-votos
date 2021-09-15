package rh.southsystem.desafio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import rh.southsystem.desafio.dto.AssociateDTO;
import rh.southsystem.desafio.model.Associate;

@Mapper
public interface AssociateMapper {

    AssociateMapper INSTANCE = Mappers.getMapper(AssociateMapper.class);

    AssociateDTO fromEntity(Associate a);

    Associate fromDTO(AssociateDTO aDto);

}
