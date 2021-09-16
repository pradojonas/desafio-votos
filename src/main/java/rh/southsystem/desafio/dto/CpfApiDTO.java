package rh.southsystem.desafio.dto;

import lombok.Getter;
import lombok.Setter;
import rh.southsystem.desafio.enums.CanVoteEnum;

@Getter
@Setter
public class CpfApiDTO {

    CanVoteEnum status;
}