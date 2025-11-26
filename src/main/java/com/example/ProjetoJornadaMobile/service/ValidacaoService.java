package com.example.ProjetoJornadaMobile.service;

import com.example.ProjetoJornadaMobile.domain.Projeto;
import com.example.ProjetoJornadaMobile.exceptions.GenericException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class ValidacaoService {

    public void validacaoAltura(Projeto projeto) throws Exception {
        if (projeto.getAlturaElevador().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Altura inválida");
        }
    }

    public void validacaoDiametro(Projeto projeto) throws Exception {
        if (projeto.getDiametro().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Diametro inválido");
        }
    }

    public void validacaoPasso(Projeto projeto) throws Exception {
        if (projeto.getPassoCorreia().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Passo inválido");
        }
    }

    public void validacaoFileiras(Projeto projeto) throws Exception {
        if (projeto.getNumeroFileiras() < 1 || projeto.getNumeroFileiras() > 2) {
            throw new GenericException("Fileira inválida");
        }
    }

    public void validacaoVelocidadeCorreia(Projeto projeto) throws Exception {
        if (projeto.getVelocidadeElevador().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Velocidade da correia inválida");
        }
    }

    public void validacaoFatorEnchimento(Projeto projeto) throws Exception {
//        if (projeto.getEnchimento().compareTo(BigDecimal.ZERO) <= 0 || projeto.getEnchimento().compareTo(new BigDecimal("100")) > 0) {
//            throw new GenericException("Fator de enchimento inválido");
//        }
    }

    public void validacaoRendimento(Projeto projeto) throws Exception {
        if (projeto.getRendimento().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Rendimento inválido");
        }
    }

    public void validacaoRotacaoMotor(Projeto projeto) throws Exception {
        if (projeto.getRotacaoMotor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Rotação do motor inválida");
        }
    }

    public void validacaoReducao(Projeto projeto) throws Exception {
        if (projeto.getReducao().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException("Redução inválida");
        }
    }
}
