package rh.southsystem.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.dto.AssociateDTO;
import rh.southsystem.desafio.exceptions.MappedException;
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

    public Associate getByCPF(String CPF) throws MappedException {
        if (CPF == null)
            throw new MappedException("Null CPF for Associate.", HttpStatus.BAD_REQUEST);
        return associateRepo.findBycpf(CPF)
                            .orElseThrow(() -> new EntityNotFoundException("CPF " + CPF + " não encontrado"));
    }

    public Associate createAssociate(String cpf) {
        var newAssociate = new Associate(cpf); 
        return associateRepo.save(newAssociate);
    }

}
