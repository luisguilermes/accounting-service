package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IRRF extends Desconto {

    private static final double TETO_FAIXA_1 = 1903.98;
    private static final double TETO_FAIXA_2 = 2826.65;
    private static final double TETO_FAIXA_2_PARC_DEDUZIR = 142.8;
    private static final double TETO_FAIXA_3 = 3751.05;
    private static final double TETO_FAIXA_3_PARC_DEDUZIR = 354.8;
    private static final double TETO_FAIXA_4 = 4664.68;
    private static final double TETO_FAIXA_4_PARC_DEDUZIR = 636.13;
    private static final double TETO_FAIXA_5_PARC_DEDUZIR = 839.36;

    public IRRF(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        BigDecimal salario = funcionario.getSalario().setScale(2, RoundingMode.HALF_UP);
        BigDecimal desconto = BigDecimal.ZERO;

        if (funcionario.getSalario() == null ||
                funcionario.getSalario().doubleValue() <= 0 ||
                        salario.doubleValue() <= TETO_FAIXA_1)
            return Collections.EMPTY_MAP;

        if(salario.doubleValue() > TETO_FAIXA_1 && salario.doubleValue() <= TETO_FAIXA_2 ) {
            var calc = salario.multiply(new BigDecimal("0.075"))
                    .setScale(2, RoundingMode.HALF_UP);
            desconto = calc.doubleValue() > TETO_FAIXA_2_PARC_DEDUZIR ?
                    BigDecimal.valueOf(TETO_FAIXA_2_PARC_DEDUZIR) : calc;
        } else if(salario.doubleValue() > TETO_FAIXA_2 && salario.doubleValue() <= TETO_FAIXA_3 ) {
            var calc = salario.multiply(new BigDecimal("0.15"))
                    .setScale(2, RoundingMode.HALF_UP);
            desconto = calc.doubleValue() > TETO_FAIXA_3_PARC_DEDUZIR ?
                    BigDecimal.valueOf(TETO_FAIXA_3_PARC_DEDUZIR) : calc;
        } else if(salario.doubleValue() > TETO_FAIXA_3 && salario.doubleValue() <= TETO_FAIXA_4 ) {
            var calc = salario.multiply(new BigDecimal("0.225"))
                    .setScale(2, RoundingMode.HALF_UP);
            desconto = calc.doubleValue() > TETO_FAIXA_4_PARC_DEDUZIR ?
                    BigDecimal.valueOf(TETO_FAIXA_4_PARC_DEDUZIR) : calc;
        } else {
            var calc = salario.multiply(new BigDecimal("0.275"))
                    .setScale(2, RoundingMode.HALF_UP);
            desconto = calc.doubleValue() > TETO_FAIXA_5_PARC_DEDUZIR ?
                    BigDecimal.valueOf(TETO_FAIXA_5_PARC_DEDUZIR) : calc;
        }


        var map = new HashMap<String, BigDecimal>();
        map.put("IRRF", desconto);
        return map;
    }


}
