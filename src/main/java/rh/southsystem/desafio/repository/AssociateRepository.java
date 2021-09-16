package rh.southsystem.desafio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.model.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {

    Optional<Associate> findBycpf(String string);

}
