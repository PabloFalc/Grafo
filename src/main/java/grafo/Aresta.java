package grafo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aresta {
    /*

"id": "2324867802-6410842748-0",
"source": "2324867802",
"target": "6410842748",
"length": 346.304282800876,
"travel_time": 0.0,
"oneway": false,
"maxspeed": 50.0
    */

    private String id;
    private Vertice source;
    private Vertice target;
    private int length;
    private double travel_time;
    private boolean oneway;
    private int maxspeed;



    public Aresta(Vertice source, Vertice target, int length, double travel_time, boolean oneway, int maxspeed) {
        this.source = source;
        this.target = target;
        this.length = length;
        this.travel_time = travel_time;
        this.oneway = oneway;
        this.maxspeed = maxspeed;
    }


}
