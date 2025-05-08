package estruturas;

public class No<Tipo> {

    private Tipo valor;
    private No<Tipo> prox;
    private No<Tipo> ant;

    No(Tipo valor) {
        this.valor = valor;
        this.prox = null;
        this.ant = null;
    }
}
