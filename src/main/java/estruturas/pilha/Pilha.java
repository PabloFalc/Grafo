package estruturas.pilha;

import estruturas.No;

public class Pilha<Tipo> {
    No<Tipo> head;
    private int length;

    public Pilha(){
        this.head = null;
        this.length = 0;
    }

    public boolean push(Tipo elemento){

        No<Tipo> no = new No<Tipo>(elemento);
        if(isEmpty()){
            this.head = no;
            this.length++;
            return true;
        }
        no.setAnt(this.head);
        this.head.setProx(no);
        this.head = no;
        this.length++;
        return true;
    }

    public boolean pop(){
        No<Tipo> no = this.head;
        if(isEmpty()){
            return false;
        }
        else if(size()== 1){
            this.head = null;
            this.length--;
            return true;
        }

        this.head = head.getAnt();
        this.head.setProx(null);
        no.setAnt(null);
        this.length--;
        return true;

    }

    public Tipo peek(){
        return isEmpty() ? null : this.head.getValor();
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return this.length;
    }

}
