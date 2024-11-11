package com.pupuseriajenny.ordenes;

import com.pupuseriajenny.ordenes.DTOs.CategoriaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApiService {
    @GET("api/categoria")
    Call<CategoriaResponse> obtenerCategorias();
}
