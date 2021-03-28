package br.com.lgs.accounting.core.contracheque.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Extrato {
    private LocalDate referencia;
    private List<Lancamento> lancamentos;
    BigDecimal salarioBruto;
    BigDecimal descontos;
    BigDecimal salarioLiquido;
}
