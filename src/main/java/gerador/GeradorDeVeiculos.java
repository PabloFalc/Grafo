package gerador;

import dijkstra.Dijkstra;
import estruturas.fila.Fila;
import estruturas.lista.Lista;
import grafo.Grafo;
import grafo.Vertice;
import javafx.scene.paint.Color;
import simulador.Simulador;
import veiculo.Veiculo;

import java.util.Random;

public class GeradorDeVeiculos {

    private static final Color[] CORES = {
            Color.CYAN,      // 1
            Color.GREEN,     // 2
            Color.BLUE,      // 3
            Color.RED,       // 4
            Color.YELLOW,    // 5
            Color.ORANGE,    // 6
            Color.PURPLE,    // 7
            Color.BROWN,     // 8
            Color.PINK,      // 9
            Color.LIGHTBLUE, // 10
            Color.LIME,      // 11
            Color.MAGENTA,   // 12
            Color.GOLD,      // 13
            Color.TEAL,      // 14
            Color.INDIGO,    // 15
            Color.DARKGRAY,  // 16
            Color.LIGHTSALMON, // 17
            Color.DARKOLIVEGREEN, // 18
            Color.CORAL,     // 19
            Color.CHOCOLATE  // 20
    };

    private static final Random random = new Random();

    public static Veiculo gerar(int id, Grafo grafo) {
        Lista<Vertice> vertices = grafo.getVertices();
        int tamanho = vertices.getTamanho();

        Vertice origem;
        Vertice destino;

        // Garante que origem e destino são diferentes
        do {
            origem = vertices.get(random.nextInt(tamanho));
            destino = vertices.get(random.nextInt(tamanho));
        } while (origem == destino);

        Color cor = selectCor(random.nextInt(CORES.length));

        Fila<Vertice> caminhoFila = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);
        Lista<Vertice> caminho = Simulador.filaParaLista(caminhoFila);

        return new Veiculo(id, origem, destino, caminho, cor);
    }




    private static Color selectCor(int numero) {
        if (numero >= 1 && numero <= CORES.length) {
            return CORES[numero - 1];
        }
        return Color.GRAY; // Cor padrão para valores inválidos
    }

}
