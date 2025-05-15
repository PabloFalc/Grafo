package grafo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Semaforo {

    private String id;
    private String latitude;
    private String longitude;
    private String direcao;
    public Semaforo() {
    }

    public Semaforo(String id, String direcao, String longitude, String latitude) {
        this.id = id;
        this.direcao = direcao;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}


