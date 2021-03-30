package br.com.lgs.accounting;

import br.com.lgs.accounting.rest.payload.CreateFuncionarioRequest;
import br.com.lgs.accounting.rest.payload.CreateFuncionarioResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioCriarITest extends AccountingApplicationTest {

    @Test
    public void criarFuncionarioComNomeInvalidoDeveRetornarStatusCode400() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest(null, "Drogo", "54004096014", "Logistica",
                BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), true, true, true);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<Object> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void criarFuncionarioComSobrenomeInvalidoDeveRetornarStatusCode400() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "", "54004096014", "Logistica",
                BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), true, true, true);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<Object> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void criarFuncionarioSemOpcoesDeBeneficiosDeveRetornarStatusCode400() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "Drogo", "54004096014", "Logistica",
                BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), null, null, null);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<Object> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void criarFuncionarioComCpfInvalidoDeveRetornarStatusCode400() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "Drogo", "54004096015", "Logistica",
                BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), true, true, true);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<Object> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void criarFuncionarioComSalarioLiquidoNegativoRetornarStatusCode400() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "Drogo", "72554847002", "Logistica",
                BigDecimal.valueOf(10), LocalDate.now().minusMonths(2), true, true, true);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<Object> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, Object.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(422);
    }

    @Test
    public void criarFuncionarioDeveRetornarStatusCode200EIdCriado() {
        CreateFuncionarioRequest body = new CreateFuncionarioRequest("Karl", "Drogo", "72554847002", "Logistica",
                BigDecimal.valueOf(5000), LocalDate.now().minusMonths(2), true, true, true);

        HttpEntity<CreateFuncionarioRequest> request = new HttpEntity<>(body);

        ResponseEntity<CreateFuncionarioResponse> resp = restTemplate.postForEntity("/api/v1/funcionarios", request, CreateFuncionarioResponse.class);
        Assertions.assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        Assert.assertTrue(resp.getBody().getId() instanceof  Long);
    }
}
