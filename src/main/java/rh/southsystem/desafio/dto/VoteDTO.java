package rh.southsystem.desafio.dto;

import lombok.Getter;
import lombok.Setter;
import rh.southsystem.desafio.enums.DecisionEnum;

@Getter
@Setter
public class VoteDTO {

    private Long cpf;

    private Long idVotingSession;

    private DecisionEnum vote;
}
