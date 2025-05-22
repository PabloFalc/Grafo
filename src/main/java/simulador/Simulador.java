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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import utils.LogSistema;
import veiculo.Veiculo;

import java.util.HashMap;
import java.util.Map;


public class Simulador extends Application {

    public static Lista<Vertice> filaParaLista(Fila<Vertice> fila) {
        return fila.toList();
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Gerar grafo
        GeradorMapa<Grafo> gerador = new GeradorMapa<>();
        Grafo grafo = gerador.gerar("json/mapa.json");

        double larguraTela = 1200;
        double alturaTela = 800;

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
        TextField inputVelocidade = new TextField("1");
        Label labelVeiculos = new Label("Quantidade de Veículos:");
        Label labelVelocidade = new Label("Velocidade do carro (s):");
        Label timerLabel = new Label("Tempo: 0s");
        timerLabel.setLayoutX(10);
        timerLabel.setLayoutY(100);
        timerLabel.setMinWidth(200);
        timerLabel.setMinHeight(40);
        timerLabel.setTextFill(Color.RED);
        startPauseButton.setLayoutX(10);
        startPauseButton.setLayoutY(10);
        stopButton.setLayoutX(80);
        stopButton.setLayoutY(10);
        heuristicaSelect.setLayoutX(150);
        heuristicaSelect.setLayoutY(10);
        labelVeiculos.setLayoutX(10);
        labelVeiculos.setLayoutY(40);
        inputVeiculos.setLayoutX(150);
        inputVeiculos.setLayoutY(40);
        labelVelocidade.setLayoutX(10);
        labelVelocidade.setLayoutY(70);
        inputVelocidade.setLayoutX(150);
        inputVelocidade.setLayoutY(70);

        heuristicaSelect.getItems().addAll(Heuristica.PADRAO, Heuristica.ENERGIA, Heuristica.ESPERA);
        heuristicaSelect.setValue(Heuristica.PADRAO);

        pane.getChildren().addAll(startPauseButton, stopButton, heuristicaSelect, inputVeiculos,inputVelocidade, labelVeiculos, labelVelocidade, timerLabel);

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

                SimuladorSemaforo controlador = new SimuladorSemaforo(semaforoView, Heuristica.PADRAO);
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

        LogSistema log = new LogSistema();
        log.totalSemaforos = semaforos.size();


        stopButton.setOnAction(event -> {
            timeline[0].stop();
            exibirLogModal(log);
        });

        startPauseButton.setOnAction(event -> {
            if (timeline[0].getStatus() == Timeline.Status.RUNNING) {
                timeline[0].pause();
                startPauseButton.setText("Start");
                exibirLogModal(log);
            } else {
                // Inicializar veículos apenas na primeira vez
                if (veiculos.getTamanho() == 0) {
                    int quantidadeVeiculos = 5000;
                    try {
                        quantidadeVeiculos = Math.min(5000, Math.max(0, Integer.parseInt(inputVeiculos.getText())));
                        log.totalVeiculosCriados = quantidadeVeiculos;
                        log.totalVeiculosAtivos = quantidadeVeiculos;

                    } catch (NumberFormatException e) {
                        inputVeiculos.setText("5000");
                    }

                    for (int i = 0; i < quantidadeVeiculos; i++) {
                        Veiculo v = GeradorDeVeiculos.gerar(i + 1, grafo);
                        veiculos.add(v);
                        icones.add(v.getRectangle());
                        pane.getChildren().add(v.getRectangle());
                    }
                }

                double velocidade = Double.parseDouble(inputVelocidade.getText());

                timeline[0] = getTimeline(semaforos, veiculos , pane, log, velocidade, timerLabel);
                timeline[0].play();
                startPauseButton.setText("Pause");
            }
        });

        // 6. Exibir a cena
        Scene scene = new Scene(pane, larguraTela, alturaTela);
        log.createTxt();
        stage.setTitle("Simulador de Trânsito");
        stage.setScene(scene);
        stage.show();
    }

    private static Timeline getTimeline(Map<String, SimuladorSemaforo> semaforos, Lista<Veiculo> veiculos, Pane pane, LogSistema logSys, double velocidade, Label timerLabel) {
        double[] tempo = {0};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(velocidade), e -> {


            logSys.tempoTotal++;
            logSys.energiaTotal = semaforos.values().stream().mapToInt(SimuladorSemaforo::getEnergia).sum();

            tempo[0] += velocidade;
            timerLabel.setText(String.format("Tempo: %.1fs", tempo[0]));

            // Atualiza semáforos
            for (SimuladorSemaforo controlador : semaforos.values()) {
                controlador.tick();
                logSys.ciclosSemaforosExecutados++;
            }

            // Atualiza veículos
            for (int i = 0; i < veiculos.getTamanho(); i++) {
                Veiculo v = veiculos.get(i);


                Vertice proximo = v.getProximoVertice();
                Aresta proximaAresta = v.getArestaAtual();
                if (v.isChegouAoDestino()) {
                    System.out.println("veículo ["+v.getId()+"] tempo de espera total : " +  v.getTempoEspera());
                    proximaAresta.removeVeiculo(v);
                    removerVeiculo(pane, veiculos, logSys, i, false);
                    i--;
                    continue;
                }

                boolean podeMover = true;

                // Verifica semáforo
                if (proximo != null) {
                    SimuladorSemaforo semaforo = semaforos.get(proximo.getId());
                    if (semaforo != null && !semaforo.podePassar() && v.getProgressoNaAresta() >= 0.65 && proximaAresta == semaforo.getArestasControladas().get(0)) {
                        podeMover = false;
                        v.setTempoEspera(v.getTempoEspera() + 1);
                        logSys.tempoEspera += v.getTempoEspera();
                    }
                }

                // Controle de aresta
                if (v.isInicio()) {
                    if (proximaAresta != null) {
                        v.setArestaAtual(proximaAresta);
                        proximaAresta.addVeiculo(v);
                    }
                } else {
                    Aresta arestaAtual = v.getArestaAtual();
                    if (proximaAresta != null) {
                        if (proximaAresta.isFull()) {
                            podeMover = false;
                            v.setTempoEspera(v.getTempoEspera() + 1);
                            logSys.tempoEspera += v.getTempoEspera();
                        }
                        if (arestaAtual != proximaAresta) {
                            if (arestaAtual != null) arestaAtual.removeVeiculo(v);
                            proximaAresta.addVeiculo(v);
                            v.setArestaAtual(proximaAresta);
                        }
                    }
                }

                if (podeMover) {
                    v.resetarTicksParado();
                    try {
                        v.mover();
                        if(proximaAresta == null){
                            System.out.println("veículo ["+v.getId()+"] tempo de espera total : " +  v.getTempoEspera());
                            removerVeiculo(pane, veiculos, logSys, i, false);
                        }
                        if(proximaAresta != v.getArestaAtual() && proximaAresta != null) {
                            proximaAresta.removeVeiculo(v);
                        }
                    } catch (Exception exp) {
                        System.out.println("veículo ["+v.getId()+"] tempo de espera total : " +  v.getTempoEspera());
                        removerVeiculo(pane, veiculos, logSys, i, true);
                        i--;
                    }
                } else {
                    v.incrementarTicksParado();

                    if (v.getTicksParado() > 500) {
                        boolean remover = true;

                        if (proximo != null) {
                            SimuladorSemaforo semaforo = semaforos.get(proximo.getId());
                            if ((semaforo != null && !semaforo.podePassar()) ||
                                    (proximaAresta != null && proximaAresta.isFull())) {
                                remover = false;
                            }
                        }

                        if (remover) {
                            System.out.println("veículo ["+v.getId()+"] tempo de espera total : " +  v.getTempoEspera());
                            removerVeiculo(pane, veiculos, logSys, i, true);
                            i--;
                        }
                    }
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    // Método utilitário para remoção de veículos
    private static void removerVeiculo(Pane pane, Lista<Veiculo> veiculos, LogSistema logSys, int index, boolean defeituoso) {
        Veiculo v = veiculos.get(index);
        pane.getChildren().remove(v.getRectangle());
        veiculos.removerPorPosicao(index);
        logSys.totalVeiculosAtivos--;
        if (defeituoso) {
            logSys.veiculosDefeituosos++;
        } else {
            logSys.totalVeiculosFinalizados++;
        }
    }
    private void exibirLogModal(LogSistema log) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Log do Sistema");
        alert.setHeaderText("Resumo da Simulação");

        String mensagem = "Tempo total de simulação: " + log.tempoTotal + "\n" +
                "Energia total gasta pelos semáforos: " + log.energiaTotal + "\n" +
                "Total de veículos criados: " + log.totalVeiculosCriados + "\n" +
                "Total de veículos finalizados: " + log.totalVeiculosFinalizados + "\n" +
                "Total de veículos ativos: " + log.totalVeiculosAtivos + "\n" +
                "Total de semáforos: " + log.totalSemaforos + "\n" +
                "Ciclos de semáforos executados: " + log.ciclosSemaforosExecutados + "\n"+
                "Tempo de espera total dos veículos (ms) : " + log.tempoEspera+ "\n";

        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
