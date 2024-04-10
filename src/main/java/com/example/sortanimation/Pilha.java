package com.example.sortanimation;

public class Pilha {
    No topo;

    public Pilha() {

    }
    public Pilha(Pilha pilha) {
        topo = pilha.topo;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public void push(int valor) {
        topo = new No(valor, topo);
    }

    public int pop() {
        int valor = topo.getValor();
        topo = topo.getProx();
        return valor;
    }
}
