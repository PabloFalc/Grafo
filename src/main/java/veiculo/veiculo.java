package veiculo;

import estruturas.lista.Lista;
import grafo.Vertice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class veiculo {
    private int id;
    private Vertice origem, destino;
    private int tempoEspera;
    private Lista<Vertice> caminho;
    private int posicaoAtual; // índice do caminho atual
    private boolean chegouAoDestino;

    public veiculo(int id, Vertice origem, Vertice destino, Lista<Vertice> caminho) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.tempoEspera = 0;
        this.posicaoAtual = 0;
        this.chegouAoDestino = false;
    }

    public Vertice getVerticeAtual() {
        return caminho.get(posicaoAtual);
    }

    public Vertice getProximoVertice() {
        if (posicaoAtual + 1 < caminho.getTamanho()) {
            return caminho.get(posicaoAtual + 1);
        }
        return null;
    }

    public void mover() {
        if (chegouAoDestino) return;

        if (posicaoAtual + 1 < caminho.getTamanho()) {
            posicaoAtual++;
        } else {
            chegouAoDestino = true;
        }
    }
}
