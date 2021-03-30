package br.com.lgs.accounting.rest.mapper;

import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase.InserirFuncionarioCommand;
import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase.RecuperarFuncionarioQuery;
import br.com.lgs.accounting.rest.payload.CreateFuncionarioRequest;

public class FuncionarioMapper {

    public static InserirFuncionarioCommand toInserirCommand(CreateFuncionarioRequest request) {
        return new InserirFuncionarioCommand(
                request.getNome(), request.getSobrenome(), request.getDocumento(), request.getSetor(),
                request.getSalario(), request.getDataAdmissao(), request.getPlanoSaude(),
                request.getPlanoDental(), request.getValeTransporte());
    }

    public static RecuperarFuncionarioQuery toRecuperarFuncionarioQuery(Long id) {
        return new RecuperarFuncionarioQuery(id);
    }

}
