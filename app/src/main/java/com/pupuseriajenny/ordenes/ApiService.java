package com.pupuseriajenny.ordenes;

import com.pupuseriajenny.ordenes.DTOs.CategoriaResponse;
import com.pupuseriajenny.ordenes.DTOs.LoginResponse;
import com.pupuseriajenny.ordenes.data.model.DetallesVentas;
import com.pupuseriajenny.ordenes.data.model.DetallesVentasResponse;
import com.pupuseriajenny.ordenes.data.model.Orden;
import com.pupuseriajenny.ordenes.data.model.Producto;
import com.pupuseriajenny.ordenes.data.model.RG_Orden;
import com.pupuseriajenny.ordenes.data.model.Venta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/categoria")
    Call<CategoriaResponse> obtenerCategorias();
    @GET("api/categoria/productos/{categoria}")
    Call<List<Producto>> obtenerProductosPorCategoria(@Path("categoria") String categoria);

    // Insertar venta
    @POST("api/venta")
    Call<Venta> insertarVenta(@Body Venta venta);
    @GET("/api/Orden/pendientes/con-mesa")
    Call<List<Orden>> obtenerOrdenes();
    // Insertar detalle de venta
    @POST("api/DetalleVenta")
    Call<DetallesVentasResponse> insertarDetalleVenta(@Body DetallesVentas detalleVenta);

    // Obtener detalles de venta por orden pendiente
    @GET("api/detalleventa/orden/{idOrden}")
    Call<List<DetallesVentas>> obtenerDetallesPorOrden(@Path("idOrden") int idOrden);
    @POST("api/detalleventa")
    Call<DetallesVentas> actualizarDetalleVenta(@Body DetallesVentas detallesVentas);
    @POST("api/orden")
    Call<Integer> insertarOrden(@Body RG_Orden orden);
    @POST("auth/renew-token") // Suponiendo que esta es la ruta para renovar el token
    Call<LoginResponse> renovarToken(@Header("Authorization") String token);
}
