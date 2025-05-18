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

    public Logs(int veiculoId, int tempoAtual, int tempoViagem, int tempoEspera) {
        this.veiculoId = veiculoId;
        this.tempoAtual = tempoAtual;
        this.tempoViagem = tempoViagem;
        this.tempoEspera = tempoEspera;
    }

    public Logs() {
    }


}
