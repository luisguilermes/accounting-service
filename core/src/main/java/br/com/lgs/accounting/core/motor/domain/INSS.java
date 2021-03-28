package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.common.exception.BusinessRuleException;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class INSS extends Desconto {

    private static final double TETO_FAIXA_1 = 1045.0;
    private static final double TETO_FAIXA_2 = 2089.60;
    private static final double TETO_FAIXA_3 = 3134.40;
    private static final double TETO_FAIXA_4 = 6101.06;

    public INSS(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {

        if (funcionario.getSalario() == null || funcionario.getSalario().doubleValue() <= 0)
            return Collections.EMPTY_MAP;

        BigDecimal salario = funcionario.getSalario().setScale(2, RoundingMode.HALF_UP);
        BigDecimal desconto = BigDecimal.ZERO;
        if(salario.doubleValue() <= TETO_FAIXA_1)
            desconto = primeiraFaixa();
        else if(salario.doubleValue() > TETO_FAIXA_1 && salario.doubleValue() <= TETO_FAIXA_2)
            desconto = segundaFaixa();
        else if(salario.doubleValue() > TETO_FAIXA_2 && salario.doubleValue() <= TETO_FAIXA_3)
            desconto = terceiraFaixa();
        else if(salario.doubleValue() > TETO_FAIXA_3 && salario.doubleValue() <= TETO_FAIXA_4)
            desconto = quartaFaixa();
        else {
            desconto = acimaDoTeto();
        }

        var map = new HashMap<String, BigDecimal>();
        map.put("INSS", desconto);
        return map;
    }

    private BigDecimal primeiraFaixa() {
        return BigDecimal.valueOf(TETO_FAIXA_1).multiply(new BigDecimal("0.075"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal segundaFaixa() {
        return BigDecimal.valueOf(TETO_FAIXA_2 - TETO_FAIXA_1).multiply(new BigDecimal("0.09"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal terceiraFaixa() {
        return  BigDecimal.valueOf(TETO_FAIXA_3 - TETO_FAIXA_2).multiply(new BigDecimal("0.12"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal quartaFaixa() {
        return BigDecimal.valueOf(TETO_FAIXA_4 - TETO_FAIXA_3).multiply(new BigDecimal("0.14"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal acimaDoTeto() {
        return primeiraFaixa()
                .add(segundaFaixa())
                .add(terceiraFaixa())
                .add(quartaFaixa());
    }
}
