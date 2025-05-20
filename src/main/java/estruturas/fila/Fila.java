package estruturas.fila;

import estruturas.No;
import estruturas.lista.Lista;

public class Fila<Tipo> {

    private No<Tipo> head;
    private No<Tipo> tail;

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
            return elemento;
        }

        No<Tipo> atual = this.head;
        while (atual.getProx() != this.tail) {
            atual = atual.getProx();
        }

        Tipo elemento = this.tail.getValor();
        atual.setProx(null);
        this.tail = atual;
        return elemento;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public Lista<Tipo> toList(){
        Lista<Tipo> lista = new Lista<>();
        No<Tipo> no = this.head;
        while(no.getProx() != null){
            no = no.getProx();
            lista.add(no.getValor());
        }

        return  lista;
    }
}
