package estruturas.fila;

import estruturas.No;
import lombok.Getter;

@SuppressWarnings("DuplicatedCode")
@Getter
public class FilaLimite<Tipo> {


    private No<Tipo> head;
    private No<Tipo> tail;
    private int tamanho;
    private final int limite;

    public FilaLimite(int  limite) {
        this.head = null;
        this.tail = null;
        this.tamanho = 0;
        this.limite = limite;
    }

    public boolean add(Tipo elemento) {
        No<Tipo> no = new No<>(elemento);

        if (isEmpty()) {
            this.head = no;
            this.tail = no;
        }
        else if(isFull()){
            return false;
        }
        else {
            no.setProx(this.head);
            this.head = no;
        }
            tamanho++;
        return true;
    }

    public Tipo remover() {
        if (isEmpty()) {
            return null;
        }

        if (this.head == this.tail) {
            Tipo valor = this.head.getValor();
            this.head = null;
            this.tail = null;
            tamanho--;
            return valor;
        }

        No<Tipo> atual = this.head;
        while (atual.getProx() != this.tail) {
            atual = atual.getProx();
        }

        Tipo elemento = this.tail.getValor();
        atual.setProx(null);
        this.tail = atual;
        tamanho--;
        return elemento;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public boolean isFull(){
        return this.head == this.tail;
    }

}
