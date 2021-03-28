package br.com.lgs.accounting.core.funcionario.application;

import br.com.lgs.accounting.core.common.exception.AlreadExistsException;
import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

public class InserirFuncionarioService implements InserirFuncionarioUseCase {

    private final FuncionarioPersistencePort funcionarioPersistencePort;

    public InserirFuncionarioService(FuncionarioPersistencePort funcionarioPersistencePort) {
        this.funcionarioPersistencePort = funcionarioPersistencePort;
    }

    @Override
    public Long inserir(InserirFuncionarioCommand command) {
        funcionarioPersistencePort.buscarPorDocumento(command.getDocumento())
                .ifPresent(s -> {
                    throw new AlreadExistsException("Já existe um funcionário cadastrado para este documento.");
                });
        return funcionarioPersistencePort.salvar(mapper(command)).getId();
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
}
