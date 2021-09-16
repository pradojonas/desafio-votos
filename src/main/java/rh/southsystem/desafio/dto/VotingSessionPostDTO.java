package rh.southsystem.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingSessionPostDTO {

    private Long id;

    private Long idAgenda;
    private Long minutesDuration;
}
