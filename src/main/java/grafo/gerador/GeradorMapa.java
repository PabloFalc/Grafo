package grafo.gerador;

import com.fasterxml.jackson.databind.ObjectMapper;
import grafo.Vertice;

import java.io.File;
import java.util.List;

public class GeradorMapa {


    public void gerar(){
        File file = new File("C:/Users/pablo/Documents/Grafo/Grafo/json/j.json");


        try{
            ObjectMapper mapper = new ObjectMapper();
            GrafoFormat g = mapper.readValue(file, GrafoFormat.class);

            List<Vertice> vert = g.getVertices();

            for (Vertice v : vert) {
                System.out.println(v.getId());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
}
