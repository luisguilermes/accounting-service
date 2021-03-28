package br.com.lgs.accounting.core.motor.application;


import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculadoraDeDescontosServiceTest {

    private CalculadoraDeDescontosService service;

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioNegativo() {
        Funcionario funcionario = new Funcionario.Builder()
                .salario(new BigDecimal("-1045"))
                .beneficio(new Beneficio.Builder()
                        .valeTransporte(true)
                        .planoDental(true)
                        .planoSaude(true)
                        .build())
                .build();
        service = new CalculadoraDeDescontosService(funcionario);
        assertTrue(service.calcular().isEmpty());
    }

    @Test
    void deveRetornarVazioAoRealizarCalculoComSalarioZerado() {
        Funcionario funcionario = new Funcionario.Builder()
                .salario(BigDecimal.ZERO)
                .beneficio(new Beneficio.Builder()
                        .valeTransporte(true)
                        .planoDental(true)
                        .planoSaude(true)
                        .build())
                .build();
        service = new CalculadoraDeDescontosService(funcionario);
        assertTrue(service.calcular().isEmpty());
    }

    @Test
    void deveRetornarDescontosQuandoFuncionarioTemSalario() {
        Funcionario funcionario = new Funcionario.Builder()
                .salario(BigDecimal.valueOf(4000))
                .beneficio(new Beneficio.Builder()
                        .valeTransporte(true)
                        .planoDental(true)
                        .planoSaude(true)
                        .build())
                .build();
        service = new CalculadoraDeDescontosService(funcionario);
        Map<String, BigDecimal> descontos = service.calcular();

        assertTrue(
                descontos.containsKey("FGTS")
                        && descontos.get("FGTS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 320);
        assertTrue(
                descontos.containsKey("INSS")
                        && descontos.get("INSS")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 415.33);

        assertTrue(
                descontos.containsKey("IRRF")
                        && descontos.get("IRRF")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 636.13);

        assertTrue(
                descontos.containsKey("Vale Transporte")
                        && descontos.get("Vale Transporte")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 320);
        assertTrue(
                descontos.containsKey("Plano De Saúde")
                        && descontos.get("Plano De Saúde")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 10);
        assertTrue(
                descontos.containsKey("Plano Dental")
                        && descontos.get("Plano Dental")
                        .setScale(2, RoundingMode.HALF_UP).doubleValue() == 5);
    }
}
