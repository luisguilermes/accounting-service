package br.com.lgs.accounting.core.funcionario.application;

import br.com.lgs.accounting.core.common.exception.AlreadExistsException;
import br.com.lgs.accounting.core.common.exception.ConstraintViolationException;
import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


public class InserirFuncionarioServiceTest {
    private InserirFuncionarioService service;
    private FuncionarioPersistencePort funcionarioPersistencePort;

    @BeforeEach
    void init() {
        this.funcionarioPersistencePort = Mockito.mock(FuncionarioPersistencePort.class);
        this.service = new InserirFuncionarioService(funcionarioPersistencePort);
    }

    @Test
    void naoDeveCriarFuncionarioComCPFInvalido() {
        List<String> patterns = Arrays.asList(null, "", "00000000000", "11111111111",
                "22222222222", "33333333333", "44444444444", "55555555555",
                "66666666666", "77777777777", "88888888888", "99999999999",
                "736530340958", "73653034", "a3653034095", "73653034095", "774.013.220-89",
                "686.348.57026");
        for(String cpf : patterns) {
            assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                    .InserirFuncionarioCommand("Jon", "Snow",
                    cpf, "Logistica", new BigDecimal("1100"),
                    LocalDate.now(), true, true, true)));
        }
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemNome() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand(null, "Snow",
                "01341490092", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemSobrenome() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", null,
                "01341490092", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemSetor() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", null, new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "01341490092", "", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemSalario() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemDataDeAdmissao() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                null, true, true, true)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveCriarFuncionarioSemSelecionarBeneficios() {
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), null, true, true)));
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, null, true)));
        assertThrows(ConstraintViolationException.class, () -> service.inserir(new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "37120924078", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, null)));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void naoDeveInserirFuncionarioComDocumentoJaCadastrado() {
        var command = new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "55366975018", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true);

        Mockito.when(funcionarioPersistencePort.buscarPorDocumento(any()))
                .thenReturn(Optional.of(new Funcionario.Builder().build()));

        assertThrows(AlreadExistsException.class, () -> service.inserir(command));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(0)).salvar(any());
    }

    @Test
    void deveCriarUmFuncionarioComSucesso() {
        var command = new InserirFuncionarioUseCase
                .InserirFuncionarioCommand("Jon", "Snow",
                "55366975018", "Logistica", new BigDecimal("1100"),
                LocalDate.now(), true, true, true);

        Mockito.when(funcionarioPersistencePort.buscarPorDocumento(any()))
                .thenReturn(Optional.empty());
        Mockito.when(funcionarioPersistencePort.salvar(any()))
                .thenReturn(new Funcionario.Builder().id(100L).build());

        assertDoesNotThrow(() -> service.inserir(command));
        Mockito.verify(funcionarioPersistencePort, Mockito.times(1)).buscarPorDocumento(any());
        Mockito.verify(funcionarioPersistencePort, Mockito.times(1)).salvar(any());
    }
}
