package br.com.lgs.accounting.core.contracheque.application;

import br.com.lgs.accounting.core.common.exception.NotFoundException;
import br.com.lgs.accounting.core.contracheque.application.port.in.GerarExtratoUseCase;
import br.com.lgs.accounting.core.contracheque.domain.Extrato;
import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.domain.Beneficio;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.core.motor.application.CalculadoraDeDescontosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class GerarExtratoServiceTest {
    private RecuperarFuncionarioUseCase recuperarFuncionarioUseCase;
    private GerarExtratoService service;

    @BeforeEach
    void init() {
        this.recuperarFuncionarioUseCase = Mockito.mock(RecuperarFuncionarioUseCase.class);
        this.service = new GerarExtratoService(recuperarFuncionarioUseCase);
    }

    @Test
    void deveRetornarErroQuandoFuncionarioNaoExistir(){
        GerarExtratoUseCase.GerarExtratoQuery query = new GerarExtratoUseCase.GerarExtratoQuery(1L, 2, 2021);

        Mockito.when(recuperarFuncionarioUseCase.pesquisar(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.service.gerar(query));
        Mockito.verify(recuperarFuncionarioUseCase, Mockito.times(1)).pesquisar(any());
    }

    @Test
    void deveRetornarErroQuandoFuncionarioFoiContratadoAposMesReferencia(){
        GerarExtratoUseCase.GerarExtratoQuery query = new GerarExtratoUseCase.GerarExtratoQuery(1L, 1, 2021);

        Mockito.when(recuperarFuncionarioUseCase.pesquisar(any()))
                .thenReturn(Optional.of(new Funcionario.Builder().dataAdmissao(LocalDate.of(2021, 2, 1)).build()));

        assertThrows(NotFoundException.class, () -> this.service.gerar(query));
        Mockito.verify(recuperarFuncionarioUseCase, Mockito.times(1)).pesquisar(any());
    }

    @Test
    void deveRetornarErroQuandoMesReferenciaEAtual(){
        LocalDate now = LocalDate.now();
        GerarExtratoUseCase.GerarExtratoQuery query = new GerarExtratoUseCase.GerarExtratoQuery(1L, now.getMonth().getValue(), now.getYear());

        Mockito.when(recuperarFuncionarioUseCase.pesquisar(any()))
                .thenReturn(Optional.of(new Funcionario.Builder().dataAdmissao(LocalDate.of(2021, 2, 1)).build()));

        assertThrows(NotFoundException.class, () -> this.service.gerar(query));
        Mockito.verify(recuperarFuncionarioUseCase, Mockito.times(1)).pesquisar(any());
    }

    @Test
    void deveRetornarErroQuandoMesReferenciaEPosterior(){
        LocalDate after = LocalDate.now().plusMonths(1);
        GerarExtratoUseCase.GerarExtratoQuery query = new GerarExtratoUseCase.GerarExtratoQuery(1L, after.getMonth().getValue(), after.getYear());

        Mockito.when(recuperarFuncionarioUseCase.pesquisar(any()))
                .thenReturn(Optional.of(new Funcionario.Builder().dataAdmissao(LocalDate.of(2021, 2, 1)).build()));

        assertThrows(NotFoundException.class, () -> this.service.gerar(query));
        Mockito.verify(recuperarFuncionarioUseCase, Mockito.times(1)).pesquisar(any());
    }

    @Test
    void deveRetornarUmExtratoComSucesso(){
        GerarExtratoUseCase.GerarExtratoQuery query = new GerarExtratoUseCase.GerarExtratoQuery(1L, 1, 2021);
        Optional<Funcionario> funcionario = Optional.of(new Funcionario.Builder()
                .dataAdmissao(LocalDate.of(2020, 2, 1))
                .salario(BigDecimal.valueOf(4000))
                .beneficio(new Beneficio.Builder()
                        .valeTransporte(true)
                        .planoDental(true)
                        .planoSaude(true).build())
                .build());

        Mockito.when(recuperarFuncionarioUseCase.pesquisar(any())).thenReturn(funcionario);

        Extrato extrato = this.service.gerar(query);

        assertEquals(extrato.getReferencia(), "1/2021");
        assertEquals(extrato.getSalarioBruto(), 4000);
        assertEquals(-1706.46, extrato.getTotalDescontos());
        assertEquals(extrato.getSalarioLiquido(), 2293.54);
        assertEquals(extrato.getLancamentos().size(), 7);

        Mockito.verify(recuperarFuncionarioUseCase, Mockito.times(1)).pesquisar(any());
    }
}
