package grafo.gerador;

import grafo.Vertice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArestaBruta {

    private String id;
    private String origem;
    private String destino;
    private int tamanho;
    private double travel_time;
    private boolean oneway;
    private int maxspeed;

    public ArestaBruta(String id, int maxspeed, boolean oneway, int tamanho, double travel_time, String destino,
                       String origem) {
        this.id = id;
        this.maxspeed = maxspeed;
        this.oneway = oneway;
        this.tamanho = tamanho;
        this.travel_time = travel_time;
        this.destino = destino;
        this.origem = origem;
    }

    public ArestaBruta() {
    }
}
