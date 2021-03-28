package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class Desconto {

    protected Desconto proximoDesconto;

    public Desconto(Desconto proximoDesconto) {
        this.proximoDesconto = proximoDesconto;
    }

    protected abstract Map<String, BigDecimal> realizarCalculo(Funcionario funcionario);

    public Map<String, BigDecimal> calcular(Funcionario funcionario) {
        Map<String, BigDecimal> descontos = new HashMap<>();
        Map<String, BigDecimal> desconto = realizarCalculo(funcionario);
        descontos.putAll(desconto);

        while(proximoDesconto != null) {
            Map<String, BigDecimal> opa = proximoDesconto.realizarCalculo(funcionario);
            descontos.putAll(opa);
            this.proximoDesconto = proximoDesconto.getProximoDesconto();
        }

        return descontos;
    }

    public Desconto getProximoDesconto() {
        return proximoDesconto;
    }
}
