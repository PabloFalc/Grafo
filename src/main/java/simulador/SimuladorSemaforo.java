package simulador;

import enums.Heuristica;
import enums.Led;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SimuladorSemaforo {
    private final Circle view;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;

    private int tempoAtual = 0;
    private Led estado;

    public SimuladorSemaforo(Circle view, Heuristica heuristica) {
        this.view = view;
        this.estado = Led.VERDE;
        this.view.setFill(Color.GREEN);
        configHeuristica(heuristica);
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
            case VERDE:
                if (tempoAtual >= tempoVerde) {
                    switchCor();
                    tempoAtual = 0;
                }
                break;
            case AMARELO:
                if (tempoAtual >= tempoAmarelo) {
                    switchCor();
                    tempoAtual = 0;
                }
                break;
            case VERMELHO:
                if (tempoAtual >= tempoVermelho) {
                    switchCor();
                    tempoAtual = 0;
                }
                break;
        }
    }

    public boolean podePassar() {
        return estado == Led.VERDE || estado == Led.AMARELO;
    }

    public void switchCor(){

        switch (estado) {
            case VERDE:
                estado = Led.AMARELO;
                view.setFill(Color.YELLOW);
                break;
            case AMARELO:
                estado = Led.VERMELHO;
                view.setFill(Color.RED);
                break;
            case VERMELHO:
                estado = Led.VERDE;
                view.setFill(Color.GREEN);
                break;
            default:
                System.out.println("Erro ao switchCor");
        }
    }
}
