package rh.southsystem.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingSessionDTO {

    private Long id;
    
    private Long idAgenda;
    private Long minutesDuration = 1L;
    
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="JsonFormat.DEFAULT_TIMEZONE")
//    private LocalDateTime endSession = LocalDateTime.now().plusMinutes(1); // Default session lasts for one minute
}
