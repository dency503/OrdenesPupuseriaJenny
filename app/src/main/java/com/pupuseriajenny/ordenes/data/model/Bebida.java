package com.pupuseriajenny.ordenes.data.model;

import lombok.Getter;

@Getter
public class Bebida {
    // Getters y setters
    private String nombre;
    private String precio;
    private int imagenResourceId;
    private int cantidad;

    public Bebida(String nombre, String precio, int imagenResourceId) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenResourceId = imagenResourceId;
        this.cantidad = 0; // Valor inicial de cantidad
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
