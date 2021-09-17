package rh.southsystem.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.enums.DecisionEnum;
import rh.southsystem.desafio.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.votingSession vs WHERE vs.id = :idVotingSession")
    List<Vote> findByVotindSession(@Param("idVotingSession") Long idVotingSession);

    @Query("SELECT v.vote FROM Vote v INNER JOIN v.votingSession vs WHERE vs.id = :idVotingSession")
    List<DecisionEnum> countVotesForSession(@Param("idVotingSession") Long idVotingSession);

}
