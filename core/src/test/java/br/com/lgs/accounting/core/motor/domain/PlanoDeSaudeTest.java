package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlanoDeSaudeTest {

    private final Desconto desconto = new PlanoDeSaude(null);

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioNegativo() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().planoSaude(true).build())
                .salario(new BigDecimal("-1045")).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioZerado() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().planoSaude(true).build())
                .salario(BigDecimal.ZERO).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioQuandoFuncionarioNaoOptouPeloPlano() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().planoSaude(false).build())
                .salario(BigDecimal.valueOf(1500)).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarDescontoQuandoFuncionarioOptarPeloPlano() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().planoSaude(true).build())
                .salario(BigDecimal.valueOf(1500)).build();
        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);
        assertTrue(
                valorDesconto.containsKey("Plano De Saúde")
                        && valorDesconto.get("Plano De Saúde")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 10);
    }
}
