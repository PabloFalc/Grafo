package simulador;

import enums.Heuristica;
import estruturas.fila.Fila;
import estruturas.lista.Lista;
import gerador.GeradorDeVeiculos;
import gerador.GeradorMapa;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import utils.Logs;
import veiculo.Veiculo;

import java.util.HashMap;
import java.util.Map;


public class Simulador extends Application {

    public static Lista<Vertice> filaParaLista(Fila<Vertice> fila) {
        Lista<Vertice> lista = new Lista<>();
        while (!fila.isEmpty()) {
            lista.add(fila.remover());
        }
        return lista;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Gerar grafo
        GeradorMapa<Grafo> gerador = new GeradorMapa<>();
        Grafo grafo = gerador.gerar("json/mapa.json");
        System.out.println(grafo.getVertices().getTamanho());
        System.out.println(grafo.getArestas().getTamanho());

        double larguraTela = 800;
        double alturaTela = 600;

        // 1. Encontrar os valores mínimo e máximo
        double minLat = Double.MAX_VALUE;
        double maxLat = -Double.MAX_VALUE;

        double minLon = Double.MAX_VALUE;
        double maxLon = -Double.MAX_VALUE;

        for (int i = 0; i < grafo.getVertices().getTamanho(); i++) {
            Vertice v = grafo.getVertices().get(i);
            if (v.getLatitude() < minLat) minLat = v.getLatitude();
            if (v.getLatitude() > maxLat) maxLat = v.getLatitude();
            if (v.getLongitude() < minLon) minLon = v.getLongitude();
            if (v.getLongitude() > maxLon) maxLon = v.getLongitude();
        }

        System.out.println("Latitude: " + minLat + " a " + maxLat);
        System.out.println("Longitude: " + minLon + " a " + maxLon);

        Pane pane = new Pane();
        double escala = 40000;
        double offsetX = 350;
        double offsetY = 225;

        for (int i = 0; i < grafo.getVertices().getTamanho(); i++) {
            Vertice vertice = grafo.getVertices().get(i);
            // Convertendo graus para radianos
            double angulo = Math.toRadians(190);

            // Calcula o centro do mapa
            double centroLat = (minLat + maxLat) / 2.0;
            double centroLon = (minLon + maxLon) / 2.0;

            // Para cada vértice:
            double lat = vertice.getLatitude() - centroLat;
            double lon = vertice.getLongitude() - centroLon;

            // Aplica rotação
            double rotX = lat * Math.cos(angulo) - lon * Math.sin(angulo);
            double rotY = lat * Math.sin(angulo) + lon * Math.cos(angulo);

            // Escala e posicionamento
            double normX = rotX * escala + 400 + offsetX; // centralizado
            double normY = rotY * escala + 300 + offsetY; // centralizado

            vertice.setLongitude(normX);
            vertice.setLatitude(normY);

            Circle circle = new Circle(normX, normY, 3, Color.ORANGE);
            Text label = new Text(normX - 5, normY - 10, vertice.getId());
            circle.setStroke(Color.BLACK);

            pane.getChildren().addAll(circle, label);
        }

        Map<String, SimuladorSemaforo> semaforos = new HashMap<>();

        for (int i = 0; i < grafo.getVertices().getTamanho(); i++) {
            Vertice vertice = grafo.getVertices().get(i);

            if(vertice.hasSemaforo()){
                Circle semaforoView = new Circle(vertice.getLongitude(), vertice.getLatitude(), 5, Color.GREEN);
                pane.getChildren().add(semaforoView);

                SimuladorSemaforo controlador = new SimuladorSemaforo(semaforoView, Heuristica.PADRAO);
                semaforos.put(vertice.getId(), controlador);
            }
        }


        // 4. Criar arestas com as posições já normalizadas
        for (int i = 0; i < grafo.getArestas().getTamanho(); i++) {
            Aresta aresta = grafo.getArestas().get(i);
            Line line = new Line(
                    aresta.getOrigem().getLongitude(), aresta.getOrigem().getLatitude(),
                    aresta.getDestino().getLongitude(), aresta.getDestino().getLatitude()
            );
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(1);
            pane.getChildren().add(0, line);
        }

        // 5. Criar caminho com Dijkstra e animar veículo
        Vertice origem = grafo.getVertices().get(0);

        //veiculo
        int quantidadeVeiculos = 1000;
        Lista<Veiculo> veiculos = new Lista<>();
        Lista<Rectangle> icones = new Lista<>();
        for (int i = 0; i < quantidadeVeiculos; i++) {
            Veiculo v = GeradorDeVeiculos.gerar(i + 1, grafo);
            veiculos.add(v);
            icones.add(v.getRectangle());
            pane.getChildren().add(v.getRectangle());
        }


        final Timeline timeline = getTimeline(semaforos, veiculos , pane);
        timeline.play();

        // 6. Exibir a cena
        Scene scene = new Scene(pane, larguraTela, alturaTela);
        stage.setTitle("Simulador com Coordenadas Normalizadas");
        stage.setScene(scene);
        stage.show();
    }

    private static Timeline getTimeline(Map<String, SimuladorSemaforo> semaforos, Lista<Veiculo> veiculos, Pane pane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            // Atualiza os semáforos
            for (SimuladorSemaforo controlador : semaforos.values()) {
                controlador.tick();
            }

            // Atualiza os veículos
            for (int i = 0; i < veiculos.getTamanho(); i++) {
                Veiculo v = veiculos.get(i);

                if (v.isChegouAoDestino()) {
                    System.out.println("Veículo chegou ao destino e será removido.");
                    veiculos.removerPorPosicao(i);
                    pane.getChildren().remove(v.getRectangle());
                    i--;
                    continue;
                }

                Vertice proximo = v.getProximoVertice();
                Aresta proxima = v.getProximoAresta();

                if (proximo != null) {
                    SimuladorSemaforo semaforo = semaforos.get(proximo.getId());
                    if (semaforo != null && !semaforo.podePassar()) {
                        continue; // Semáforo fechado
                    }
                }

                if (v.isInicio()) {
                    v.setArestaAtual(proxima);
                    if (proxima != null) {
                        proxima.addVeiculo(v);
                    }
                } else {
                    Aresta atual = v.getArestaAtual();
                    if (proxima != null && proxima.isFull()) {
                        continue; // Aresta futura cheia
                    }

                    if (atual != null) {
                        atual.removeVeiculo(v);
                    }

                    if (proxima != null) {
                        proxima.addVeiculo(v);
                        v.setArestaAtual(proxima);
                    }
                }

                v.mover();

            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }
}
