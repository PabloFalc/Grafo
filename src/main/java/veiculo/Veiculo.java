package veiculo;

import estruturas.lista.Lista;
import grafo.Aresta;
import grafo.Vertice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Veiculo {
    private int id;
    private Vertice origem, destino;
    private int tempoEspera;
    private Lista<Vertice> caminho;
    private int posicaoAtual; // índice do caminho atual
    private boolean chegouAoDestino;
    private Aresta arestaAtual;

    public Veiculo(int id, Vertice origem, Vertice destino, Lista<Vertice> caminho) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.tempoEspera = 0;
        this.posicaoAtual = 0;
        this.chegouAoDestino = false;
        this.arestaAtual = null;
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
        if (isChegouAoDestino()) return;

        if (posicaoAtual + 1 < caminho.getTamanho()) {
            posicaoAtual++;
            if(posicaoAtual == caminho.getTamanho()) {
                setChegouAoDestino(true);
            }
        }
    }

    public Aresta getProximoAresta() {

        Vertice verticeAtual = getVerticeAtual();
        Vertice proximoVertice = getProximoVertice();
        if(isChegouAoDestino()){
            return null;
        }

        for (int i = 0; i < verticeAtual.getArestasDeEntrada().tamanho; i++) {
            Aresta aresta = verticeAtual.getArestasDeEntrada().get(i);
            if(aresta.getOrigem().getId().equals(proximoVertice.getId())){
                return aresta;
            }
        }
        return null;
    }

    public boolean isInicio() {
        return  this.posicaoAtual == 0;
    }
}
