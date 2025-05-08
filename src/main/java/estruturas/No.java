package estruturas;

public class No<Tipo> {

    private Tipo valor;
    private No<Tipo> prox;
    private No<Tipo> ant;

    public No(Tipo valor) {
        this.valor = valor;
        this.prox = null;
        this.ant = null;
    }

    public Tipo getValor() {
        return valor;
    }

    public void setValor(Tipo valor) {
        this.valor = valor;
    }

    public No<Tipo> getAnt() {
        return ant;
    }

    public void setAnt(No<Tipo> ant) {
        this.ant = ant;
    }

    public No<Tipo> getProx() {
        return prox;
    }

    public void setProx(No<Tipo> prox) {
        this.prox = prox;
    }
}
