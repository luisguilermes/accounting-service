package br.com.lgs.accounting.rest;

import br.com.lgs.accounting.core.contracheque.application.port.in.GerarExtratoUseCase;
import br.com.lgs.accounting.core.contracheque.application.port.in.GerarExtratoUseCase.GerarExtratoQuery;
import br.com.lgs.accounting.core.contracheque.domain.Extrato;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@Api(tags = "Contracheque", description = "API de contracheque")
@RequestMapping("/api/v1/contracheque")
public class ExtratoController {

    private final GerarExtratoUseCase gerarExtratoUseCase;

    public ExtratoController(GerarExtratoUseCase gerarExtratoUseCase) {
        this.gerarExtratoUseCase = gerarExtratoUseCase;
    }

    @ApiOperation(value = "Gerar o último contracheque ou o da data selecionada.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o extrato para o id do funcionario criado"),
            @ApiResponse(code = 404, message = "Usuario não encontrado ou data de admissão do funcionário posterior a data solicitada.."),
            @ApiResponse(code = 422, message = "Ocorreu algum erro de negócio."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<Extrato> extratoParaFuncionario(@PathVariable Long id,
                                                          @RequestParam Optional<Integer> mes,
                                                          @RequestParam Optional<Integer> ano) {
        Integer[]  params = parseParams(mes, ano);
        return ResponseEntity.ok(gerarExtratoUseCase.gerar(new GerarExtratoQuery(id, params[0], params[1])));
    }

    private Integer[] parseParams(Optional<Integer> mes, Optional<Integer> ano) {
        Integer[] params = new Integer[2];

        LocalDate beforeMonth = LocalDate.now().minusMonths(1);
        if(mes.isPresent())
            params[0] = mes.get();
        else
            params[0] = beforeMonth.getMonth().getValue();

        if(!ano.isEmpty())
            params[1] = ano.get();
        else
            params[1] = beforeMonth.getYear();

        return params;
    }
}
