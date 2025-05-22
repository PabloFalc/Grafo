package utils;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Logs {

    private int veiculoId;
    private int tempoEspera;
    private int tempoViagem;
    private int  tempoAtual;
    private int energiaTotal;

    public Logs(int veiculoId, int tempoAtual, int tempoViagem, int tempoEspera, int energiaTotal) {
        this.veiculoId = veiculoId;
        this.tempoAtual = tempoAtual;
        this.tempoViagem = tempoViagem;
        this.tempoEspera = tempoEspera;
        this.energiaTotal = energiaTotal;
    }

    public Logs() {
    }

}
