package utils;

public class LogSistema {

    public int tempoAtual; // tempo de simulação
    public int totalVeiculosCriados;
    public int totalVeiculosFinalizados;
    public int totalVeiculosAtivos;
    public int totalSemaforos;
    public int ciclosSemaforosExecutados;

    public LogSistema(){
        this.tempoAtual = 0;
        this.totalVeiculosCriados = 0;
        this.totalVeiculosFinalizados = 0;
        this.totalVeiculosAtivos = 0;
        this.totalSemaforos = 0;
        this.ciclosSemaforosExecutados = 0;
    }
}
