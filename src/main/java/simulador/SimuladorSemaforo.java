package simulador;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SimuladorSemaforo {
    private final Circle view;
    private final int tempoVerde;
    private final int tempoAmarelo;
    private final int tempoVermelho;

    private int tempoAtual = 0;
    private String estado = "VERDE"; // inicia no verde

    public SimuladorSemaforo(Circle view, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.view = view;
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
        this.view.setFill(Color.GREEN);
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
