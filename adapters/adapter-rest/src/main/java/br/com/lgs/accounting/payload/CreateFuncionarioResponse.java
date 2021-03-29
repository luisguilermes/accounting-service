package br.com.lgs.accounting.payload;

public class CreateFuncionarioResponse {
    private Long id;

    public CreateFuncionarioResponse() {
    }

    public CreateFuncionarioResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
