package veiculo;

import estruturas.No;
import estruturas.lista.Lista;

public class veiculo {

    private int id;
    private No origem, destino;
    private int tempoEspera;
    private Lista caminho;

    veiculo(int id, No origem, No destino, int tempoEspera) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.tempoEspera = tempoEspera;
    }
}
