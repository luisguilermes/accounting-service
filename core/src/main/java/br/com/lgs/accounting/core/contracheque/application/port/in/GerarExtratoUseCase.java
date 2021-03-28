package br.com.lgs.accounting.core.contracheque.application.port.in;

import br.com.lgs.accounting.core.common.exception.ConstraintViolationException;
import br.com.lgs.accounting.core.common.validation.ConstraintViolation;
import br.com.lgs.accounting.core.common.validation.SelfValidating;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotNull;
import br.com.lgs.accounting.core.contracheque.domain.Extrato;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public interface GerarExtratoUseCase {

    Extrato gerar(GerarExtratoQuery query);

    class GerarExtratoQuery extends SelfValidating<GerarExtratoQuery> {
        @ValidateNotNull private final Long funcionarioId;
        private final LocalDate referencia;

        public GerarExtratoQuery(Long funcionarioId, int mes, int ano) {
            this.funcionarioId = funcionarioId;
            this.referencia = parseReferencia(mes, ano);
            this.validateSelf();
        }

        private LocalDate parseReferencia(int mes, int ano) {
            if(ano <= 0) {
                throw new ConstraintViolationException(
                        new HashSet(Arrays.asList(new ConstraintViolation<>("ano", "Ano informado inválido."))));
            }
            try {
                return LocalDate.of(ano, mes, 1);
            } catch (Exception e) {
                throw new ConstraintViolationException(
                    new HashSet(Arrays.asList(new ConstraintViolation<>("mes", "Mês informado inválido."))));
            }
        }

        public Long getFuncionarioId() {
            return funcionarioId;
        }

        public LocalDate getReferencia() {
            return referencia;
        }
    }
}
