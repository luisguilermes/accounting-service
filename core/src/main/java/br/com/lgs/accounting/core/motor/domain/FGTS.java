package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FGTS extends Desconto {
    public FGTS(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        if (funcionario.getSalario() == null || funcionario.getSalario().doubleValue() <= 0)
            return Collections.EMPTY_MAP;

        var map = new HashMap<String, BigDecimal>();
        map.put("FGTS", funcionario.getSalario().multiply(new BigDecimal("0.08"))
                .setScale(2, RoundingMode.HALF_UP));
        return map;
    }
}
