package rh.southsystem.desafio.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import rh.southsystem.desafio.enums.DecisionEnum;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Vote",
       uniqueConstraints = { @UniqueConstraint(name = "UniqueVote",
                                               columnNames = { "id_session", "id_associate" }) })
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_associate", nullable = false)
    private Associate associate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session", nullable = false)
    private VotingSession votingSession;

    @NotNull
    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DecisionEnum vote;

    @NotNull
    @Column(name = "dt_vote", nullable = false)
    private Instant dateVote = Instant.now();

}