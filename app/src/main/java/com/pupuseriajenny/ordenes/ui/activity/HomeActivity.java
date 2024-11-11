package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pupuseriajenny.ordenes.CategoriaApiService;
import com.pupuseriajenny.ordenes.DTOs.CategoriaResponse;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private GridLayout gridLayoutCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar GridLayout
        gridLayoutCategorias = findViewById(R.id.gridLayoutCategorias);

        // Llamar al servicio de Retrofit para obtener las categorías
        obtenerCategorias();
    }

    private void obtenerCategorias() {
        // Obtener el token de SharedPreferences (si es necesario para autenticar la solicitud)
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);

        if (token != null) {
            // Crear el servicio API
            CategoriaApiService apiService = RetrofitClient.getClient(getString(R.string.base_url), token).create(CategoriaApiService.class);

            // Llamada para obtener las categorías
            Call<CategoriaResponse> call = apiService.obtenerCategorias();

            call.enqueue(new Callback<CategoriaResponse>() {
                @Override
                public void onResponse(Call<CategoriaResponse> call, Response<CategoriaResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CategoriaResponse categoriaResponse = response.body();
                        List<String> categorias = categoriaResponse.getCategoria();

                        if (categorias != null && !categorias.isEmpty()) {
                            // Recorrer las categorías
                            agregarBotonesCategorias(categoriaResponse);
                            for (String categoria : categorias) {
                                
                                Log.d("Categoria", categoria);  // Esto debería imprimir "Bebidas" y "Pupusas"
                            }
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "No se encontraron categorías", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CategoriaResponse> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Error al cargar las categorías", Toast.LENGTH_SHORT).show();
                    Log.e("HomeActivity", "Error en la llamada a la API", t);
                }
            });
        } else {
            Toast.makeText(this, "Token no encontrado. Por favor, inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void agregarBotonesCategorias(CategoriaResponse categoriaResponse) {
        // Limpiar el GridLayout antes de agregar los nuevos botones
        gridLayoutCategorias.removeAllViews();

        // Obtener la lista de categorías desde el objeto CategoriaResponse
        List<String> categorias = categoriaResponse.getCategoria();

        if (categorias != null && !categorias.isEmpty()) {
            // Recorrer la lista de categorías
            for (String categoria : categorias) {
                // Crear un botón para cada categoría
                Button button = new Button(this);
                button.setText(categoria); // Aquí usamos el nombre de la categoría
                button.setTextSize(16);
                button.setBackgroundTintList(getResources().getColorStateList(R.color.Gray));
Toast.makeText(this,categoria,Toast.LENGTH_SHORT).show();
                // Configurar el tamaño del botón
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = GridLayout.LayoutParams.MATCH_PARENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.setMargins(16, 16, 16, 16); // Márgenes alrededor del botón
                button.setLayoutParams(params);

                // Añadir el botón al GridLayout
                gridLayoutCategorias.addView(button);

                // Configurar el clic del botón
                button.setOnClickListener(v -> {
                    // Manejo del clic en cada categoría, por ejemplo, abrir una nueva actividad
                    Toast.makeText(HomeActivity.this, "Categoría seleccionada: " + categoria, Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

}
