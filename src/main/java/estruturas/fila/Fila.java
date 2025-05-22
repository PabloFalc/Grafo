package estruturas.fila;

import estruturas.No;
import estruturas.lista.Lista;
import lombok.Getter;

@Getter
public class Fila<Tipo> {

    private No<Tipo> head;
    private No<Tipo> tail;
    private int tamanho;

    public Fila() {
        this.head = null;
        this.tail = null;
    }

    public boolean add(Tipo elemento) {
        No<Tipo> no = new No<>(elemento);

        if (this.head == null) {
            this.head = no;
            this.tail = no;
        } else {
            no.setProx(this.head);
            this.head = no;
        }
        tamanho++;
        return true;
    }

    public Tipo remover() {
        if (this.head == null) {
            return null;
        }

        if (this.head == this.tail) {
            Tipo elemento = this.head.getValor();
            this.head = null;
            this.tail = null;
            tamanho--;
            return elemento;
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

    public Lista<Tipo> toList(){
        Lista<Tipo> lista = new Lista<>();
        No<Tipo> no = this.head;

        for (int i = 0; i < tamanho; i++) {
            if(no.getValor() == null){
                break;
            }
            lista.add(no.getValor());
            no = no.getProx();
        }

        return  lista;
    }

    public Tipo get(int pos) {
        if (pos < 0 || pos >= this.tamanho) {
            return null;
        }

        No<Tipo> atual = this.head;

        for (int i = 0; i < pos ; i++) {
            atual = atual.getProx();
        }

        return atual.getValor();
    }
}
