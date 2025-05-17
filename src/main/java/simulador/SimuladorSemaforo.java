package simulador;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SimuladorSemaforo {
    private final Circle view;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;

    private int tempoAtual = 0;
    private String estado = "VERDE"; // inicia no verde

    public SimuladorSemaforo(Circle view, Heuristica heuristica) {
        this.view = view;
        configHeuristica(heuristica);
        this.view.setFill(Color.GREEN);
    }

    private void configHeuristica(Heuristica heuristica) {

        switch (heuristica) {
            case PADRAO:
                this.tempoVerde = 4;
                this.tempoAmarelo = 2;
                this.tempoVermelho = 4;
                break;
            case ENERGIA:
                break;
            case ESPERA:
                break;
            default:
                System.out.println("Erro ao configurar heuristica");
                break;
        }


    }


    public void tick() {
        tempoAtual++;

        switch (estado) {
            case "VERDE":
                if (tempoAtual >= tempoVerde) {
                    estado = "AMARELO";
                    tempoAtual = 0;
                    view.setFill(Color.YELLOW);
                }
                break;
            case "AMARELO":
                if (tempoAtual >= tempoAmarelo) {
                    estado = "VERMELHO";
                    tempoAtual = 0;
                    view.setFill(Color.RED);
                }
                break;
            case "VERMELHO":
                if (tempoAtual >= tempoVermelho) {
                    estado = "VERDE";
                    tempoAtual = 0;
                    view.setFill(Color.GREEN);
                }
                break;
        }
    }

    public boolean podePassar() {
        return estado.equals("VERDE") || estado.equals("AMARELO");
    }
}
