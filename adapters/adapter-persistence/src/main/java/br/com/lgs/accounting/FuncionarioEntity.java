package br.com.lgs.accounting;


import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class FuncionarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String documento;
    private String setor;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private Boolean planoSaude;
    private Boolean planoDental;
    private Boolean valeTransporte;

    public FuncionarioEntity() { }

    private FuncionarioEntity(String nome, String sobrenome, String documento, String setor, BigDecimal salario,
                             LocalDate dataAdmissao, Boolean planoSaude, Boolean planoDental, Boolean valeTransporte) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.setor = setor;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.planoSaude = planoSaude;
        this.planoDental = planoDental;
        this.valeTransporte = valeTransporte;
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

    public Boolean getPlanoSaude() {
        return planoSaude;
    }

    public Boolean getPlanoDental() {
        return planoDental;
    }

    public Boolean getValeTransporte() {
        return valeTransporte;
    }

    public Funcionario toEntity() {
        return new Funcionario.Builder()
                .id(id)
                .nome(nome)
                .sobrenome(sobrenome)
                .documento(documento)
                .setor(setor)
                .salario(salario)
                .dataAdmissao(dataAdmissao)
                .beneficio(new Beneficio.Builder()
                        .planoSaude(planoSaude)
                        .planoDental(planoDental)
                        .valeTransporte(valeTransporte)
                        .build())
                .build();
    }
    
    public static class Builder {
        private String nome;
        private String sobrenome;
        private String documento;
        private String setor;
        private BigDecimal salario;
        private LocalDate dataAdmissao;
        private Boolean planoSaude;
        private Boolean planoDental;
        private Boolean valeTransporte;

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

        public Builder planoSaude(Boolean planoSaude) {
            this.planoSaude = planoSaude;
            return this;
        }

        public Builder planoDental(Boolean planoDental) {
            this.planoDental = planoDental;
            return this;
        }

        public Builder valeTransporte(Boolean valeTransporte) {
            this.valeTransporte = valeTransporte;
            return this;
        }

        public FuncionarioEntity build() {
            return new FuncionarioEntity(nome, sobrenome, documento, setor, salario, dataAdmissao,
                    planoSaude,  planoDental,  valeTransporte);
        }
    }

}
