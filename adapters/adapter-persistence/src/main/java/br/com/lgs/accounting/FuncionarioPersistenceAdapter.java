package br.com.lgs.accounting;

import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FuncionarioPersistenceAdapter implements FuncionarioPersistencePort {

    private final FuncionarioRepository repository;

    public FuncionarioPersistenceAdapter(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Funcionario salvar(Funcionario salvar) {
        return repository.save(new FuncionarioEntity.Builder()
                .nome(salvar.getNome())
                .sobrenome(salvar.getSobrenome())
                .documento(salvar.getDocumento())
                .setor(salvar.getSetor())
                .salario(salvar.getSalario())
                .dataAdmissao(salvar.getDataAdmissao())
                .planoSaude(salvar.getBeneficio().getPlanoSaude())
                .planoDental(salvar.getBeneficio().getPlanoDental())
                .valeTransporte(salvar.getBeneficio().getValeTransporte())
                .build()).toEntity();
    }

    @Override
    public Optional<Funcionario> buscarPorDocumento(String documento) {
        return repository.findByDocumento(documento).map(FuncionarioEntity::toEntity);
    }

    @Override
    public Optional<Funcionario> buscarPorID(Long id) {
        return repository.findById(id).map(FuncionarioEntity::toEntity);
    }
}
