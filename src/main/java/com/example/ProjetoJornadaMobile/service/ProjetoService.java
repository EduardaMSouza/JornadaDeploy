package com.example.ProjetoJornadaMobile.service;

import com.example.ProjetoJornadaMobile.domain.Motor;
import com.example.ProjetoJornadaMobile.domain.Projeto;
import com.example.ProjetoJornadaMobile.domain.enums.PossuiMotorredutor;
import com.example.ProjetoJornadaMobile.dto.request.ProjetoComMotorredutorRequest;
import com.example.ProjetoJornadaMobile.dto.request.ProjetoSemMotorredutorRequest;
import com.example.ProjetoJornadaMobile.dto.response.ProjetoComMotorredutorResponse;
import com.example.ProjetoJornadaMobile.dto.response.ProjetoSemMotorredutorResponse;
import com.example.ProjetoJornadaMobile.exceptions.GenericException;
import com.example.ProjetoJornadaMobile.repository.ProjetoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ObjectMapper objectMapper;
    private final CalculoService calculoService;
    private final PdfGeneratorService pdfGeneratorService;

    private ProjetoSemMotorredutorResponse mapearProjetoSemMotorRedutorResponse(Projeto projeto) {
        return new ProjetoSemMotorredutorResponse(
                projeto.getCapacidadeReal(),
                projeto.getVelocidadeElevador(),
                projeto.getPotenciaNecessaria(),
                projeto.getPotenciaEmCv(),
                projeto.getMomentoMaximo(),
                projeto.getMotorRecomendado(),
                projeto.getComprimentoCorreia(),
                projeto.getQuantidadeCanecaSuportada()
        );
    }
    public byte[] exportarPdfProjetoSemMotorredutor(Long id) throws Exception {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        ProjetoSemMotorredutorResponse response = mapearProjetoSemMotorRedutorResponse(projeto);
        return pdfGeneratorService.gerarPdfSemMotorredutor(response);
    }
    private ProjetoComMotorredutorResponse mapearProjetoComMotorredutorResponse(Projeto projeto) {
        return new ProjetoComMotorredutorResponse(
                projeto.getRotacaoExistente(),
                projeto.getVelocidadeExistente(),
                projeto.getCapacidadeReal(),
                projeto.getPotenciaNecessaria(),
                projeto.getMomentoMaximo(),
                projeto.getMotorRecomendado()
        );
    }

    public List<Projeto> buscarProjeto() {
        return projetoRepository.findAll();
    }

    public Projeto buscarProjetoPorId(Long id) throws Exception {
        verificarProjetoExistente(id);
        return projetoRepository.findById(id).get();
    }

    public void verificarProjetoExistente(Long id) throws Exception {
        if (!projetoRepository.existsById(id)) {
            throw new GenericException("Este projeto não existe");
        }
    }

    public ProjetoSemMotorredutorResponse criarProjetoSemMotorRedutor(ProjetoSemMotorredutorRequest projetoSemMotorredutorRequest) throws Exception {
        Projeto projeto = objectMapper.convertValue(projetoSemMotorredutorRequest, Projeto.class);

        if (projeto.getPossuiMotorredutor() == PossuiMotorredutor.NAO_POSSUI_MOTORREDUTOR) {
            BigDecimal comprimentoCorreia = calculoService.calcularComprimentoCorreia(projeto);
            BigDecimal quantidadeCanecas = calculoService.calcularQuantidadeCanecas(projeto);
            Integer quantidade = quantidadeCanecas.intValue();
            BigDecimal capacidadeCalculada = calculoService.calcularCapacidade(projeto);
            BigDecimal potenciaNecessaria = calculoService.calcularPotenciaNecessaria(projeto);
            BigDecimal potenciaEmCv = calculoService.calcularPotenciaEmCv(projeto);
            BigDecimal torque = calculoService.calcularTorque(projeto);
            BigDecimal momentoMaximo = calculoService.calcularMomentoMaximo(projeto);
            Motor motorRecomendado = calculoService.recomendarMotor(potenciaNecessaria);

            projeto.setQuantidadeCanecaSuportada(quantidade);
            projeto.setCapacidadeReal(capacidadeCalculada);
            projeto.setComprimentoCorreia(comprimentoCorreia);
            projeto.setPotenciaNecessaria(potenciaNecessaria);
            projeto.setPotenciaEmCv(potenciaEmCv);
            projeto.setTorque(torque);
            projeto.setMomentoMaximo(momentoMaximo);
            projeto.setMotorRecomendado(motorRecomendado);
        }

        Projeto projetoSalvo = projetoRepository.save(projeto);
        return mapearProjetoSemMotorRedutorResponse(projetoSalvo);
    }

    public ProjetoComMotorredutorResponse criarProjetoComMotorredutor(ProjetoComMotorredutorRequest projetoComMotorredutorRequest) throws Exception {
        Projeto projetoModel = objectMapper.convertValue(projetoComMotorredutorRequest, Projeto.class);

        if (projetoModel.getPossuiMotorredutor().equals(PossuiMotorredutor.POSSUI_MOTORREDUTOR)) {
            BigDecimal rotacaoExistente = calculoService.calcularRotacaoExistente(projetoModel);
            BigDecimal velocidadeExistente = calculoService.calcularVelocidadeExistente(projetoModel);
            BigDecimal capacidadeReal = calculoService.calcularCapacidadeReal(projetoModel);
            BigDecimal potenciaNecessaria = calculoService.calcularPotenciaNecessaria(projetoModel);
            BigDecimal momentoMaximo = calculoService.calcularMomentoMaximoExistente(projetoModel);
            Motor motorRecomendado = calculoService.calcularRecomendacaoMotor(projetoModel);
            BigDecimal comprimentoCorreia = calculoService.calcularComprimentoCorreia(projetoModel);
            BigDecimal quantidadeCanecas = calculoService.calcularQuantidadeCanecas(projetoModel);

            projetoModel.setRotacaoExistente(rotacaoExistente);
            projetoModel.setVelocidadeExistente(velocidadeExistente);
            projetoModel.setCapacidadeReal(capacidadeReal);
            projetoModel.setPotenciaNecessaria(potenciaNecessaria);
            projetoModel.setMomentoMaximo(momentoMaximo);
            projetoModel.setMotorRecomendado(motorRecomendado);
            projetoModel.setComprimentoCorreia(comprimentoCorreia);
            projetoModel.setQuantidadeCanecaSuportada(quantidadeCanecas.intValue());
        }

        Projeto projetoSalvo = projetoRepository.save(projetoModel);
        return mapearProjetoComMotorredutorResponse(projetoSalvo);
    }

    public void removerProjeto(Long id) throws Exception {
        verificarProjetoExistente(id);
        projetoRepository.deleteById(id);
    }

    public void atualizarProjetoComMotorredutor(Long id, ProjetoComMotorredutorRequest projetoComMotorredutorRequest) throws Exception {
        verificarProjetoExistente(id);
        Projeto projetoModel = objectMapper.convertValue(projetoComMotorredutorRequest, Projeto.class);
        projetoModel.setIdProjeto(id);
        projetoRepository.save(projetoModel);
    }

    public void atualizarProjetoSemMotorredutor(Long id, ProjetoSemMotorredutorRequest projetoSemMotorredutorRequest) throws Exception {
        verificarProjetoExistente(id);
        Projeto projetoModel = objectMapper.convertValue(projetoSemMotorredutorRequest, Projeto.class);
        projetoModel.setIdProjeto(id);
        projetoRepository.save(projetoModel);
    }
}