package br.com.lgs.accounting.core.motor.domain;

import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValeTransporteTest {

    private final Desconto desconto = new ValeTransporte(null);

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioNegativo() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().valeTransporte(true).build())
                .salario(new BigDecimal("-1045")).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioZerado() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().valeTransporte(true).build())
                .salario(BigDecimal.ZERO).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioQuandoFuncionarioNaoOptouPeloPlano() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().valeTransporte(false).build())
                .salario(BigDecimal.valueOf(2000)).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarVazioQuandoFuncionarioTemSalarioInferiorAoAbono() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().valeTransporte(true).build())
                .salario(BigDecimal.valueOf(1499)).build();
        assertTrue(desconto.calcular(funcionario).isEmpty());
    }

    @Test
    void deveRetornarDescontoQuandoFuncionarioOptarPeloPlano() {
        Funcionario funcionario = new Funcionario.Builder()
                .beneficio(new Beneficio.Builder().valeTransporte(true).build())
                .salario(BigDecimal.valueOf(2000)).build();
        Map<String, BigDecimal> valorDesconto = desconto.calcular(funcionario);
        assertTrue(
                valorDesconto.containsKey("Vale Transporte")
                        && valorDesconto.get("Vale Transporte")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 160);
    }
}
