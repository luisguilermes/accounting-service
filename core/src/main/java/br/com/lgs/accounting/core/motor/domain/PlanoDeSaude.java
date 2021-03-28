package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlanoDeSaude extends Desconto {

    private static final double VALOR = 10;

    public PlanoDeSaude(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        if (funcionario.getSalario() == null ||
                funcionario.getSalario().doubleValue() <= 0 ||
                !funcionario.getBeneficio().getPlanoSaude())
            return Collections.EMPTY_MAP;

        var map = new HashMap<String, BigDecimal>();
        map.put("Plano De Saúde", BigDecimal.valueOf(VALOR));
        return map;
    }
}
