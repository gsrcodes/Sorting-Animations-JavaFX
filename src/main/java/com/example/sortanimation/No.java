package com.example.sortanimation;

public class No {
    int valor;
    No prox;

    public No(int valor, No prox) {
        this.valor = valor;
        this.prox = prox;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
