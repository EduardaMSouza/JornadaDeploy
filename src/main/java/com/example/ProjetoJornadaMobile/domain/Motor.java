package com.example.ProjetoJornadaMobile.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "motor")
public class Motor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "potencia_nominal")
    private BigDecimal potenciaNominal;

    @Column(name = "torque_maximo")
    private Integer torqueMaximo;

    @Column(name = "rotacao_de_saida")
    private Integer rotacaoSaida;

}
