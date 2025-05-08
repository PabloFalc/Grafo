package estruturas.fila;

import estruturas.No;

public class Pilha {


    No head;
    private int length;

    public Stack(){
        this.head = null;
        this.length = 0;
    }

    public boolean push (elemento){

        No node = new No(elemento);
        if(isEmpty()){
            this.head = node;
            this.length++;
            return  true;
        }
        node.prev = this.head;
        this.head.next = node;
        this.head = node;
        this.length++;
        return  true
    }

    public No pop(){
        No node = this.head;
        if(isEmpty()){
            throw new RuntimeException("Stack tá nula");
        }
        else if(size()== 1){
            this.head = null;
            this.length--;
            return node;
        }

        this.head = head.prev;
        this.head.next = null;
        node.prev = null;
        printStack();
        this.length--;
        return node;

    }

    publiceek(){
        return isEmpty() ? null : this.head.value;
    }

    public boolean isEmpty(){
        return size() == 0 ? true : false;
    }

    public int size(){
        return this.length;
    }


}
