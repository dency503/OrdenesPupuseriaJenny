package com.pupuseriajenny.ordenes.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Venta {
    private int idVenta;
    private int idEmpleado;
    private int idDetalleVenta;
    private double totalVenta;
    private String fechaVenta;

    // Constructor
    public Venta() {
        this.idVenta = idVenta;
        this.idEmpleado = idEmpleado;
        this.idDetalleVenta = idDetalleVenta;
        this.totalVenta = totalVenta;
        this.fechaVenta = fechaVenta;
    }

    // Getters y Setters para cada atributo
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
}
