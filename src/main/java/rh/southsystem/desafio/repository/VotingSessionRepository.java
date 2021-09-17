package rh.southsystem.desafio.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.model.VotingSession;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

    @Query(value = "SELECT * FROM SESSION WHERE ID_AGENDA = ?1", nativeQuery = true)
    VotingSession findByAgendaId(Long idAgenda);

    @Query(value = "SELECT vs FROM VotingSession vs WHERE vs.closed = :closed AND vs.endSession < :instant")
    List<VotingSession> findExpiredOpenSessions(@Param("closed") Boolean closed,
                                                @Param("instant") Instant instant);

    default List<VotingSession> findExpiredOpenSessions() {
        return this.findExpiredOpenSessions(false, Instant.now());
    }
}
