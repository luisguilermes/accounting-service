package br.com.lgs.accounting.core.funcionario.application.port.out;

import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.util.Optional;

public interface FuncionarioPersistencePort {
    Funcionario salvar(Funcionario salvar);
    Optional<Funcionario> buscarPorDocumento(String documento);
    Optional<Funcionario> buscarPorID(Long id);


}
