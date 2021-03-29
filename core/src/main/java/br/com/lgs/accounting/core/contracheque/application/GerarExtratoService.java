package br.com.lgs.accounting.core.contracheque.application;

import br.com.lgs.accounting.core.common.exception.NotFoundException;
import br.com.lgs.accounting.core.contracheque.application.port.in.GerarExtratoUseCase;
import br.com.lgs.accounting.core.contracheque.domain.Extrato;
import br.com.lgs.accounting.core.contracheque.domain.Lancamento;
import br.com.lgs.accounting.core.contracheque.domain.TipoLancamento;
import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.core.motor.application.CalculadoraDeDescontosService;

import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class GerarExtratoService implements GerarExtratoUseCase {

    private final RecuperarFuncionarioUseCase recuperarFuncionarioUseCase;

    public GerarExtratoService(RecuperarFuncionarioUseCase recuperarFuncionarioUseCase) {
        this.recuperarFuncionarioUseCase = recuperarFuncionarioUseCase;
    }

    @Override
    public Extrato gerar(GerarExtratoQuery query) {
        Funcionario funcionario = recuperarFuncionarioUseCase.pesquisar(new RecuperarFuncionarioUseCase
                .RecuperarFuncionarioQuery(query.getFuncionarioId()))
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado."));

        validarSeFuncionarioContratadoNoMesDeReferencia(funcionario, query.getReferencia());
        validarSeExtratoEstaDisponivelParaAReferencia(query.getReferencia());
        return construirExtrao(funcionario, query.getReferencia());
    }

    private Extrato construirExtrao(Funcionario funcionario, LocalDate ref) {
        String referencia = ref.getMonth().getValue() + "/" + ref.getYear();
        Map<String, BigDecimal> descontos = new CalculadoraDeDescontosService().calcular(funcionario);
        List<Lancamento> lancamentos = construirLancamentos(funcionario, descontos);
        BigDecimal totalDesconto = calcularTotalDesconto(lancamentos);
        BigDecimal salarioLiquido = calcularSalarioLiquido(funcionario.getSalario(), totalDesconto);
        return new Extrato(referencia,lancamentos,
                funcionario.getSalario().doubleValue(),
                totalDesconto.doubleValue(),
                salarioLiquido.doubleValue());
    }

    private BigDecimal calcularSalarioLiquido(BigDecimal salario, BigDecimal totalDesconto) {
        return salario.add(totalDesconto);
    }

    private BigDecimal calcularTotalDesconto(List<Lancamento> lancamentos) {
        BigDecimal totalDesconto = BigDecimal.ZERO;
        for(Lancamento lancamento : lancamentos) {
            if(lancamento.getTipo().equals(TipoLancamento.DESCONTO)) {
                totalDesconto = totalDesconto.add(lancamento.getValor());
            }
        }
        return totalDesconto.multiply(BigDecimal.valueOf(-1));
    }

    private List<Lancamento> construirLancamentos(Funcionario funcionario, Map<String, BigDecimal> descontos) {
        List<Lancamento> lancamentos = new ArrayList<>();
        lancamentos.add(new Lancamento(TipoLancamento.REMUNERACAO, funcionario.getSalario(), "Salário Mensal"));
        lancamentos.addAll(descontos.entrySet().stream()
                .map(desconto -> new Lancamento(TipoLancamento.DESCONTO, desconto.getValue(), desconto.getKey()))
                .collect(Collectors.toList()));
        return lancamentos;
    }

    private void validarSeFuncionarioContratadoNoMesDeReferencia(Funcionario funcionario, LocalDate referencia) {
        if (!((referencia.getMonth().getValue() == funcionario.getDataAdmissao().getMonth().getValue() &&
                        referencia.getYear() == funcionario.getDataAdmissao().getYear()
        ) || referencia.isAfter(funcionario.getDataAdmissao()))) {
            throw new NotFoundException("Funcionário não estava contratado nesse período.");
        }
    }

    private void validarSeExtratoEstaDisponivelParaAReferencia(LocalDate referencia) {
        LocalDate now = LocalDate.now();
        if(referencia.isAfter(now) || (now.getYear() == referencia.getYear()  && referencia.getMonth().equals(now.getMonth())))
            throw  new NotFoundException("Extrato para este mês ainda não está disponível.");
    }

}
