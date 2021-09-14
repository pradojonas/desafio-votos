package rh.southsystem.desafio.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Session")
public class VotingSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Column(name = "dt_end_session_UTC", nullable = false)
    private LocalDateTime endSession = LocalDateTime.now().plusMinutes(1); // Default session lasts for one minute
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_agenda", nullable = false)
    private Agenda agenda;
    
}