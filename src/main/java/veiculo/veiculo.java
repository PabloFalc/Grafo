package veiculo;

import estruturas.No;
import estruturas.lista.Lista;
import grafo.Vertice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class veiculo {

    private int ident;
    private Vertice origem, destino;
    private int tempoEspera;
    private Lista caminho;

    public veiculo(int ident, Vertice origem, Vertice destino, int tempoEspera) {
        this.ident = ident;
        this.origem = origem;
        this.destino = destino;
        this.tempoEspera = tempoEspera;
    }


}
