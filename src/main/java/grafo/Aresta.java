package grafo;

import estruturas.fila.FilaLimite;
import lombok.Getter;
import lombok.Setter;
import veiculo.Veiculo;

@Getter
@Setter
public class Aresta {

    private String id;
    private Vertice origem;
    private Vertice destino;
    private int tamanho;
    private double travel_time;
    private boolean oneway;
    private int maxspeed;
    private FilaLimite<Veiculo> fila;


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

    public Aresta(){

    }

    public void setFilaLimite(){
        fila = new FilaLimite(this.tamanho/3);
    }

    public boolean hasVeliculos(){
        return !fila.isEmpty();
    }

    public boolean isFull(){
        return fila.isFull();
    }

    public boolean addVeiculo(Veiculo veiculo){
        return fila.add(veiculo);
    }

    public Veiculo removeVeiculo(Veiculo veiculo){
        return fila.remover();
    }




}
