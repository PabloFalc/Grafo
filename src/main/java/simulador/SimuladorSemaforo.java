package simulador;

import enums.Heuristica;
import enums.Led;
import estruturas.lista.Lista;
import grafo.Aresta;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Setter;

public class SimuladorSemaforo {
    private final Circle view;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    @Setter
    private Lista<Aresta> arestasControladas;
    private int resetTime;
    private final Heuristica heuristica;
    private int tempoAtual = 0;
    private Led estado;

    public SimuladorSemaforo(Circle view, Heuristica heuristica) {
        this.view = view;
        this.estado = Led.VERDE;
        this.view.setFill(Color.GREEN);
        this.heuristica = heuristica;
        resetTime = 0;
        tempoAmarelo = 2;
    }


    private void configHeuristica() {
        switch (heuristica) {
            case PADRAO:
                this.tempoVerde = 4;
                this.tempoVermelho = 4;
                break;

            case ESPERA:
                int totalVeiculosEspera = 0;
                for (int i = 0; i < arestasControladas.tamanho; i++) {
                    totalVeiculosEspera += arestasControladas.get(i).getFila().getTamanho();
                }

                if (totalVeiculosEspera >= 10) {
                    tempoVerde = 6;
                } else if (totalVeiculosEspera >= 5) {
                    tempoVerde = 4;
                } else {
                    tempoVerde = 2;
                }

                tempoVermelho = 4;
                break;

            case ENERGIA:
                int totalVeiculosEnergia = 0;
                for (int i = 0; i < arestasControladas.tamanho; i++) {
                    totalVeiculosEnergia += arestasControladas.get(i).getFila().getTamanho();
                }

                boolean pico = tempoAtual % 1200 >= 300 && tempoAtual % 1200 <= 600 || tempoAtual % 1200 >= 900;

                if (pico) {
                    tempoVerde = totalVeiculosEnergia >= 5 ? 6 : 4;
                    tempoVermelho = 4;
                } else {
                    tempoVerde = totalVeiculosEnergia >= 5 ? 4 : 2;
                    tempoVermelho = 6;
                }
                break;

            default:
                System.out.println("Erro ao configurar heuristica");
                break;
        }
    }


    public void tick() {
        tempoAtual++;

        configHeuristica();

        if(!hasVeiculos() && resetTime == 0) {
            return;
        }

        if(resetTime == 15){
            resetTime = 0;
            return;
        }

        switch (estado) {
            case VERDE:
                if (tempoAtual >= tempoVerde) {
                    switchCor();
                    tempoAtual = 0;
                    resetTime++;
                }
                break;
            case AMARELO:
                if (tempoAtual >= tempoAmarelo) {
                    switchCor();
                    tempoAtual = 0;
                    resetTime++;
                }
                break;
            case VERMELHO:
                if (tempoAtual >= tempoVermelho) {
                    switchCor();
                    tempoAtual = 0;
                    resetTime++;
                }
                break;
        }
    }


    public boolean podePassar() {
        return estado == Led.VERDE || estado == Led.AMARELO;
    }

    public void switchCor() {

        switch (this.estado) {
            case VERDE:
                this.estado = Led.AMARELO;
                view.setFill(Color.YELLOW);
                break;
            case AMARELO:
                this.estado = Led.VERMELHO;
                view.setFill(Color.RED);
                break;
            case VERMELHO:
                this.estado = Led.VERDE;
                view.setFill(Color.GREEN);
                break;
            default:
                System.out.println("Erro ao switchCor");
        }
    }

    private boolean hasVeiculos(){
        for(int i = 0; i< arestasControladas.tamanho; i++){
            if(!arestasControladas.get(i).getFila().isEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean hasArestasFull(){
        for(int i = 0; i< arestasControladas.tamanho; i++){
            if(!arestasControladas.get(i).getFila().isFull()){
                return true;
            }
        }
        return false;

    }


    public int getEnergia() {
        //cada ciclo de semáforo gasta 1 de energia
        return (tempoVerde + tempoVermelho + tempoAmarelo) / 10;
    }
}
