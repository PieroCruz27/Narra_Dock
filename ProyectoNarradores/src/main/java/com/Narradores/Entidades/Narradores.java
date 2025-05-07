package com.Narradores.Entidades;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "narradores")
public class Narradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    private String experiencia;

    @Column(nullable = false)
    private BigDecimal precioPorMinuto;

 
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)  // Eliminar unique = true
    private Usuario usuario;

}