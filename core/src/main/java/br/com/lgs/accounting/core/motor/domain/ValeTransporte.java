package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ValeTransporte extends Desconto {
    public ValeTransporte(Desconto proximoDesconto) {
        super(proximoDesconto);
    }

    @Override
    protected Map<String, BigDecimal> realizarCalculo(Funcionario funcionario) {
        var teste = new HashMap();
        teste.put("ValeTransporte", new BigDecimal("10.0"));
        return teste;
    }
}
