package com.pupuseriajenny.ordenes.data.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // Lombok genera el constructor automáticamente
public class Producto implements Serializable {
    private int idProducto;
    private String nombreProducto;
    private double costoUnitarioProducto;
    private double precioProducto;
    private double subTotalProducto;
    private int imagenResourceId;
    private int cantidad;

    public Producto() {
        // Constructor vacío
    }

    // Getters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioProducto() {
        return precioProducto;  // Getter añadido
    }

    public int getImagenResourceId() {
        return imagenResourceId;  // Getter añadido
    }

    public int getIdProducto() {
        return idProducto;  // Getter añadido
    }

    // Setter para cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public void setCostoUnitarioProducto(double costoUnitarioProducto) {
        this.costoUnitarioProducto = costoUnitarioProducto;
    }
    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }
}
