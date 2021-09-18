package rh.southsystem.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.southsystem.desafio.dto.AgendaDTO;
import rh.southsystem.desafio.dto.AssociateDTO;
import rh.southsystem.desafio.service.AssociateService;

@RestController
@RequestMapping("/v1/associate")
public class AssociateController {

    @Autowired
    AssociateService service;

    @ApiOperation(value="Lists all Associates", response = AssociateDTO.class)
    @GetMapping
    public List<AssociateDTO> list() {
        return service.list();
    }
}