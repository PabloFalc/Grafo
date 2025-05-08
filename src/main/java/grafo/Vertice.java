package grafo;

import estruturas.lista.Lista;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vertice {
    /*
"id": "7170812871",
"latitude": -5.1500259,
"longitude": -42.7885974
    */

    private String id;
    private double latitude;
    private double longitude;
    private Lista<Aresta> arestasDeEntrada;

    private Lista<Aresta> arestasDeSaida;

    public Vertice(double longitude, double latitude, String id) {
        this.arestasDeEntrada = new Lista<Aresta>();
        this.arestasDeSaida = new Lista<Aresta>();
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
    }

    public boolean addArestaDeEntrada(Aresta aresta){
        return this.arestasDeEntrada.add(aresta);
    }
    public boolean addArestaDeSaida(Aresta aresta){
        return this.arestasDeSaida.add(aresta);
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





}
