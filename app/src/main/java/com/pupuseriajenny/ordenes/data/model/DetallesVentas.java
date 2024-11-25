package com.pupuseriajenny.ordenes.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetallesVentas {
    private int idDetalleVenta;
    private int idOrden;
    private int idProducto;
    private int cantidadDetalleVenta;
    private double subTotalDetalleVenta;

}
