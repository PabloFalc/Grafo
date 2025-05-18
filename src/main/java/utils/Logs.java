package utils;


import lombok.Getter;

@Getter
public class Logs {

    private final int veiculoId;
    private final int tempoEspera;
    private final int tempoViagem;
    private final int  tempoAtual;

    public Logs(int veiculoId, int tempoAtual, int tempoViagem, int tempoEspera) {
        this.veiculoId = veiculoId;
        this.tempoAtual = tempoAtual;
        this.tempoViagem = tempoViagem;
        this.tempoEspera = tempoEspera;
    }
}
