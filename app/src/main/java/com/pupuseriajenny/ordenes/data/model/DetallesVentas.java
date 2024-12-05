package com.pupuseriajenny.ordenes.data.model;

public class DetallesVentas {
    private int idDetalleVenta;
    private int idOrden;
    private int idProducto;
    private int cantidadDetalleVenta;
    private double subTotalDetalleVenta;

    // Constructor vacío (opcional)
    public DetallesVentas() {
    }

    // Getters y Setters

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidadDetalleVenta() {
        return cantidadDetalleVenta;
    }

    public void setCantidadDetalleVenta(int cantidadDetalleVenta) {
        this.cantidadDetalleVenta = cantidadDetalleVenta;
    }

    public double getSubTotalDetalleVenta() {
        return subTotalDetalleVenta;
    }

    public void setSubTotalDetalleVenta(double subTotalDetalleVenta) {
        this.subTotalDetalleVenta = subTotalDetalleVenta;
    }
}
