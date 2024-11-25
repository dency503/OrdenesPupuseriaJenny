package com.pupuseriajenny.ordenes.data.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Producto implements Serializable {
    private int idProducto;
    private String nombreProducto;
    private double costoUnitarioProducto;
    private double precioProducto;

    private int imagenResourceId;
    private int cantidad;

    // Getters y setters para cada campo
}