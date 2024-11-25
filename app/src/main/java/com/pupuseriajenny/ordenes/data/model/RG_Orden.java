package com.pupuseriajenny.ordenes.data.model;

public class RG_Orden {
    private int idOrden;               // ID de la orden
    private int idMesa;                // ID de la mesa
    private String clienteOrden;       // Nombre del cliente
    private String fechaOrden;         // Fecha de la orden
    private String tipoOrden;          // Tipo de orden (Llevar o Comer en restaurante)
    private String estadoOrden;        // Estado de la orden (Pendiente, Cancelada, Pagada)
    private String comentarioOrden;    // Comentarios adicionales

    // Constructor vacío (necesario para la serialización y deserialización de Retrofit)
    public RG_Orden() {}

    // Constructor con parámetros (si necesitas crear instancias de manera más directa)
    public RG_Orden(int idMesa, String clienteOrden, String fechaOrden, String tipoOrden, String estadoOrden, String comentarioOrden) {
        this.idMesa = idMesa;
        this.clienteOrden = clienteOrden;
        this.fechaOrden = fechaOrden;
        this.tipoOrden = tipoOrden;
        this.estadoOrden = estadoOrden;
        this.comentarioOrden = comentarioOrden;
    }

    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getClienteOrden() {
        return clienteOrden;
    }

    public void setClienteOrden(String clienteOrden) {
        this.clienteOrden = clienteOrden;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public String getComentarioOrden() {
        return comentarioOrden;
    }

    public void setComentarioOrden(String comentarioOrden) {
        this.comentarioOrden = comentarioOrden;
    }
}