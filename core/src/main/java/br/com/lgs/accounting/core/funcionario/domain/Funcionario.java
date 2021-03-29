package br.com.lgs.accounting.core.funcionario.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String documento;
    private String setor;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private Beneficio beneficio;

    private Funcionario(Long id, String nome, String sobrenome, String documento,
                        String setor, BigDecimal salario, LocalDate dataAdmissao, Beneficio beneficio) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.setor = setor;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.beneficio = beneficio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getSetor() {
        return setor;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String sobrenome;
        private String documento;
        private String setor;
        private BigDecimal salario;
        private LocalDate dataAdmissao;
        private Beneficio beneficio;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder sobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
            return this;
        }

        public Builder documento(String documento) {
            this.documento = documento;
            return this;
        }

        public Builder setor(String setor) {
            this.setor = setor;
            return this;
        }

        public Builder salario(BigDecimal salario) {
            this.salario = salario;
            return this;
        }

        public Builder dataAdmissao(LocalDate dataAdmissao) {
            this.dataAdmissao = dataAdmissao;
            return this;
        }

        public Builder beneficio(Beneficio beneficio) {
            this.beneficio = beneficio;
            return this;
        }

        public Funcionario build() {
            return new Funcionario(id, nome, sobrenome, documento, setor, salario, dataAdmissao, beneficio);
        }

    }
}
