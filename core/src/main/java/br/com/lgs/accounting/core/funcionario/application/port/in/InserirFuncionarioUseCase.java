package br.com.lgs.accounting.core.funcionario.application.port.in;

import br.com.lgs.accounting.core.common.validation.SelfValidating;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateCPF;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotEmpty;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InserirFuncionarioUseCase {
    Long inserir(InserirFuncionarioCommand command);

    class InserirFuncionarioCommand extends SelfValidating<InserirFuncionarioCommand> {
        @ValidateNotEmpty private String nome;
        @ValidateNotEmpty private String sobrenome;
        @ValidateCPF private String documento;
        @ValidateNotEmpty private String setor;
        @ValidateNotNull private BigDecimal salario;
        @ValidateNotNull private LocalDate dataAdmissao;
        @ValidateNotNull private Boolean planoSaude;
        @ValidateNotNull private Boolean planoDental;
        @ValidateNotNull private Boolean valeTransporte;

        public InserirFuncionarioCommand(String nome, String sobrenome, String documento,
                                         String setor, BigDecimal salario, LocalDate dataAdmissao,
                                         Boolean planoSaude, Boolean planoDental, Boolean valeTransporte) {
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.documento = parserCPFInput(documento);
            this.setor = setor;
            this.salario = salario;
            this.dataAdmissao = dataAdmissao;
            this.planoSaude = planoSaude;
            this.planoDental = planoDental;
            this.valeTransporte = valeTransporte;
            this.validateSelf();
        }

        private static String parserCPFInput(String cpf) {
            if(cpf != null && cpf.matches("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})")) {
                cpf = cpf.replaceAll("\\.|-", "");
            }
            return cpf;
        }

        public String getNome() {
            return nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public String getDocumento() {
            return documento;
        }

        public String getSetor() {
            return setor;
        }

        public BigDecimal getSalario() {
            return salario;
        }

        public LocalDate getDataAdmissao() {
            return dataAdmissao;
        }

        public Boolean getPlanoSaude() {
            return planoSaude;
        }

        public Boolean getPlanoDental() {
            return planoDental;
        }

        public Boolean getValeTransporte() {
            return valeTransporte;
        }
    }
}
