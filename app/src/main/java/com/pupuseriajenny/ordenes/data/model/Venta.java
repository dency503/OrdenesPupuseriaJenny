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

}
