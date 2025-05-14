package estruturas.pilha;

import estruturas.No;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pilha<Tipo> {
    private No<Tipo> topo;
    private int tamanho;

    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    public boolean add(Tipo elemento) {
        No<Tipo> novo = new No<>(elemento);
        novo.setProx(topo);
        topo = novo;
        tamanho++;
        return true;
    }

    public Tipo remover() {
        if (topo == null) return null;

        Tipo elemento = topo.getValor();
        topo = topo.getProx();
        tamanho--;
        return elemento;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public Tipo peek() {
        return topo != null ? topo.getValor() : null;
    }
}
