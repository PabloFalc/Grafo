package simulador;

import enums.Heuristica;
import estruturas.fila.Fila;
import estruturas.lista.Lista;
import gerador.GeradorDeVeiculos;
import gerador.GeradorMapa;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

        double larguraTela = 1920;
        double alturaTela = 1080;

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

        Pane pane = new Pane();

        Button startPauseButton = new Button("Start");
        Button stopButton = new Button("Stop");
        ComboBox<Heuristica> heuristicaSelect = new ComboBox<>();
        TextField inputVeiculos = new TextField("400");
        startPauseButton.setLayoutX(10);
        startPauseButton.setLayoutY(10);
        stopButton.setLayoutX(80);
        stopButton.setLayoutY(10);
        heuristicaSelect.setLayoutX(150);
        heuristicaSelect.setLayoutY(10);
        inputVeiculos.setLayoutX(300);
        inputVeiculos.setLayoutY(10);

        heuristicaSelect.getItems().addAll(Heuristica.PADRAO, Heuristica.ENERGIA, Heuristica.ESPERA);
        heuristicaSelect.setValue(Heuristica.PADRAO);

        pane.getChildren().addAll(startPauseButton, stopButton, heuristicaSelect, inputVeiculos);

        double escala = 45000;
        double offsetX = 550;
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

            Circle circle = new Circle(normX, normY, 3, Color.BLACK);
            circle.setStroke(Color.BLACK);

            pane.getChildren().addAll(circle);
        }

        Map<String, SimuladorSemaforo> semaforos = new HashMap<>();

        for (int i = 0; i < grafo.getVertices().getTamanho(); i++) {
            Vertice vertice = grafo.getVertices().get(i);

            if(vertice.hasSemaforo()){
                Circle semaforoView = new Circle(vertice.getLongitude(), vertice.getLatitude(), 5, Color.GREEN);
                pane.getChildren().add(semaforoView);

                SimuladorSemaforo controlador = new SimuladorSemaforo(semaforoView, Heuristica.ENERGIA);
                controlador.setArestasControladas(vertice.getArestasDeEntrada());
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
            line.setStrokeWidth(4);
            pane.getChildren().add(0, line);
        }

        // 5. Criar veículos
        Lista<Veiculo> veiculos = new Lista<>();
        Lista<Rectangle> icones = new Lista<>();

        final Timeline[] timeline = new Timeline[1];
        timeline[0] = new Timeline();

        startPauseButton.setOnAction(event -> {
            if (timeline[0].getStatus() == Timeline.Status.RUNNING) {
                timeline[0].pause();
                startPauseButton.setText("Start");
            } else {
                // Inicializar veículos apenas na primeira vez
                if (veiculos.getTamanho() == 0) {
                    int quantidadeVeiculos = 1000;
                    try {
                        quantidadeVeiculos = Math.min(2000, Math.max(0, Integer.parseInt(inputVeiculos.getText())));
                    } catch (NumberFormatException e) {
                        inputVeiculos.setText("1000");
                    }

                    for (int i = 0; i < quantidadeVeiculos; i++) {
                        Veiculo v = GeradorDeVeiculos.gerar(i + 1, grafo);
                        veiculos.add(v);
                        icones.add(v.getRectangle());
                        pane.getChildren().add(v.getRectangle());
                    }
                }
                timeline[0] = getTimeline(semaforos, veiculos , pane);
                timeline[0].play();
                startPauseButton.setText("Pause");
            }
        });

        stopButton.setOnAction(event -> {
            timeline[0].stop();
        });

        // 6. Exibir a cena
        Scene scene = new Scene(pane, larguraTela, alturaTela);
        stage.setTitle("Simulador com Coordenadas Normalizadas");
        stage.setScene(scene);
        stage.show();
    }

    private static Timeline getTimeline(Map<String, SimuladorSemaforo> semaforos, Lista<Veiculo> veiculos, Pane pane) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {

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
