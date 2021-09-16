package rh.southsystem.desafio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import rh.southsystem.desafio.exceptions.MappedException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { MappedException.class })
    @ResponseBody
    public ResponseEntity<?> handleMappedException(MappedException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatusCode());
    }
    
    @ExceptionHandler(value = { Throwable.class })
    @ResponseBody
    public ResponseEntity<?> handleUnmappedException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}