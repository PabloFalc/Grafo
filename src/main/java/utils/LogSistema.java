package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogSistema {

    public int tempoTotal; // tempo de simulação
    public int totalVeiculosCriados;
    public int totalVeiculosFinalizados;
    public int totalVeiculosAtivos;
    public int totalSemaforos;
    public int ciclosSemaforosExecutados;
    public int veiculosDefeituosos;

    public LogSistema(){
        this.tempoTotal = 0;
        this.totalVeiculosCriados = 0;
        this.totalVeiculosFinalizados = 0;
        this.totalVeiculosAtivos = 0;
        this.totalSemaforos = 0;
        this.ciclosSemaforosExecutados = 0;
        this.veiculosDefeituosos = 0;
    }

    public void createTxt(){
        String dirPath = "logs/sys";
        String filePath = dirPath + "/log_sistema.txt";

        // Cria o diretório se não existir
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Log do Sistema de Simulação");
            writer.println("----------------------------");
            writer.println("Tempo total de simulação: " + tempoTotal);
            writer.println("Total de veículos criados: " + totalVeiculosCriados);
            writer.println("Total de veículos finalizados: " + totalVeiculosFinalizados);
            writer.println("Total de veículos ativos: " + totalVeiculosAtivos);
            writer.println("Total de semáforos: " + totalSemaforos);
            writer.println("Ciclos de semáforos executados: " + ciclosSemaforosExecutados);
            writer.println("Veiculos com defeito: "+ veiculosDefeituosos);
            writer.println("----------------------------");
            writer.println("Fim do log.");
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo de log: " + e.getMessage());
        }
    }
}
