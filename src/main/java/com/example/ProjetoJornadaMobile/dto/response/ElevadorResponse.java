package com.example.ProjetoJornadaMobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElevadorResponse {

    private BigDecimal quantidadeCanecasPorMetro;
    private BigDecimal capacidadeCalculada;
    private BigDecimal velocidadeElevador;
}
