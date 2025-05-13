package gerador;

import com.fasterxml.jackson.annotation.JsonProperty;
import grafo.Aresta;
import lombok.Getter;
import lombok.Setter;

/**
 * Versão bruta da classe Aresta para mapear objetos do JSON.
 * 
 * Campos:
 * - id: Identificador único.
 * - origem: Nó de origem.
 * - destino: Nó de destino.
 * - tamanho: Comprimento da aresta.
 * - travel_time: Tempo de viagem.
 * - oneway: Indica mão única (true) ou mão dupla (false).
 * - maxspeed: Velocidade máxima.
 * 
 * Métodos:
 * - getArestaRefinada: Converte para Aresta refinada.
 */


@Getter
@Setter
public class ArestaBruta{

    @JsonProperty("id")
    private String id;

    @JsonProperty("origem")
    private String origem;

    @JsonProperty("destino")
    private String destino;

    @JsonProperty("tamanho")
    private int tamanho;
    @JsonProperty("travel_time")
    private double travel_time;
    @JsonProperty("oneway")
    private boolean oneway;
    @JsonProperty("maxspeed")
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

    public Aresta getArestaRefinada(){
        Aresta aresta = new Aresta();

        aresta.setId(this.id);
        aresta.setMaxspeed(this.maxspeed);
        aresta.setOneway(this.oneway);
        aresta.setTamanho(this.tamanho);
        aresta.setTravel_time(this.travel_time);

        return aresta;
    }



}
