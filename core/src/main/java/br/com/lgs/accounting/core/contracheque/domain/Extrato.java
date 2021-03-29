package br.com.lgs.accounting.core.contracheque.domain;

import java.util.List;

public class Extrato {
    private final String referencia;
    private final List<Lancamento> lancamentos;
    private final double salarioBruto;
    private final double totalDescontos;
    private final double salarioLiquido;

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
}
