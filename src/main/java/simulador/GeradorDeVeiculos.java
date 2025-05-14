package simulador;

import dijkstra.Dijkstra;
import estruturas.fila.Fila;
import estruturas.lista.Lista;
import grafo.Grafo;
import grafo.Vertice;
import veiculo.veiculo;

import java.util.Random;

public class GeradorDeVeiculos {

    private static final Random random = new Random();

    public static veiculo gerar(int id, Grafo grafo) {
        Lista<Vertice> vertices = grafo.getVertices();
        int tamanho = vertices.getTamanho();

        Vertice origem;
        Vertice destino;

        // Garante que origem e destino são diferentes
        do {
            origem = vertices.get(random.nextInt(tamanho));
            destino = vertices.get(random.nextInt(tamanho));
        } while (origem == destino);

        Fila<Vertice> caminhoFila = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);
        Lista<Vertice> caminho = Main.filaParaLista(caminhoFila);

        return new veiculo(id, origem, destino, caminho);
    }
}
