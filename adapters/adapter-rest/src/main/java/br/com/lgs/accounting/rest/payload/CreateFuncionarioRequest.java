package br.com.lgs.accounting.rest.payload;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateFuncionarioRequest {
    private String nome;
    private String sobrenome;
    private String documento;
    private String setor;
    private BigDecimal salario;
    @ApiModelProperty(notes="Formato data yyyy-MM-dd.")
    private LocalDate dataAdmissao;
    private Boolean planoSaude;
    private Boolean planoDental;
    private Boolean valeTransporte;

    public CreateFuncionarioRequest() {
    }

    public CreateFuncionarioRequest(String nome, String sobrenome, String documento, String setor,
                                    BigDecimal salario, LocalDate dataAdmissao, Boolean planoSaude, Boolean planoDental, Boolean valeTransporte) {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public void setPlanoSaude(Boolean planoSaude) {
        this.planoSaude = planoSaude;
    }

    public void setPlanoDental(Boolean planoDental) {
        this.planoDental = planoDental;
    }

    public void setValeTransporte(Boolean valeTransporte) {
        this.valeTransporte = valeTransporte;
    }
}
