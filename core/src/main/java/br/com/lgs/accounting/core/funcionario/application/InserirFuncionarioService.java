package br.com.lgs.accounting.core.funcionario.application;

import br.com.lgs.accounting.core.common.exception.AlreadExistsException;
import br.com.lgs.accounting.core.common.exception.BusinessRuleException;
import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.core.motor.application.CalculadoraDeDescontosService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class InserirFuncionarioService implements InserirFuncionarioUseCase {

    private final FuncionarioPersistencePort funcionarioPersistencePort;
    private final CalculadoraDeDescontosService calculadoraDeDescontosService;

    public InserirFuncionarioService(FuncionarioPersistencePort funcionarioPersistencePort, CalculadoraDeDescontosService calculadoraDeDescontosService) {
        this.funcionarioPersistencePort = funcionarioPersistencePort;
        this.calculadoraDeDescontosService = calculadoraDeDescontosService;
    }

    @Override
    public Long inserir(InserirFuncionarioCommand command) {
        funcionarioPersistencePort.buscarPorDocumento(command.getDocumento())
                .ifPresent(s -> {
                    throw new AlreadExistsException("Já existe um funcionário cadastrado para este documento.");
                });
        Funcionario funcionario = mapper(command);
        easterEggEhTrabalhoEscravo(funcionario);
        return funcionarioPersistencePort.salvar(funcionario).getId();
    }

    private Funcionario mapper(InserirFuncionarioCommand command) {
        return new Funcionario.Builder()
                .nome(command.getNome())
                .sobrenome(command.getSobrenome())
                .sobrenome(command.getSobrenome())
                .documento(command.getDocumento())
                .beneficio(new Beneficio.Builder()
                        .planoSaude(command.getPlanoSaude())
                        .planoDental(command.getPlanoDental())
                        .valeTransporte(command.getValeTransporte())
                        .build())
                .setor(command.getSetor())
                .salario(command.getSalario())
                .dataAdmissao(command.getDataAdmissao())
                .build();
    }

    private void easterEggEhTrabalhoEscravo(Funcionario funcionario) {
        Map<String, BigDecimal> descontos = calculadoraDeDescontosService.calcular(funcionario);
        BigDecimal reduce = descontos.entrySet().stream()
                .map(item -> item.getValue()).collect(Collectors.toList())
                .stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if(funcionario.getSalario().subtract(reduce).doubleValue() < 0)
            throw new BusinessRuleException("Salário liquido será inferior a zero. Trabalho escravo.");
    }
}
