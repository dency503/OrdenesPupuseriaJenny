package com.pupuseriajenny.ordenes.data.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Orden {
    // Getters y setters
    private int idOrden;
    private String nombreCliente;
    private String fechaOrden;
    private String tipoOrden;
    private String estadoOrden;
    private String comentarioOrden;

}
