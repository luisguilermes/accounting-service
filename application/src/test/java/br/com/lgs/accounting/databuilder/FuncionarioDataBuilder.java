package br.com.lgs.accounting.databuilder;

import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public abstract class FuncionarioDataBuilder {

    public static Optional<Funcionario> getFuncionario() {
        return Optional.of(new Funcionario.Builder()
                .id(1L)
                .nome("Karl")
                .sobrenome("Drogo")
                .documento("72554847002")
                .setor("Logistica")
                .salario(BigDecimal.valueOf(5000))
                .dataAdmissao(LocalDate.now().minusMonths(2))
                .beneficio(new Beneficio.Builder().valeTransporte(true).planoDental(true).planoSaude(true).build())
                .build());
    }

    public static Optional<Funcionario> getFuncionarioAdmissao20210201() {
        return Optional.of(new Funcionario.Builder()
                .id(1L)
                .nome("Karl")
                .sobrenome("Drogo")
                .documento("72554847002")
                .setor("Logistica")
                .salario(BigDecimal.valueOf(5000))
                .dataAdmissao(LocalDate.of(2021, 2, 1))
                .beneficio(new Beneficio.Builder().valeTransporte(true).planoDental(true).planoSaude(true).build())
                .build());
    }

    public static Optional<Funcionario> getFuncionarioEmpty() {
        return Optional.empty();
    }
}
