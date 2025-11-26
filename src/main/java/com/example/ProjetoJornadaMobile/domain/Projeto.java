package com.example.ProjetoJornadaMobile.domain;

import com.example.ProjetoJornadaMobile.domain.enums.PossuiMotorredutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projeto")
    private Long idProjeto;

    @Column(name = "nome_projeto")
    private String nomeProjeto;

    // ---------- CAMPOS QUE EXISTEM NO DTO ----------
    @Column(name = "potencia")
    private BigDecimal potenciaMotor; // DTO: potenciaMotor

    @Column(name = "rotacao")
    private BigDecimal rotacaoMotor; // DTO: rotacaoMotor

    @Column(name = "diametro")
    private BigDecimal diametro; // antes era diametroTambor → equalizado com DTO

    @Column(name = "reducao")
    private BigDecimal reducao; // DTO: reducao

    @Column(name = "rendimento")
    private BigDecimal rendimento; // DTO: rendimento

    @Column(name = "fator_de_servico")
    private BigDecimal fatorDeServico; // DTO: fatorDeServico

    @Enumerated(EnumType.STRING)
    @Column(name = "motor_redutor")
    private PossuiMotorredutor possuiMotorredutor; // DTO: possuiMotorredutor
    // ------------------------------------------------

    // ---------- DEMAIS CAMPOS QUE VOCÊ JÁ TINHA ----------
    @Column(name = "cnpj_cliente")
    private String cnpjCliente;

    @Column(name = "cpf_funcionario")
    private String cpfFuncionario;

    @Column(name = "fileiras")
    private Integer numeroFileiras;

    @Column(name = "altura")
    private BigDecimal alturaElevador;

    @Column(name = "densidade")
    private BigDecimal densidade;

    @Column(name = "capacidade")
    private BigDecimal capacidadeDesejada;

    @Column(name = "velocidade")
    private BigDecimal velocidadeElevador;

    @Column(name = "passo")
    private BigDecimal passoCorreia;

    @Column(name = "canecametro")
    private BigDecimal canecaMetro;

    @Column(name = "enchimento")
    private BigDecimal enchimento;

    @Column(name = "momentomaximo")
    private BigDecimal momentoMaximo;

    @Column(name = "modelocorreia")
    private String modeloCorreia;

    @Column(name = "fixacao")
    private String fixacaoAdequada;

    @Column(name = "quantidadecanecasuportada")
    private Integer quantidadeCanecaSuportada;

    @Column(name = "comprimentocorreia")
    private BigDecimal comprimentoCorreia;

    @Column(name = "potenciaemcv")
    private BigDecimal potenciaEmCv;

    @Column(name = "torque")
    private BigDecimal torque;

    @Column(name = "rotacaoexistente")
    private BigDecimal rotacaoExistente;

    @Column(name = "velocidadeexistente")
    private BigDecimal velocidadeExistente;

    @Column(name = "capacidadereal")
    private BigDecimal capacidadeReal;

    @Column(name = "potencianecessaria")
    private BigDecimal potenciaNecessaria;

    @ManyToOne
    @JoinColumn(name = "motoredutorrecomendado")
    private Motor motorRecomendado;

    @Column(name = "momento")
    private BigDecimal momento;

    @Column(name = "tracaonecessaria")
    private BigDecimal tracaoNecessaria;

    @Column(name = "tracao_selecionada")
    private BigDecimal tracaoSelecionada;

    @Column(name = "relacaotracao")
    private BigDecimal relacaoTracao;

    @Column(name = "volumecaneca")
    private Integer volumeCaneca;
}
