package grafo;

import estruturas.lista.Lista;
import gerador.ArestaBruta;
import lombok.Getter;

@Getter
public class Grafo {

    public Lista<Aresta> arestas;
    public Lista<Vertice> vertices;


    public Grafo() {
        this.vertices = new Lista<>();
        this.arestas = new Lista<>();
    }

    public void addVertice(Vertice novoVertice) {
        this.vertices.add(novoVertice);
    }
    public void addAresta(Aresta novaAresta) {
        Vertice inicio = this.getVertice(novaAresta.getDestino().getId());
        Vertice fim = this.getVertice(novaAresta.getOrigem().getId());
        this.arestas.add(novaAresta);
        inicio.addArestaDeSaida(novaAresta);
        fim.addArestaDeEntrada(novaAresta);
    }

    // Method para adicionar arestas vindas do json
    public void addArestaBruta(ArestaBruta novaAresta) {

        Vertice inicio = this.getVertice(novaAresta.getDestino());
        Vertice fim = this.getVertice(novaAresta.getOrigem());

        Aresta aresta = novaAresta.getArestaRefinada();

        aresta.setOrigem(inicio);
        aresta.setDestino(fim);


        this.arestas.add(aresta);
        inicio.addArestaDeSaida(aresta);
        fim.addArestaDeEntrada(aresta);
    }

    public Vertice getVertice(String verticeId){
        Vertice atual = null;
        for (int i = 0; i < this.vertices.tamanho; i++) {
            if (this.vertices.get(i).getId().equals(verticeId)) {
                atual = this.vertices.get(i);
                break;
            }
        }
        return atual;
    }
    public Lista<Aresta> obterArestasDe(Vertice vertice){
        return vertice.getArestasDeSaida();
    }
}
