package com.example.ProjetoJornadaMobile.controller;

import com.example.ProjetoJornadaMobile.domain.Projeto;
import com.example.ProjetoJornadaMobile.dto.request.ProjetoComMotorredutorRequest;
import com.example.ProjetoJornadaMobile.dto.request.ProjetoSemMotorredutorRequest;
import com.example.ProjetoJornadaMobile.dto.response.ProjetoComMotorredutorResponse;
import com.example.ProjetoJornadaMobile.dto.response.ProjetoSemMotorredutorResponse;
import com.example.ProjetoJornadaMobile.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/criar-projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    @GetMapping
    public List<Projeto> getProjeto() {
        return projetoService.buscarProjeto();
    }

    @GetMapping("/{id}/projeto")
    public Projeto buscarProjetoPorId(@PathVariable Long id) throws Exception {
        return projetoService.buscarProjetoPorId(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdfProjeto(@PathVariable Long id) throws Exception {
        byte[] pdfContent = projetoService.exportarPdfProjetoSemMotorredutor(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        String filename = "projeto_" + id + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/sem-motorredutor")
    public ResponseEntity<ProjetoSemMotorredutorResponse> criarProjetoSemMotorredutor(@RequestBody ProjetoSemMotorredutorRequest projetoSemMotorredutorRequest) throws Exception {
        ProjetoSemMotorredutorResponse response = projetoService.criarProjetoSemMotorRedutor(projetoSemMotorredutorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/com-motorredutor")
    public ResponseEntity<ProjetoComMotorredutorResponse> criarProjetoComMotorredutor(@RequestBody ProjetoComMotorredutorRequest projetoComMotorredutorRequest) throws Exception {
        ProjetoComMotorredutorResponse response = projetoService.criarProjetoComMotorredutor(projetoComMotorredutorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public void removerProjeto(@PathVariable Long id) throws Exception {
        projetoService.removerProjeto(id);
    }

    @PutMapping("/atualizar-sem-motorredutor/{id}")
    public void atualizarProjetoSemMotorredutor(@PathVariable Long id, @RequestBody ProjetoSemMotorredutorRequest projetoSemMotorredutorRequest) throws Exception {
        projetoService.atualizarProjetoSemMotorredutor(id, projetoSemMotorredutorRequest);
    }

    @PutMapping("/atualizar-com-motorredutor/{id}")
    public void atualizarProjetoComMotorredutor( @PathVariable Long id, @RequestBody ProjetoComMotorredutorRequest projetoComMotorredutorRequest) throws Exception {
        projetoService.atualizarProjetoComMotorredutor(id, projetoComMotorredutorRequest);
    }
}
