package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class INSSTest {

    private final Desconto desconto = new INSS(null);

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
    void deveRetornarCalculoAliquotaParaPrimeiraFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("1045")).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("INSS")
                        && valorDesconto.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 78.38);
    }

    @Test
    void deveRetornarCalculoAliquotaParaSegundaFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("2089.60")).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("INSS")
                        && valorDesconto.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 94.01);
    }

    @Test
    void deveRetornarCalculoAliquotaParaTerceiraFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("3134.40")).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("INSS")
                        && valorDesconto.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 125.38);
    }

    @Test
    void deveRetornarCalculoAliquotaParaQuartaFaixaSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("6101.06")).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("INSS")
                        && valorDesconto.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 415.33);
    }

    @Test
    void deveRetornarCalculoAliquotaParaFaixaAcimaDoTetoSalarial() {
        Funcionario funcionario = new Funcionario.Builder().salario(new BigDecimal("7000.00")).build();

        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);

        assertTrue(
                valorDesconto.containsKey("INSS")
                        && valorDesconto.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 713.10);
    }
}
