package com.example.ProjetoJornadaMobile.dto.response;

import com.example.ProjetoJornadaMobile.domain.Motor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoSemMotorredutorResponse {

    private BigDecimal capacidadeReal;
    private BigDecimal velocidadeElevadorDesejada;
    private BigDecimal potenciaNecessaria;
    private BigDecimal potenciaEmCv;
    private BigDecimal momentoMaximo;
    private Motor motorRecomendado;
    private BigDecimal comprimentoCorreia;
    private Integer quantidadeCanecaSuportada;
}