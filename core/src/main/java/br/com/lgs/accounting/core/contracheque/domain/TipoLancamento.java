package br.com.lgs.accounting.core.contracheque.domain;

public enum TipoLancamento {
    DESCONTO("Desconto"),
    REMUNERACAO("Remuneração");

    private final String value;

    TipoLancamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
