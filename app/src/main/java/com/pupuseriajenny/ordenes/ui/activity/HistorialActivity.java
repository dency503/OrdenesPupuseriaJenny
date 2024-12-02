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

    // Implementación de la interfaz OnOrdenInteractionListener
    @Override
    public void onEditarOrden(Orden orden) {
        // Lógica para editar la orden
        Toast.makeText(this, "Editar orden de: " + orden.getClienteOrden(), Toast.LENGTH_SHORT).show();
        // Aquí puedes abrir un diálogo o una nueva actividad para editar la orden
    }

    @Override
    public void onEliminarOrden(Orden orden) {
        // Diálogo de confirmación para eliminar
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Orden")
                .setMessage("¿Desea eliminar la orden de " + orden.getClienteOrden() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Eliminar la orden de la lista y notificar al adaptador
                    listaOrdenes.remove(orden);
                    historialAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
