package rh.southsystem.desafio.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import rh.southsystem.desafio.enums.DecisionEnum;

@Data
@Entity
@Table(name = "Vote")
public class Vote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_associate", nullable = false)
    private Associate votingAssociate;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_session", nullable = false)
    private VotingSession votingSession;
    
    @NotNull
    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DecisionEnum vote;
    
    @NotNull
    @Column(name = "dt_vote", nullable = false)
    private LocalDateTime dateVote = LocalDateTime.now();
    
}