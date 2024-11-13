package com.pupuseriajenny.ordenes;

import com.pupuseriajenny.ordenes.DTOs.CategoriaResponse;
import com.pupuseriajenny.ordenes.data.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/categoria")
    Call<CategoriaResponse> obtenerCategorias();
    @GET("api/categoria/productos/{categoria}")
    Call<List<Producto>> obtenerProductosPorCategoria(@Path("categoria") String categoria);
}
