package gerador;

import grafo.Semaforo;
import grafo.Vertice;
import lombok.Getter;

/**
 * Classe JsonConteiner usada apenas para mapear os dados de um JSON 
 * contendo arestas, vértices e semáforos.
 */
@Getter
public class JsonConteiner {

    ArestaBruta[] arestas;
    Vertice[] vertices;
    Semaforo[] semaforos;

    public JsonConteiner(ArestaBruta[] arestas, Semaforo[] semaforos, Vertice[] vertices) {
        this.arestas = arestas;
        this.semaforos = semaforos;
        this.vertices = vertices;
    }

    public JsonConteiner() {
    }
}
