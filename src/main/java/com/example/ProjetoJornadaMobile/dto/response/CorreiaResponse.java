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
public class CorreiaResponse {

    private BigDecimal tracao;
    private String modeloCorreia;
    private String fixacao;
    private BigDecimal quantidadeCanecasSuportada;
}
