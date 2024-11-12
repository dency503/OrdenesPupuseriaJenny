package com.pupuseriajenny.ordenes.data.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bebida  implements Serializable {
    // Getters y setters
    private String nombre;
    private double precio;
    private int imagenResourceId;
    private int cantidad;

    public Bebida(String nombre, double precio, int imagenResourceId) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenResourceId = imagenResourceId;
        this.cantidad = 0; // Valor inicial de cantidad
    }


}
