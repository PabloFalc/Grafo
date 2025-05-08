package estruturas.lista;

import estruturas.No;

public class Lista<Tipo>{
    public No<Tipo> head,tail;
    public int tamanho;

    public Lista(){
        this.head = null;
        this.tail = null;
        this.tamanho = 0;
    }

    public boolean add(Tipo valor, int pos){
        if (pos < 0 || pos > tamanho) {
            return false;
        }
        No<Tipo> novo = new No<>(valor);

        if(pos == 0) {
            if (this.head == null) {
                this.head = novo;
                this.tail = novo;
            }else{
                novo.setProx(this.head);
                this.head.setAnt(novo);
                this.head = novo;
            }
        }else if (pos == tamanho) {
            novo.setAnt(this.tail);
            this.tail.setProx(novo);
            this.tail = novo;
        }else{
            No<Tipo> atual = this.head;
            for (int i = 0; i < pos - 1; i++) {
                atual = atual.getProx();
            }
            novo.setProx(atual.getProx());
            novo.setAnt(atual);
            atual.getProx().setAnt(novo);
            atual.setProx(novo);
        }
        tamanho++;
        return true;
    }
    public void imprimir() {
        No<Tipo> atual = this.head;
        No<Tipo> atual2 = this.tail;
        System.out.print("head -> ");

        while (atual != null) {
            System.out.print(atual.getValor() + " -> ");
            atual = atual.getProx();
        }
        System.out.println("null");
        System.out.print("tail -> ");
        for (int i = tamanho - 1; i >= 0; i--) {
            System.out.print(atual2.getValor() + " -> ");
            atual2 = atual2.getAnt();
        }
        System.out.println("null");

    }

    public No<Tipo> remover(int pos) {
        if (pos < 0 || pos >= tamanho) {
            return null;
        }
        No<Tipo> removido;
        if (pos == 0) {
            removido = this.head;
            if (tamanho == 1) {
                this.head = null;
                this.tail = null;
            }else{
                this.head = removido.getProx();
                removido.setProx(null);
                this.head.setAnt(null);
            }
        }else if (pos == tamanho - 1) {
            removido = this.tail;
            this.tail = removido.getAnt();
            this.tail.setProx(null);
            removido.setAnt(null);
        }
        else {
            No<Tipo> atual = this.head;
            for (int i = 0; i < pos - 1; i++) {
                atual = atual.getProx();
            }
            removido = atual.getProx();
            atual.setProx(removido.getProx());
            removido.getProx().setAnt(removido.getAnt());
            removido.setAnt(null);
            removido.setProx(null);
        }
        tamanho--;
        return removido;
    }
}
