package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FGTSTest {
    private final Desconto desconto = new FGTS(null);

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioNegativo() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("-1045")).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarErroAoRealizarCalculoComSalarioZerado() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.ZERO).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarScala2() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("1045")).build();
        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);
        assertTrue(
                valorDesconto.containsKey("FGTS")
                        && valorDesconto.get("FGTS").doubleValue() == 83.60);
    }

    @Test
    void deveRetornarCalculoDoPercentualDeDesconto() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("1045")).build();
        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);
        assertTrue(
                valorDesconto.containsKey("FGTS")
                        && valorDesconto.get("FGTS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 83.6);
    }
}
