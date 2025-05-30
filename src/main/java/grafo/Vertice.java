package grafo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import estruturas.lista.Lista;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vertice {

    @JsonProperty("id")
    private String id;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    private Lista<Aresta> arestasDeEntrada;
    private Lista<Aresta> arestasDeSaida;
    @JsonIgnore
    private Semaforo semaforo;
    private double distancia = Double.POSITIVE_INFINITY;
    private Vertice anterior;

    public void resetarDijkstra() {
        distancia = Double.POSITIVE_INFINITY;
        anterior = null;
    }

    public Vertice(double longitude, double latitude, String id) {
        this.arestasDeEntrada = new Lista<>();
        this.arestasDeSaida = new Lista<>();
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
    }

    public Vertice(){
        this.arestasDeEntrada = new Lista<>();
        this.arestasDeSaida = new Lista<>();
    }


    public void addArestaDeEntrada(Aresta aresta){
        arestasDeEntrada.add(aresta);
    }
    public void addArestaDeSaida(Aresta aresta){
        arestasDeSaida.add(aresta);
    }

    public Lista<Aresta> ArestasTotais() {
        Lista<Aresta> todas = new Lista<>();

        for (int i = 0; i < arestasDeEntrada.getTamanho(); i++) {
            todas.add(arestasDeEntrada.get(i));
        }

        for (int i = 0; i < arestasDeSaida.getTamanho(); i++) {
            todas.add(arestasDeSaida.get(i));
        }

        return todas;
    }

    public boolean hasSemaforo(){
        return semaforo != null;
    }




}
