package br.com.lgs.accounting.core.motor.application;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.core.motor.domain.*;

import java.math.BigDecimal;
import java.util.Map;

public class CalculadoraDeDescontosService {
    private final Desconto desconto;

    public CalculadoraDeDescontosService() {
        this.desconto = new FGTS(
                new INSS(
                    new IRRF(
                        new PlanoDental(
                             new PlanoDeSaude(
                                 new ValeTransporte(null))))));
    }

    public Map<String, BigDecimal> calcular(Funcionario funcionario) {
        Map<String, BigDecimal> descontos = this.desconto.calcular(funcionario);
        return descontos;
    }

}
