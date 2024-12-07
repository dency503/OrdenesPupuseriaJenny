package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.ApiService;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.model.DetallesVentas;
import com.pupuseriajenny.ordenes.data.model.Orden;
import com.pupuseriajenny.ordenes.data.model.Producto;
import com.pupuseriajenny.ordenes.ui.adapter.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarOrdenActivity extends AppCompatActivity {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private Button btnGuardar;
    private int idOrden;
    private List<Producto> productoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_orden);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBebidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el adaptador
        ProductoAdapter productoAdapter = new ProductoAdapter(productoList);
        recyclerView.setAdapter(productoAdapter);

        // Obtener productos de la orden
        apiService = RetrofitClient.getClient(this).create(ApiService.class);
        idOrden = getIntent().getIntExtra("idOrden", -1);
        if (idOrden == -1) {
            Log.e("EditarOrdenActivity", "ID de la orden no válido.");
            Toast.makeText(this, "ID de orden inválido", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.d("EditarOrdenActivity", "ID de la orden recibida: " + idOrden);
            obtenerProductosOrden(idOrden, productoAdapter);
        }
        // Configurar el botón Guardar
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(view -> cambiarEstadoOrden(idOrden, "Pagada"));
    }

    private void obtenerProductosOrden(int idOrden, ProductoAdapter adapter) {
        Call<List<DetallesVentas>> call = apiService.obtenerDetallesPorOrden(idOrden);
        call.enqueue(new Callback<List<DetallesVentas>>() {

            @Override
            public void onResponse(Call<List<DetallesVentas>> call, Response<List<DetallesVentas>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DetallesVentas> detallesVentas = response.body();
                    productoList.clear();

                    // Convertir cada DetallesVentas a Producto
                    for (DetallesVentas detalles : detallesVentas) {
                        if (detalles.getNombreProducto() == null || detalles.getCantidad() <= 0) {
                            Log.e("EditarOrdenActivity", "Datos inválidos en DetallesVentas: " + detalles.toString());
                            continue;
                        }
                        Producto producto = new Producto();
                        producto.setNombreProducto(detalles.getNombreProducto());
                        producto.setCantidad(detalles.getCantidad());
                        producto.setPrecioProducto(detalles.getPrecio());
                        producto.setSubTotalProducto(detalles.getSubTotal());
                        productoList.add(producto);
                    }

                    Log.d("EditarOrdenActivity", "Productos obtenidos: " + detallesVentas.size());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("EditarOrdenActivity", "Error en la respuesta: " + response.code() + " - " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("EditarOrdenActivity", "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(EditarOrdenActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallesVentas>> call, Throwable t) {
                // Manejar error de la llamada
                Log.e("EditarOrdenActivity", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(EditarOrdenActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void cambiarEstadoOrden(int idOrden, String nuevoEstado) {
        // Crear objeto Orden con el nuevo estado
        Orden ordenActualizada = new Orden();
        ordenActualizada.setEstadoOrden(nuevoEstado);

        // Realizar la solicitud para actualizar el estado de la orden
        apiService.actualizarEstadoOrden(idOrden, ordenActualizada).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarOrdenActivity.this, "Orden marcada como Pagada", Toast.LENGTH_SHORT).show();
                    finish(); // Finalizar la actividad o redirigir
                } else {
                    Toast.makeText(EditarOrdenActivity.this, "Error al actualizar la orden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditarOrdenActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
