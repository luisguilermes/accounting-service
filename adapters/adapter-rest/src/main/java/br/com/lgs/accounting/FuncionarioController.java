package br.com.lgs.accounting;

import br.com.lgs.accounting.core.funcionario.application.port.in.InserirFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.application.port.in.RecuperarFuncionarioUseCase;
import br.com.lgs.accounting.core.funcionario.domain.Funcionario;
import br.com.lgs.accounting.mapper.FuncionarioMapper;
import br.com.lgs.accounting.payload.CreateFuncionarioRequest;
import br.com.lgs.accounting.payload.CreateFuncionarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final InserirFuncionarioUseCase inserirFuncionarioUseCase;
    private final RecuperarFuncionarioUseCase recuperarFuncionarioUseCase;

    public FuncionarioController(InserirFuncionarioUseCase inserirFuncionarioUseCase, RecuperarFuncionarioUseCase recuperarFuncionarioUseCase) {
        this.inserirFuncionarioUseCase = inserirFuncionarioUseCase;
        this.recuperarFuncionarioUseCase = recuperarFuncionarioUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateFuncionarioResponse> criarFuncionario(@RequestBody CreateFuncionarioRequest data) {
        Long funcionarioId = inserirFuncionarioUseCase.inserir(FuncionarioMapper.toInserirCommand(data));
        return ResponseEntity.ok(new CreateFuncionarioResponse(funcionarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = recuperarFuncionarioUseCase.pesquisar(FuncionarioMapper.toRecuperarFuncionarioQuery(id));
        if(funcionario.isPresent())
            return ResponseEntity.ok(funcionario.get());
        return ResponseEntity.notFound().build();
    }

}
