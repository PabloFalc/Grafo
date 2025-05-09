package simulador;

import grafo.gerador.GeradorMapa;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        GeradorMapa gerador = new GeradorMapa();
        gerador.gerar();



    }

    @Override
    public void start(Stage primaryStage) {
        // Criar um texto
        Label label = new Label("Simulador");

        // Colocar o texto no layout
        StackPane root = new StackPane(label);

        // Criar a cena
        Scene scene = new Scene(root, 400, 300);

        // Configurar e mostrar a janela
        primaryStage.setTitle("Simulador");
        primaryStage.setScene(scene);
        primaryStage.show(); // 🔥 Agora a janela aparece
    }
}
