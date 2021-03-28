package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValeTransporte extends Desconto {
    private static final double VALOR_INSENCAO = 1500;

    public ValeTransporte(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        if (funcionario.getSalario() == null ||
                funcionario.getSalario().doubleValue() <= 0 ||
                !funcionario.getBeneficio().getValeTransporte() ||
                funcionario.getSalario().doubleValue()  < VALOR_INSENCAO
        )
            return Collections.EMPTY_MAP;

        var map = new HashMap<String, BigDecimal>();
        map.put("Vale Transporte", funcionario.getSalario().multiply(new BigDecimal("0.08"))
                .setScale(2, RoundingMode.HALF_UP));
        return map;
    }

}
