package br.com.lgs.accounting.rest;

import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.rest.mapper.FuncionarioMapper;
import br.com.lgs.accounting.rest.payload.CreateFuncionarioRequest;
import br.com.lgs.accounting.rest.payload.CreateFuncionarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api(tags = "Funcionarios", description = "API de funcionarios")
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final InserirFuncionarioUseCase inserirFuncionarioUseCase;
    private final RecuperarFuncionarioUseCase recuperarFuncionarioUseCase;

    public FuncionarioController(InserirFuncionarioUseCase inserirFuncionarioUseCase, RecuperarFuncionarioUseCase recuperarFuncionarioUseCase) {
        this.inserirFuncionarioUseCase = inserirFuncionarioUseCase;
        this.recuperarFuncionarioUseCase = recuperarFuncionarioUseCase;
    }

    @ApiOperation(value = "Registrar funcionário.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o id do funcionario criado"),
            @ApiResponse(code = 400, message = "Atributos informados no payload com valores incorretos."),
            @ApiResponse(code = 422, message = "Ocorreu algum erro de negócio."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<CreateFuncionarioResponse> criarFuncionario(@RequestBody CreateFuncionarioRequest data) {
        Long funcionarioId = inserirFuncionarioUseCase.inserir(FuncionarioMapper.toInserirCommand(data));
        return ResponseEntity.ok(new CreateFuncionarioResponse(funcionarioId));
    }

    @ApiOperation(value = "Recuperar funcionário pelo id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna funcionário"),
            @ApiResponse(code = 404, message = "Não encontrado funcionário para o id informado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = recuperarFuncionarioUseCase.pesquisar(FuncionarioMapper.toRecuperarFuncionarioQuery(id));
        if(funcionario.isPresent())
            return ResponseEntity.ok(funcionario.get());
        return ResponseEntity.notFound().build();
    }

}
