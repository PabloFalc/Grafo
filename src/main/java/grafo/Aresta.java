package grafo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aresta {

    protected String id;
    private Vertice origem;
    private Vertice destino;
    protected int tamanho;
    protected double travel_time;
    protected boolean oneway;
    protected int maxspeed;

    public Aresta(String id, int maxspeed, boolean oneway, double travel_time, int tamanho, Vertice destino,
                  Vertice origem) {
        this.id = id;
        this.maxspeed = maxspeed;
        this.oneway = oneway;
        this.travel_time = travel_time;
        this.tamanho = tamanho;
        this.destino = destino;
        this.origem = origem;
    }

    public Aresta() {
    }


}
