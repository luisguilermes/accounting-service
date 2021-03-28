package br.com.lgs.accounting.core.contracheque.domain;

import br.com.lgs.accounting.core.motor.domain.Desconto;

import java.math.BigDecimal;

public class Lancamento {
    Desconto tipo;
    BigDecimal valor;
    String descricao;
}
