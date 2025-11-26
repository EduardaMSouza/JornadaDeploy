package com.example.ProjetoJornadaMobile.dto.request;

import com.example.ProjetoJornadaMobile.domain.enums.PossuiMotorredutor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjetoSemMotorredutorRequest {

    private String nomeProjeto;
    private BigDecimal alturaElevador;
    private BigDecimal densidade;
    private BigDecimal capacidadeDesejada;
    private BigDecimal velocidadeElevador;
    private BigDecimal passoCorreia;
    private Integer numeroFileiras;
    private BigDecimal enchimento;
    private PossuiMotorredutor possuiMotorRedutor;
    private BigDecimal diametroTambor;
    private Integer volumeCaneca;
    private BigDecimal rendimento;
    private BigDecimal fatorDeServico;
}
