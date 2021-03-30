package br.com.lgs.accounting;

import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.databuilder.FuncionarioDataBuilder;
import br.com.lgs.accounting.rest.payload.CreateFuncionarioRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioRecuperarITest extends AccountingApplicationTest {

    @MockBean
    private FuncionarioPersistencePort funcionarioPersistencePort;

    @Test
    public void buscarFuncionarioNaoCadastradoDeveRetornarStatusCode404() {
        ResponseEntity<Object> resp = restTemplate.getForEntity("/api/v1/funcionarios/9999", Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(404);
    }

    CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "Drogo", "72554847002", "Logistica",
            BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), true, true, true);

    @Test
    public void buscarFuncionarioCadastradoDeveRetornarStatusCode200EUmInstanciaDeFuncionario() {
        Mockito.when(funcionarioPersistencePort.buscarPorID(1L)).thenReturn(FuncionarioDataBuilder.getFuncionario());
        ResponseEntity<Object> resp = restTemplate.getForEntity("/api/v1/funcionarios/1", Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(200);
    }
}
