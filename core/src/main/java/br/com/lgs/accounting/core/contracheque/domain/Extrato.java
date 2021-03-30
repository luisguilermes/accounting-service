package br.com.lgs.accounting.core.contracheque.domain;

import java.util.List;

public class Extrato {
    private String referencia;
    private List<Lancamento> lancamentos;
    private double salarioBruto;
    private double totalDescontos;
    private double salarioLiquido;

    public Extrato() {}
    public Extrato(String referencia, List<Lancamento> lancamentos, double salarioBruto, double totalDescontos, double salarioLiquido) {
        this.referencia = referencia;
        this.lancamentos = lancamentos;
        this.salarioBruto = salarioBruto;
        this.totalDescontos = totalDescontos;
        this.salarioLiquido = salarioLiquido;
    }

    public String getReferencia() {
        return referencia;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public double getTotalDescontos() {
        return totalDescontos;
    }

    public double getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public void setTotalDescontos(double totalDescontos) {
        this.totalDescontos = totalDescontos;
    }

    public void setSalarioLiquido(double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }
}
