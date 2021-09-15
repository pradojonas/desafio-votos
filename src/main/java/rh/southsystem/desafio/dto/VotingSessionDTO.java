package rh.southsystem.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingSessionDTO {

    private Long id;
    
    private Long idAgenda;
    private Long minutesDuration = 1L;
}
