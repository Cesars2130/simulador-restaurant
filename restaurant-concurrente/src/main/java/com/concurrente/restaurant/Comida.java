package com.concurrente.restaurant;

public class Comida {
    private Orden orden;

    public Comida(Orden orden) {
        this.orden = orden;
    }

    public Orden getOrden() {
        return orden;
    }
}

