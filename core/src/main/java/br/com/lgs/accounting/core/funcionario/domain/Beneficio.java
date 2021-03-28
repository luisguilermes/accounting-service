package br.com.lgs.accounting.core.funcionario.domain;

public class Beneficio {
    private Boolean planoSaude;
    private Boolean planoDental;
    private Boolean valeTransporte;

    private Beneficio(Boolean planoSaude, Boolean planoDental, Boolean valeTransporte) {
        this.planoSaude = planoSaude;
        this.planoDental = planoDental;
        this.valeTransporte = valeTransporte;
    }

    public static class Builder {
        private Boolean planoSaude;
        private Boolean planoDental;
        private Boolean valeTransporte;

        public Builder planoSaude(Boolean planoSaude) {
            this.planoSaude = planoSaude;
            return this;
        }
        public Builder planoDental(Boolean planoDental) {
            this.planoDental = planoDental;
            return this;
        }
        public Builder valeTransporte(Boolean valeTransporte) {
            this.valeTransporte = valeTransporte;
            return this;
        }

        public Beneficio build() {
            return new Beneficio(planoSaude, planoDental, valeTransporte);
        }
    }
}
