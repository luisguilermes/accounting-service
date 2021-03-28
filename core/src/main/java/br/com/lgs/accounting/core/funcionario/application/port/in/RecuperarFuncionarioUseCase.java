package br.com.lgs.accounting.core.funcionario.application.port.in;

import br.com.lgs.accounting.core.common.validation.SelfValidating;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotNull;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;

import java.util.Optional;

public interface RecuperarFuncionarioUseCase {
    Optional<Funcionario> pesquisar(RecuperarFuncionarioQuery query);

    class RecuperarFuncionarioQuery extends SelfValidating<RecuperarFuncionarioQuery> {
        @ValidateNotNull private final Long funcionarioId;

        public RecuperarFuncionarioQuery(Long funcionarioId) {
            this.funcionarioId = funcionarioId;
            this.validateSelf();
        }

        public Long getFuncionarioId() {
            return funcionarioId;
        }
    }
}
