package gerador;

import com.fasterxml.jackson.databind.ObjectMapper;

import estruturas.pilha.Pilha;
import grafo.Grafo;
import grafo.Semaforo;
import grafo.Vertice;

import java.io.File;

public class GeradorMapa<Tipo> {


    /**
     * Gera um grafo a partir de um arquivo JSON no caminho especificado.
     *
     * @param path Caminho do arquivo JSON com os dados do grafo.
     * @return Um objeto {Grafo} representando o grafo, ou {null} em caso de erro.
     */
    public Grafo gerar(String path){
        File json = new File(path);

        try {
            // Mapeia o json é envia ele para clase conteiner para deserializar o json
            ObjectMapper mapper = new ObjectMapper();
            JsonConteiner estruturas = mapper.readValue(json, JsonConteiner.class);

            Pilha<ArestaBruta> arestas = gerarPilha(estruturas.arestas);
            Pilha<Vertice> vertices = gerarPilha(estruturas.vertices);
            Pilha<Semaforo> semaforos = gerarPilha(estruturas.semaforos);


            return gerarGrafo(arestas, vertices, semaforos);


        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <Tipo> Pilha<Tipo> gerarPilha(Tipo[] array){
        Pilha<Tipo> pilha = new Pilha<>();
        for (Tipo elemento : array) {
            pilha.add(elemento);
        }
        return pilha;
    }


    /**
     * Gera um grafo adicionando vértices e arestas brutas a uma nova instância de grafo.
     *
     * @param arestas   Uma lista de arestas brutas (ArestaBruta) a serem adicionadas ao grafo.
     * @param vertices  Uma lista de vértices (Vertice) a serem adicionados ao grafo.
     * @param semaforos Uma lista de semáforos (Semaforo) associados ao grafo (não utilizados neste método).
     * @return Uma nova instância de Grafo contendo os vértices e arestas brutas fornecidos.
     */
    private Grafo gerarGrafo(Pilha<ArestaBruta> arestas, Pilha<Vertice> vertices, Pilha<Semaforo> semaforos) {
        Grafo grafo = new Grafo();

        while (vertices.getTamanho() != 0){
            grafo.addVertice(vertices.remover());
        }


        while(arestas.getTamanho() != 0 ){
            grafo.addArestaBruta(arestas.remover());
        }

        return grafo;
    }



}
