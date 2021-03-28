package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PlanoDental extends Desconto {
    public PlanoDental(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        var teste = new HashMap();
        teste.put("PlanoDental", new BigDecimal("10.0"));
        return teste;
    }
}
