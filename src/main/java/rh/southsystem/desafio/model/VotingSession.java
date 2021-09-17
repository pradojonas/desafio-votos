package rh.southsystem.desafio.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Session",
       uniqueConstraints = { @UniqueConstraint(name = "UniqueAgenda", columnNames = { "id_agenda" }) })
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dt_end_session_UTC", nullable = false)
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant endSession;
    
    @NotNull
    @Column(name = "closed")
    private Boolean closed;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private Agenda agenda;

}