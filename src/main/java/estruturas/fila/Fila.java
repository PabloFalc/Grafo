package estruturas.fila;

import estruturas.No;

public class Fila<Tipo> {

    public No<Tipo> head;
    public No<Tipo> tail;

    public Fila(){
        this.head = null;
        this.tail = null;
    }



    public boolean add(Tipo elemento){
        if(this.head == null){
           No<Tipo> no = new No<>(elemento);

            this.tail = no;
            this.head = no;
            return true;
        }
       No<Tipo> no = new No<Tipo>(elemento);

        no.setProx(this.head);
        this.head = no;
        return true;

    }
    public boolean remover(){

        if(this.head == null){
            return  false;
        }
        if(this.head == this.tail){
           No<Tipo> elemento = this.head;

            this.head = null;
            this.tail =null;
            return true;
        }
       No<Tipo> elemento = this.tail;
       No<Tipo> atual = this.head;

        while (atual.getProx() != elemento){
            atual = atual.getProx();
        }

        atual.setProx(null);
        this.tail = atual;

        return true;
    }

}
