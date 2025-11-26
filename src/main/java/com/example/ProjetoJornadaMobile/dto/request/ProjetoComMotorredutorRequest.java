package com.example.ProjetoJornadaMobile.dto.request;

import com.example.ProjetoJornadaMobile.domain.enums.PossuiMotorredutor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjetoComMotorredutorRequest {

    private String nomeProjeto;
    private BigDecimal potenciaMotor;
    private BigDecimal rotacaoMotor;
    private BigDecimal diametro;
    private BigDecimal reducao;
    private BigDecimal rendimento;
    private BigDecimal fatorDeServico;
    private BigDecimal densidade;
    private PossuiMotorredutor possuiMotorredutor;
}
