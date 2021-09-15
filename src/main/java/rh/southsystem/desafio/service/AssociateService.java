package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.AssociateDTO;
import rh.southsystem.desafio.mappers.AssociateMapper;
import rh.southsystem.desafio.model.Associate;
import rh.southsystem.desafio.repository.AssociateRepository;

@Service
public class AssociateService {

    @Autowired
    private AssociateRepository associateRepo;

    public List<AssociateDTO> list() {
        var modelList = associateRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> AssociateMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public AssociateDTO add(AssociateDTO newAssociateDTO) {
        var newAssociate = AssociateMapper.INSTANCE.fromDTO(newAssociateDTO); // Transforming DTO in Entity
        associateRepo.save(newAssociate);
        return AssociateMapper.INSTANCE.fromEntity(newAssociate);
    }

    public Associate getByCPF(String CPF) {
        if (CPF == null)
            throw new IllegalArgumentException("Null CPF for Associate.");
        return associateRepo.findBycpf(CPF)
                            .orElseThrow(() -> new EntityNotFoundException("CPF " + CPF + " não encontrado"));
    }

    public Associate createAssociate(String cpf) {
        // TODO: Create CPF (Premise: we already know that its not in database)
        return null;
    }

}
