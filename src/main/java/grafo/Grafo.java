package grafo;

import estruturas.lista.Lista;
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
        Vertice inicio = this.getVertice(novaAresta.getDestino());
        Vertice fim = this.getVertice(novaAresta.getOrigem());
        this.arestas.add(novaAresta);
        inicio.addArestaDeSaida(novaAresta);
        fim.addArestaDeEntrada(novaAresta);
    }

    public Vertice getVertice(Vertice vertice){
        Vertice atual = null;
        for (int i = 0; i < this.vertices.tamanho; i++) {
            if (this.vertices.get(i).getId().equals(vertice.getId())) {
                atual = this.vertices.get(i);
                break;
            }
        }
        return atual;
    }


}
