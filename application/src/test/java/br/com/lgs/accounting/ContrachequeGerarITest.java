package br.com.lgs.accounting;

import br.com.lgs.accounting.core.contracheque.domain.Extrato;
import br.com.lgs.accounting.core.funcionario.application.port.out.FuncionarioPersistencePort;
import br.com.lgs.accounting.databuilder.FuncionarioDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

public class ContrachequeGerarITest extends AccountingApplicationTest {

    @MockBean
    private FuncionarioPersistencePort funcionarioPersistencePort;

    @Test
    public void solicitarContrachequeAnteriorADataAdminissaoDeveRetornarStatusCode404() {
        Mockito.when(funcionarioPersistencePort.buscarPorID(1L)).thenReturn(FuncionarioDataBuilder.getFuncionarioAdmissao20210201());

        ResponseEntity<Object> resp = restTemplate.getForEntity("/api/v1/contracheque/funcionario/1?mes=1&ano=2021", Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void solicitarContrachequeFuncionarioInexisteRetornarStatusCode404() {
        Mockito.when(funcionarioPersistencePort.buscarPorID(9999L)).thenReturn(FuncionarioDataBuilder.getFuncionarioEmpty());

        ResponseEntity<Object> resp = restTemplate.getForEntity("/api/v1/contracheque/funcionario/9999", Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void solicitarContrachequeFuncionarioComSucessoDeveRetornar200ComSeusRespectivosCalculosCorretos() {
        Mockito.when(funcionarioPersistencePort.buscarPorID(1L)).thenReturn(FuncionarioDataBuilder.getFuncionario());
        ResponseEntity<Extrato> resp = restTemplate.getForEntity("/api/v1/contracheque/funcionario/1", Extrato.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        Assert.assertTrue(resp.getBody().getSalarioBruto() == 5000);
        Assert.assertTrue(resp.getBody().getTotalDescontos() == -2069.69);
        Assert.assertTrue(resp.getBody().getSalarioLiquido() == 2930.31);
        Assert.assertTrue(resp.getBody().getLancamentos().size() == 7);
    }
}
