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
public class ProjetoComMotorredutorResponse {

    private BigDecimal rotacaoExistente;
    private BigDecimal velocidadeExistente;
    private BigDecimal capacidadeReal;
    private BigDecimal potenciaNecessaria;
    private BigDecimal momentoMaximo;
    private Motor motorRecomendado;
}