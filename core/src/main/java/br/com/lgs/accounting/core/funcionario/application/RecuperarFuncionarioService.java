package br.com.lgs.accounting.core.funcionario.application;

import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import javax.inject.Named;
import java.util.Optional;

@Named
public class RecuperarFuncionarioService implements RecuperarFuncionarioUseCase {
    private final FuncionarioPersistencePort funcionarioPersistencePort;

    public RecuperarFuncionarioService(FuncionarioPersistencePort funcionarioPersistencePort) {
        this.funcionarioPersistencePort = funcionarioPersistencePort;
    }

    @Override
    public Optional<Funcionario> pesquisar(RecuperarFuncionarioQuery query) {
        return funcionarioPersistencePort.buscarPorID(query.getFuncionarioId());
    }
}
