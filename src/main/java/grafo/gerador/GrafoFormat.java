package grafo.gerador;

import estruturas.lista.Lista;
import grafo.Aresta;
import grafo.Semaforo;
import grafo.Vertice;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;


@Getter

public class GrafoFormat {

    List<ArestaBruta> arestas;
    List<Vertice> vertices;
    List<Semaforo> semaforos;

    public GrafoFormat(List<ArestaBruta> arestas, List<Vertice> vertices, List<Semaforo> semaforos) {
        this.arestas = arestas;
        this.vertices = vertices;
        this.semaforos = semaforos;
    }

    public GrafoFormat() {
    }
}
