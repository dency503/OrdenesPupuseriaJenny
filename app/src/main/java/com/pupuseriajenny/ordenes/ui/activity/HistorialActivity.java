package com.pupuseriajenny.ordenes.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.ApiService;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.model.Orden;
import com.pupuseriajenny.ordenes.ui.adapter.HistorialAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistorialAdapter historialAdapter;
    private List<Orden> listaOrdenes = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerView = findViewById(R.id.recyclerViewHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Llamar a la API para obtener las órdenes
        obtenerOrdenes();

        // Configurar el adaptador
        historialAdapter = new HistorialAdapter(listaOrdenes, orden -> {
            // Aquí puedes manejar el clic en cada orden
            // Por ejemplo, ir a una pantalla de detalles de la orden o editarla
            Toast.makeText(this, "Orden seleccionada: " + orden.getIdOrden(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(historialAdapter);
    }

    private void obtenerOrdenes() {
        apiService.obtenerOrdenes().enqueue(new Callback<List<Orden>>() {
            @Override
            public void onResponse(Call<List<Orden>> call, Response<List<Orden>> response) {
                if (response.isSuccessful()) {
                    listaOrdenes = response.body();
                    historialAdapter.notifyDataSetChanged();
                } else {
                    showToast("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<List<Orden>> call, Throwable t) {
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

