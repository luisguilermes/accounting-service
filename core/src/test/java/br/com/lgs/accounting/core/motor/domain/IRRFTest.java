package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IRRFTest {
    private final Desconto desconto = new IRRF(null);

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioNegativo() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("-1045")).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioZerado() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.ZERO).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioAoRealizarCalculoAliquotaParaPrimeiraFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.valueOf(1903.98)).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarCalculoAliquotaParaSegundaFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.valueOf(2000)).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("IRRF")
                        && valorDesconto.get("IRRF")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 142.8);
    }

    @Test
    void deveRetornarCalculoAliquotaParaTerceiraFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.valueOf(3751.05)).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("IRRF")
                        && valorDesconto.get("IRRF")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 354.8);
    }

    @Test
    void deveRetornarCalculoAliquotaParaQuartaFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.valueOf(4664.68)).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("IRRF")
                        && valorDesconto.get("IRRF")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 636.13);
    }

    @Test
    void deveRetornarCalculoAliquotaValoresAcimadaDaFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(BigDecimal.valueOf(5000)).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("IRRF")
                        && valorDesconto.get("IRRF")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 839.36);
    }
}
