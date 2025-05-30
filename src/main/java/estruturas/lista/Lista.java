package estruturas.lista;

import estruturas.No;
import lombok.Getter;

@Getter
public class Lista<Tipo>{
    public No<Tipo> head,tail;
    public int tamanho;

    public Lista(){
        this.head = null;
        this.tail = null;
        this.tamanho = 0;
    }

    public boolean addComPos(Tipo valor, int pos){
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

    public boolean add(Tipo valor){
        No<Tipo> novo = new No<>(valor);

        if(this.head == null || this.tamanho <= 0){
            this.head = novo;
            this.tail = novo;
            tamanho++;
            return true;
        }

        novo.setProx(this.head);
        this.head.setAnt(novo);
        this.head = novo;
        tamanho++;
        return true;
    }

    public Tipo remover(){

        if(this.head == null || this.tamanho <= 0){
            return null;
        }
        if(this.head == this.tail){
            No<Tipo> atual = this.head;
            this.head = null;
            this.tamanho--;

            return atual.getValor();
        }


        No<Tipo> atual = this.tail;
        this.tail.getAnt().setProx(null);
        this.tail.setAnt(atual.getAnt());
        atual.setAnt(null);
        tamanho--;
        return atual.getValor();
    }

    public No<Tipo> removerPorPosicao(int pos) {
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

    public Tipo get(int pos) {
        if (pos < 0 || pos >= tamanho) {
            return null;
        }

        No<Tipo> atual = this.head;

        for (int i = 0; i < pos ; i++) {
            atual = atual.getProx();
        }

        return atual.getValor();

    }

}
