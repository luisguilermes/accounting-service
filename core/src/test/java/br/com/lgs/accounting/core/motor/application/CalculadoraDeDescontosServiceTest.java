package br.com.lgs.accounting.core.motor.application;


import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraDeDescontosServiceTest {

    @Test
    void quelrer() {
        Funcionario funcionario = new Funcionario.Builder()
                .id(1L)
                .nome("Jon")
                .sobrenome("Snow")
                .dataAdmissao(LocalDate.now())
                .salario(new BigDecimal("1000.0"))
                .documento("55366975018")
                .beneficio(new Beneficio.Builder()
                        .valeTransporte(true)
                        .planoDental(true)
                        .planoSaude(true)
                        .build())
                .build();
        CalculadoraDeDescontosService service = new CalculadoraDeDescontosService(funcionario);

        Map<String, BigDecimal> descontos = service.calcular();

        assertTrue(true);

    }
}
