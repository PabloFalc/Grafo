package simulador;

import com.fasterxml.jackson.databind.ObjectMapper;
import grafo.Grafo;

import java.io.File;

public class geradorGrafo {

    public boolean gerarGrafo() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Grafo grafo = mapper.readValue(new File("C:/Users/pablo/Documents/Grafo-Teresina/teresinanew.json"),
                    Grafo.class);

            System.out.println(grafo.getVertices().getTamanho());
            System.out.println(grafo.getArestas().getTamanho());
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao gerar Grafo");
            return false;
        }
    }
}
