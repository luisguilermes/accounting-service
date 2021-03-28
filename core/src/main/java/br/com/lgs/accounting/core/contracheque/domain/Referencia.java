package br.com.lgs.accounting.core.contracheque.domain;

import java.time.LocalDate;

public class Referencia {
    public final String value;

    public Referencia(LocalDate referencia) {
        this.value = referencia.getMonth() + "/" + referencia.getYear();
    }
    public Referencia(int mes, int ano) {
        this.value = mes + "/" + ano;
    }
}
