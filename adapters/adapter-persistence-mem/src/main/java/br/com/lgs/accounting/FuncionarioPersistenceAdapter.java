package br.com.lgs.accounting;

import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FuncionarioPersistenceAdapter implements FuncionarioPersistencePort {
    private static final Map<Long, Funcionario> datasource = new HashMap<>();
    private Long id = 1L;

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        funcionario.setId(id);
        datasource.put(id, funcionario);
        id++;
        return funcionario;
    }

    @Override
    public Optional<Funcionario> buscarPorDocumento(String documento) {
        for(Funcionario funcionario : datasource.values()) {
            if(funcionario.getDocumento().equals(documento))
                return Optional.of(funcionario);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Funcionario> buscarPorID(Long id) {
        Funcionario funcionario = datasource.get(id);
        return funcionario != null ? Optional.of(funcionario) : Optional.empty();
    }
}
