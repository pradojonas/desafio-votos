package rh.southsystem.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.southsystem.desafio.model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}
