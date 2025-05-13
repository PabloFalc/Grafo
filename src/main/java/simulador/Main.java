package simulador;

import estruturas.lista.Lista;
import gerador.GeradorMapa;
import grafo.Aresta;
import grafo.Grafo;

import grafo.Aresta;
import grafo.Vertice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import grafo.Vertice;
import grafo.Aresta;
import veiculo.veiculo;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static void main(String[] args) {
        GeradorMapa gerador = new GeradorMapa();
        launch(args);

        Grafo grafo = gerador.gerar("a");
        Lista<Aresta> arestaLista = grafo.getArestas();
        System.out.println(arestaLista.tamanho);

         Vertice v1 = new Vertice(100, 100, "A");
          Vertice v2 = new Vertice(200, 150, "B");
          Vertice v3 = new Vertice(350, 125, "C");
          Vertice v4 = new Vertice(400, 50, "D");

          Aresta a1 = new Aresta(v1, v2, 5, 0.1, false, 50);
          Aresta a2 = new Aresta(v2, v3, 10, 0.2, true, 60);
          Aresta a3 = new Aresta(v3, v4, 15, 0.3, false, 70);
          Aresta a4 = new Aresta(v4, v1, 20, 0.4, true, 80);
          Aresta a5 = new Aresta(v1, v3, 25, 0.5, false, 90);
          Aresta a6 = new Aresta(v2, v4, 30, 0.6, true, 100);




    }
  
    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();

        Vertice[] vertices = {v1, v2, v3, v4};

        // Criar arestas
        Aresta[] arestas = {a1, a2, a3, a4, a5, a6};

        Map<String, Circle> nodeMap = new HashMap<>();

        // Adicionar vértices ao Pane
        for (Vertice v : vertices) {
            Circle c = new Circle(v.getLongitude(), v.getLatitude(), 15, Color.ORANGE);
            c.setStroke(Color.BLACK);
            Text t = new Text(v.getLongitude() - 5, v.getLatitude() - 20, v.getId());
            pane.getChildren().addAll(c, t);
            nodeMap.put(v.getId(), c);
        }

        // Adicionar arestas ao Pane
        for (Aresta a : arestas) {
            Line line = new Line(a.getSource().getLongitude(), a.getSource().getLatitude(), a.getTarget().getLongitude(), a.getTarget().getLatitude());
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(2);
            pane.getChildren().add(0, line); // Adiciona atrás dos vértices
        }

        Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("Mapa do Grafo");
        stage.setScene(scene);
        stage.show();
    }

}
