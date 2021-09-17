package rh.southsystem.desafio.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.model.VotingSession;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

    @Query(value = "SELECT * FROM SESSION WHERE ID_AGENDA = ?1", nativeQuery = true)
    VotingSession findByAgendaId(Long idAgenda);

    @Query(value = "SELECT * FROM SESSION WHERE closed IS TRUE AND dt_end_session_UTC < ?1",
           nativeQuery = true)
    List<VotingSession> findExpiredOpenSessions(Instant instant);
}
