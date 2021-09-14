package rh.southsystem.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "Associate")
public class Associate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;
    
}