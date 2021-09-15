package rh.southsystem.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
