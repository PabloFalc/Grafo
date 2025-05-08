package grafo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cruzamento {
    /*
"id": "7170812871",
"latitude": -5.1500259,
"longitude": -42.7885974
    */

    private String id;
    private double latitude;
    private double longitude;

    public Cruzamento(double longitude, double latitude, String id) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
    }
}
