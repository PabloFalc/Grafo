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
                Color.ORANGE,     // 2
                Color.BEIGE,      // 3
                Color.BLUE,       // 4
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

            Lista<Vertice> caminho = caminhoFila.toList();
            return new Veiculo(id, origem, destino, caminho, cor);
        }




        private static Color selectCor(int numero) {
            if (numero >= 1 && numero <= CORES.length) {
                return CORES[numero - 1];
            }
            return Color.CYAN; // Cor padrão para valores inválidos
        }

    }
