package veiculo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import estruturas.lista.Lista;
import grafo.Aresta;
import grafo.Vertice;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import utils.Logs;

@Getter
@Setter
public class Veiculo {
    @JsonIgnore
    private Rectangle rectangle;
    private Logs log;
    private int id;
    private Vertice origem, destino;
    private int tempoEspera;
    private Lista<Vertice> caminho;
    private int posicaoAtual;
    private boolean chegouAoDestino;
    private Aresta arestaAtual;

    private double progressoNaAresta = 0.0; // de 0.0 até 1.0
    private double velocidade = 15; // ajuste conforme necessário

    public Veiculo(int id, Vertice origem, Vertice destino, Lista<Vertice> caminho, Color cor) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.tempoEspera = 0;
        this.posicaoAtual = 0;
        this.chegouAoDestino = false;
        this.progressoNaAresta = 0.0;
        this.log = new Logs();

        double tamanho = 10;
        double x = origem.getLongitude() - tamanho / 2;
        double y = origem.getLatitude() - tamanho / 2;
        this.rectangle = new Rectangle(x, y, tamanho, tamanho);
        this.rectangle.setFill(cor);
        this.rectangle.setStroke(Color.BLACK);

        this.arestaAtual = calcularArestaAtual(); // inicializa corretamente
    }

    private Aresta calcularArestaAtual() {
        if (posicaoAtual + 1 >= caminho.getTamanho()) return null;
        Vertice atual = caminho.get(posicaoAtual);
        Vertice proximo = caminho.get(posicaoAtual + 1);


        if (atual.getId().equals(proximo.getId())) {
            System.out.println("⚠️ Proximo vértice igual ao atual, retornando null.");
            return null;
        }

        for (int i = 0; i < atual.getArestasDeSaida().tamanho; i++) {
            Aresta a = atual.getArestasDeSaida().get(i);
            if (a.getDestino().getId().equals(proximo.getId())) {
                return a;
            }
        }

        System.out.println("⚠️ [find]Aresta não encontrada entre " + atual.getId() + " -> " + proximo.getId() + " " +
                "para o veículo " + id);
        return  null;
    }


    public Vertice getVerticeAtual() {
        return caminho.get(posicaoAtual);
    }

    public Vertice getProximoVertice() {

        if (posicaoAtual + 1 < caminho.getTamanho()) {
            return caminho.get(posicaoAtual + 1);
        }
        else {
            return getVerticeAtual();
        }
    }

    public void mover() {
        if (chegouAoDestino || arestaAtual == null){
            return;
        }

        double comprimento = arestaAtual.getComprimento();
        progressoNaAresta += velocidade/comprimento;

        if (progressoNaAresta >= 1.0) {
            progressoNaAresta = 0.0;

            posicaoAtual++;
            if (posicaoAtual >= caminho.getTamanho() - 1) {
                chegouAoDestino = true;
                atualizarPosicaoGrafica(); // posiciona no destino final
                return;
            }

            arestaAtual = calcularArestaAtual(); // atualizar após o incremento
            if(arestaAtual == null) {
                throw new NullPointerException("asdasdasd");
            }
        }

        atualizarPosicaoGrafica();
    }

    public Aresta getProximoAresta() {
        if (posicaoAtual + 1 >= caminho.getTamanho()) return null;
        Vertice atual = caminho.get(posicaoAtual);
        Vertice proximo = caminho.get(posicaoAtual + 1);

        if (atual.getId().equals(proximo.getId())) {
            System.out.println("⚠️ Proximo vértice igual ao atual, retornando null.");
            return null;
        }

        if(proximo == null) {
            System.out.println("é nulo");
            proximo = atual;
        }

        for (int i = 0; i < atual.getArestasDeSaida().tamanho; i++) {
            Aresta a = atual.getArestasDeSaida().get(i);
            if (a.getDestino().getId().equals(proximo.getId())) {
                return a;
            }
        }

        System.out.println("⚠️ [GET]Aresta não encontrada entre " + atual.getId() + " -> " + proximo.getId() + " " +
                "para o veículo " + id);
        return  null;
    }

    public boolean isInicio() {
        return this.posicaoAtual == 0;
    }

    private void atualizarPosicaoGrafica() {
        if (posicaoAtual + 1 >= caminho.getTamanho()) {
            Vertice destino = caminho.get(caminho.getTamanho() - 1);
            double tamanho = rectangle.getWidth();
            rectangle.setX(destino.getLongitude() - tamanho / 2);
            rectangle.setY(destino.getLatitude() - tamanho / 2);
            return;
        }
        Vertice origem = caminho.get(posicaoAtual);
        Vertice destino = caminho.get(posicaoAtual + 1);

        double x = origem.getLongitude() * (1 - progressoNaAresta) + destino.getLongitude() * progressoNaAresta;
        double y = origem.getLatitude() * (1 - progressoNaAresta) + destino.getLatitude() * progressoNaAresta;

        double tamanho = rectangle.getWidth();
        rectangle.setX(x - tamanho / 2);
        rectangle.setY(y - tamanho / 2);
        // resto do código permanece igual
    }


}
