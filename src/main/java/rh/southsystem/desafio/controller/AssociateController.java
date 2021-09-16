package rh.southsystem.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import rh.southsystem.desafio.dto.AssociateDTO;
import rh.southsystem.desafio.service.AssociateService;

@RestController
@RequestMapping("/associate")
public class AssociateController {

    @Autowired
    AssociateService service;

    @GetMapping
    public List<AssociateDTO> list() {
        return service.list();
    }
}