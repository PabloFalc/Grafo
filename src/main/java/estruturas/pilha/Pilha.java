package estruturas.pilha;

import estruturas.No;
import lombok.Getter;

@Getter
public class Pilha<Tipo> {
    No<Tipo> head;
    private int tamanho;

    public Pilha(){
        this.head = null;
        this.tamanho = 0;
    }

    public boolean add(Tipo elemento){

        No<Tipo> no = new No<Tipo>(elemento);
        if(isEmpty()){
            this.head = no;
            this.tamanho++;
            return true;
        }
        no.setAnt(this.head);
        this.head.setProx(no);
        this.head = no;
        this.tamanho++;
        return true;
    }

    public Tipo remover(){
        No<Tipo> no = this.head;
        if(isEmpty()){
            return null;
        }
        else if(size()== 1){
            this.head = null;
            this.tamanho--;
            return no.getValor();
        }
        this.head = head.getAnt();
        this.head.setProx(null);
        no.setAnt(null);
        this.tamanho--;
        return no.getValor();

    }

    public Tipo ultimoElemento(){
        return isEmpty() ? null : this.head.getValor();
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return this.tamanho;
    }
}
