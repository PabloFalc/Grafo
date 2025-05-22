package grafo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Semaforo {

    private String id;
    private double latitude;
    private double longitude;
    private String direcao;
    public Semaforo() {
    }

    public Semaforo(String id, String direcao, double longitude, double latitude) {
        this.id = id;
        this.direcao = direcao;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}


