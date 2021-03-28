package br.com.lgs.accounting.core.contracheque.domain;

import java.math.BigDecimal;

public class Lancamento {
    private final TipoLancamento tipo;
    private final BigDecimal valor;
    private final String descricao;

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
}
