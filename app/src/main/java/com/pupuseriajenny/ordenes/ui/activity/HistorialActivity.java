package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.ApiService;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.model.Orden;
import com.pupuseriajenny.ordenes.ui.adapter.HistorialAdapter;
import com.pupuseriajenny.ordenes.utils.JWTUtil;
import com.pupuseriajenny.ordenes.utils.TokenUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialActivity extends AppCompatActivity implements HistorialAdapter.OnOrdenInteractionListener {

    private TextView tvUsuario;
    private RecyclerView recyclerView;
    private HistorialAdapter historialAdapter;
    private List<Orden> listaOrdenes = new ArrayList<>();
    private ApiService apiService;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Inicializar vistas
        tvUsuario = findViewById(R.id.tvUsuario);
        btnAgregar = findViewById(R.id.btnNueva);
        recyclerView = findViewById(R.id.recyclerViewHistorial);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener el nombre del usuario desde el token
        TokenUtil tokenUtil = new TokenUtil(this);
        tvUsuario.setText(JWTUtil.obtenerNombre(tokenUtil.getToken()));

        // Crear instancia del servicio API
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Obtener las órdenes desde la API
        obtenerOrdenes();

        // Acción del botón "Nueva Orden"
        btnAgregar.setOnClickListener(view -> {
            Intent intent = new Intent(HistorialActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    // Método para obtener las órdenes desde la API
    private void obtenerOrdenes() {
        apiService.obtenerOrdenes().enqueue(new Callback<List<Orden>>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaOrdenes = response.body();
                    // Configurar el adaptador con los datos
                    if (historialAdapter == null) {
                        historialAdapter = new HistorialAdapter(listaOrdenes, HistorialActivity.this);
                        recyclerView.setAdapter(historialAdapter);
                    } else {
                        historialAdapter.notifyDataSetChanged();
                    }
                } else {
                    showToast("Error al obtener las órdenes.");
                }
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable t) {
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    // Mostrar mensajes con Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Método para cancelar una orden
    public void cancelarOrden(Orden orden) {
        // Primero, crear el objeto de la orden con el nuevo estado


        orden.setEstadoOrden("Cancelada");  // Actualizamos el estado a "Cancelada"

        // Llamamos al servicio API para actualizar el estado de la orden
        apiService.actualizarEstadoOrden(orden.getIdOrden(), orden).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Orden cancelada exitosamente");
                    // Actualizar la lista de órdenes después de la cancelación
                    obtenerOrdenes();
                } else {
                   tvUsuario.setText(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    // Implementación de la interfaz OnOrdenInteractionListener para editar y eliminar órdenes
    @Override
    public void onEditarOrden(Orden orden) {
        // Lógica para editar la orden

        // Recibir el ID de la orden y abrir la actividad de edición
        Intent intent = new Intent(this, EditarOrdenActivity.class);
        intent.putExtra("ID_ORDEN", orden.getIdOrden());  // Pasar el ID de la orden
        startActivity(intent);

        //Toast.makeText(this, "Editar orden de: " + orden.getClienteOrden(), Toast.LENGTH_SHORT).show();
        // Diálogo de confirmación para eliminar
        new AlertDialog.Builder(this)
                .setTitle("Editar Orden")
                .setMessage("¿Desea editar la orden de " + orden.getClienteOrden() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {


                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onEliminarOrden(Orden orden) {
        // Diálogo de confirmación para eliminar
        new AlertDialog.Builder(this)
                .setTitle("Cancelar Orden")
                .setMessage("¿Desea cancelar la orden de " + orden.getClienteOrden() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    // Llamar al método de cancelación de orden al eliminarla
                    cancelarOrden(orden);
                    obtenerOrdenes();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
