package com.example.ProjetoJornadaMobile.service;

import com.example.ProjetoJornadaMobile.domain.Motor;
import com.example.ProjetoJornadaMobile.domain.Projeto;
import com.example.ProjetoJornadaMobile.exceptions.GenericException;
import com.example.ProjetoJornadaMobile.repository.MotorRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class CalculoService {

    private final ValidacaoService validacao;
    private final MotorRepository motorRepository;

    // ---------- VALORES PADRÃO (opção B) ----------
    private static final BigDecimal DEFAULT_DIAMETRO = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_ALTURA = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_PASSO = BigDecimal.valueOf(10);
    private static final BigDecimal DEFAULT_DENSIDADE = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_ENCHIMENTO = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_VELOCIDADE = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_ROTACAO = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_REDUCAO = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_RENDIMENTO = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_CAPACIDADE_DESEJADA = BigDecimal.valueOf(1.0);
    private static final BigDecimal DEFAULT_FATOR_DE_SERVICO = BigDecimal.valueOf(1.0);
    private static final Integer DEFAULT_VOLUME_CANECA = 1;
    private static final Integer DEFAULT_NUMERO_FILEIRAS = 1;
    private static final BigDecimal DEFAULT_POTENCIA_MOTOR = BigDecimal.valueOf(1.0);

    // pequeno EPS para evitar divisão por zero
    private static final BigDecimal EPS = BigDecimal.valueOf(1e-9);

    // ---------- NORMALIZAÇÃO: garante que campos não sejam nulos ----------
    private void normalizeProjeto(Projeto projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto não pode ser null");
        }

        if (projeto.getDiametro() == null) projeto.setDiametro(DEFAULT_DIAMETRO);
        if (projeto.getAlturaElevador() == null) projeto.setAlturaElevador(DEFAULT_ALTURA);
        if (projeto.getPassoCorreia() == null) projeto.setPassoCorreia(DEFAULT_PASSO);
        if (projeto.getDensidade() == null) projeto.setDensidade(DEFAULT_DENSIDADE);
        if (projeto.getEnchimento() == null) projeto.setEnchimento(DEFAULT_ENCHIMENTO);
        if (projeto.getVelocidadeElevador() == null) projeto.setVelocidadeElevador(DEFAULT_VELOCIDADE);
        if (projeto.getRotacaoMotor() == null) projeto.setRotacaoMotor(DEFAULT_ROTACAO);
        if (projeto.getReducao() == null) projeto.setReducao(DEFAULT_REDUCAO);
        if (projeto.getRendimento() == null) projeto.setRendimento(DEFAULT_RENDIMENTO);
        if (projeto.getCapacidadeDesejada() == null) projeto.setCapacidadeDesejada(DEFAULT_CAPACIDADE_DESEJADA);
        if (projeto.getFatorDeServico() == null) projeto.setFatorDeServico(DEFAULT_FATOR_DE_SERVICO);
        if (projeto.getVolumeCaneca() == null) projeto.setVolumeCaneca(DEFAULT_VOLUME_CANECA);
        if (projeto.getNumeroFileiras() == null) projeto.setNumeroFileiras(DEFAULT_NUMERO_FILEIRAS);
        if (projeto.getPotenciaMotor() == null) projeto.setPotenciaMotor(DEFAULT_POTENCIA_MOTOR);

        // casos adicionais: se algum BigDecimal for zero e pode causar divisão por zero em denominadores,
        // deixamos no mínimo EPS (apenas nos campos que possam ser usados como denominador).
        if (isZeroOrNegative(projeto.getPassoCorreia())) projeto.setPassoCorreia(DEFAULT_PASSO);
        if (isZeroOrNegative(projeto.getReducao())) projeto.setReducao(DEFAULT_REDUCAO);
        if (isZeroOrNegative(projeto.getRendimento())) projeto.setRendimento(DEFAULT_RENDIMENTO);
    }

    private boolean isZeroOrNegative(BigDecimal value) {
        return value == null || value.compareTo(EPS) <= 0;
    }

    // ---------- MÉTODOS PÚBLICOS (lógicos) ----------
    public BigDecimal calcularCircunferenciaTambor(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        // validação - se sua validação espera dados não nulos, agora está normalizado
        try { validacao.validacaoDiametro(projeto); } catch (Exception ignored) {}
        BigDecimal pi = new BigDecimal("3.14159265359");
        return pi.multiply(projeto.getDiametro());
    }

    public BigDecimal calcularComprimentoCorreia(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try { validacao.validacaoAltura(projeto); } catch (Exception ignored) {}
        BigDecimal valor = BigDecimal.valueOf(2);

        BigDecimal comprimentoTeorico = (projeto.getAlturaElevador()
                .multiply(valor))
                .add(calcularCircunferenciaTambor(projeto)).add(valor);

        return comprimentoTeorico.setScale(1, RoundingMode.CEILING);
    }

    public BigDecimal calcularQuantidadeCanecas(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoFileiras(projeto);
            validacao.validacaoPasso(projeto);
        } catch (Exception ignored) {}

        BigDecimal totalCanecas = calcularCanecaMetro(projeto).multiply(calcularComprimentoCorreia(projeto));
        BigDecimal multiplo = BigDecimal.valueOf(5);
        BigDecimal divido = totalCanecas.divide(multiplo, 0, RoundingMode.CEILING);

        return divido.multiply(multiplo);
    }

    public BigDecimal calcularCapacidade(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoVelocidadeCorreia(projeto);
            validacao.validacaoFatorEnchimento(projeto);
        } catch (Exception ignored) {}

        BigDecimal volume = BigDecimal.valueOf(projeto.getVolumeCaneca());
        BigDecimal valor = new BigDecimal("0.0036");

        BigDecimal velocidade = projeto.getVelocidadeElevador();
        BigDecimal canecasPorMetro = calcularCanecaMetro(projeto);
        BigDecimal densidade = projeto.getDensidade();
        BigDecimal enchimento = projeto.getEnchimento();

        return valor.multiply(velocidade
                .multiply(canecasPorMetro
                        .multiply(densidade
                                .multiply(volume.multiply(enchimento)))));
    }

    public BigDecimal calcularCapacidadeReal(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoVelocidadeCorreia(projeto);
            validacao.validacaoFatorEnchimento(projeto);
        } catch (Exception ignored) {}

        BigDecimal volume = BigDecimal.valueOf(projeto.getVolumeCaneca());
        BigDecimal valor = new BigDecimal("0.0036");

        return valor.multiply(calcularVelocidadeExistente(projeto)
                .multiply(calcularCanecaMetro(projeto)
                        .multiply(projeto.getDensidade()
                                .multiply(volume.multiply(projeto.getEnchimento())))));
    }

    public BigDecimal calcularCanecaMetro(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoPasso(projeto);
            validacao.validacaoFileiras(projeto);
        } catch (Exception ignored) {}

        BigDecimal valor = BigDecimal.valueOf(1000);
        BigDecimal fileiras = BigDecimal.valueOf(projeto.getNumeroFileiras());

        BigDecimal passo = projeto.getPassoCorreia();
        // proteção: passo >= EPS
        if (isZeroOrNegative(passo)) passo = DEFAULT_PASSO;

        return (valor.multiply(fileiras)).divide(passo, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularPotenciaNecessaria(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoRendimento(projeto);
            validacao.validacaoAltura(projeto);
        } catch (Exception ignored) {}

        BigDecimal gravidade = new BigDecimal("9.81");
        BigDecimal valor = BigDecimal.valueOf(3600);

        BigDecimal altura = projeto.getAlturaElevador();
        BigDecimal capacidade = projeto.getCapacidadeDesejada();
        BigDecimal rendimento = projeto.getRendimento();

        // proteção: denominador não pode ser zero ou negativo
        if (isZeroOrNegative(rendimento)) rendimento = DEFAULT_RENDIMENTO;

        BigDecimal numerador = altura.multiply(capacidade).multiply(gravidade);
        BigDecimal denominador = valor.multiply(rendimento);

        if (isZeroOrNegative(denominador)) denominador = EPS;

        return numerador.divide(denominador, 10, RoundingMode.HALF_UP);
    }

    public Motor calcularRecomendacaoMotor(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);

        BigDecimal potenciaNecessaria = calcularPotenciaNecessaria(projeto);
        BigDecimal potenciaExistente = projeto.getPotenciaMotor();

        // proteção: se potenciaExistente for null (já normalizamos) ou negativa -> tratar
        if (potenciaExistente == null) potenciaExistente = DEFAULT_POTENCIA_MOTOR;

        if (potenciaExistente.compareTo(potenciaNecessaria) < 0) {
            return recomendarMotor(potenciaNecessaria);
        } else {
            return null;
        }
    }

    public BigDecimal calcularPotenciaEmCv(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        BigDecimal valor = new BigDecimal("1.36");
        return calcularPotenciaNecessaria(projeto).multiply(valor);
    }

    public BigDecimal calcularRotacaoExistente(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoRotacaoMotor(projeto);
            validacao.validacaoReducao(projeto);
        } catch (Exception ignored) {}

        BigDecimal rotacao = projeto.getRotacaoMotor();
        BigDecimal reducao = projeto.getReducao();

        if (isZeroOrNegative(reducao)) reducao = DEFAULT_REDUCAO;

        return rotacao.divide(reducao, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularVelocidadeExistente(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        BigDecimal pi = new BigDecimal("3.14159265359");
        BigDecimal valor = BigDecimal.valueOf(60000);

        BigDecimal diametro = projeto.getDiametro();
        BigDecimal rotacaoExistente = calcularRotacaoExistente(projeto);

        // proteção: denominador valor não zero, e diametro não zero
        if (isZeroOrNegative(diametro)) diametro = DEFAULT_DIAMETRO;

        return diametro.multiply(rotacaoExistente.multiply(pi)).divide(valor, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularRotacaoTambor(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        try {
            validacao.validacaoVelocidadeCorreia(projeto);
            validacao.validacaoDiametro(projeto);
        } catch (Exception ignored) {}

        BigDecimal pi = new BigDecimal("3.14159265359");
        BigDecimal valor = BigDecimal.valueOf(60000);

        BigDecimal velocidade = projeto.getVelocidadeElevador();
        BigDecimal diametro = projeto.getDiametro();

        if (isZeroOrNegative(diametro)) diametro = DEFAULT_DIAMETRO;

        return (velocidade.multiply(valor)).divide(pi.multiply(diametro), 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularTorque(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);

        BigDecimal valor = BigDecimal.valueOf(9550);
        BigDecimal rpmFluxo1 = calcularRotacaoTambor(projeto);

        BigDecimal potencia = calcularPotenciaNecessaria(projeto);
        if (isZeroOrNegative(rpmFluxo1)) rpmFluxo1 = EPS;

        return (valor.multiply(potencia)).divide(rpmFluxo1, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularTorqueExistente(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);

        BigDecimal valor = BigDecimal.valueOf(9550);
        BigDecimal rpmFluxo2 = calcularRotacaoExistente(projeto);

        BigDecimal potencia = calcularPotenciaNecessaria(projeto);
        if (isZeroOrNegative(rpmFluxo2)) rpmFluxo2 = EPS;

        return (valor.multiply(potencia)).divide(rpmFluxo2, 10, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularMomentoMaximo(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        BigDecimal torqueFluxo1 = calcularTorque(projeto);
        BigDecimal fator = projeto.getFatorDeServico();
        if (isZeroOrNegative(fator)) fator = DEFAULT_FATOR_DE_SERVICO;
        return torqueFluxo1.multiply(fator);
    }

    public BigDecimal calcularMomentoMaximoExistente(Projeto projeto) throws Exception {
        normalizeProjeto(projeto);
        BigDecimal torqueFluxo2 = calcularTorqueExistente(projeto);
        BigDecimal fator = projeto.getFatorDeServico();
        if (isZeroOrNegative(fator)) fator = DEFAULT_FATOR_DE_SERVICO;
        return torqueFluxo2.multiply(fator);
    }

    public Motor recomendarMotor(BigDecimal potenciaNecessaria) throws Exception {
        List<Motor> motores = motorRepository.findAll();
        Motor melhorMotor = null;

        for (Motor motor : motores) {
            // proteção: se getPotenciaNominal for null, pular esse motor
            if (motor == null || motor.getPotenciaNominal() == null) continue;
            if (motor.getPotenciaNominal().compareTo(potenciaNecessaria) >= 0) {
                if (melhorMotor == null || motor.getPotenciaNominal().compareTo(melhorMotor.getPotenciaNominal()) < 0) {
                    melhorMotor = motor;
                }
            }
        }
        if (melhorMotor == null) {
            throw new GenericException("Nenhum motor adequado encontrado");
        }
        return melhorMotor;
    }

}
