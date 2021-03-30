package br.com.lgs.accounting.core.contracheque.domain;

import java.math.BigDecimal;

public class Lancamento {
    private TipoLancamento tipo;
    private BigDecimal valor;
    private String descricao;

    public Lancamento() {}
    public Lancamento(TipoLancamento tipo, BigDecimal valor, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
