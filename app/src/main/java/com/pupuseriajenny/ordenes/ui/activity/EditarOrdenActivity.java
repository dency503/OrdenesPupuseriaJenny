package com.pupuseriajenny.ordenes.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pupuseriajenny.ordenes.ApiService;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.model.Orden;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarOrdenActivity extends AppCompatActivity {
    private EditText etClienteOrden, etFechaOrden, etTipoOrden, etEstadoOrden, etComentarioOrden;
    private Button btnGuardar;
    private ApiService apiService;
    private int idOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_orden);

        // Obtener el ID de la orden desde el Intent
        idOrden = getIntent().getIntExtra("ID_ORDEN", -1);

        // Inicializar vistas
        etClienteOrden = findViewById(R.id.etClienteOrden);
        etFechaOrden = findViewById(R.id.etFechaOrden);
        etTipoOrden = findViewById(R.id.etTipoOrden);
        etEstadoOrden = findViewById(R.id.etEstadoOrden);
        etComentarioOrden = findViewById(R.id.etComentarioOrden);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Obtener los datos de la orden
        obtenerDatosOrden(idOrden);

        // Configurar el botón guardar
        btnGuardar.setOnClickListener(v -> guardarEdicion());
    }

    private void obtenerDatosOrden(int idOrden) {
        // Llamar al endpoint que obtendrá los datos de la orden
        apiService.obtenerOrdenPorId(idOrden).enqueue(new Callback<Orden>() {
            @Override
            public void onResponse(Call<Orden> call, Response<Orden> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Orden orden = response.body();
                    // Llenar los campos con los datos de la orden
                    etClienteOrden.setText(orden.getClienteOrden());
                    etFechaOrden.setText(orden.getFechaOrden());
                    etTipoOrden.setText(orden.getTipoOrden());
                    etEstadoOrden.setText(orden.getEstadoOrden());
                    etComentarioOrden.setText(orden.getComentarioOrden());
                } else {
                    Toast.makeText(EditarOrdenActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Orden> call, Throwable t) {
                Toast.makeText(EditarOrdenActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarEdicion() {
        // Crear el objeto de la orden con los datos editados
        Orden ordenEditada = new Orden();
        ordenEditada.setIdOrden(idOrden);
        ordenEditada.setClienteOrden(etClienteOrden.getText().toString());
        ordenEditada.setFechaOrden(etFechaOrden.getText().toString());
        ordenEditada.setTipoOrden(etTipoOrden.getText().toString());
        ordenEditada.setEstadoOrden(etEstadoOrden.getText().toString());
        ordenEditada.setComentarioOrden(etComentarioOrden.getText().toString());

        // Hacer la solicitud de actualización al servidor
        apiService.actualizarOrden(idOrden, ordenEditada).enqueue(new Callback<Orden>() {
            @Override
            public void onResponse(Call<Orden> call, Response<Orden> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarOrdenActivity.this, "Orden actualizada exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad
                } else {
                    Toast.makeText(EditarOrdenActivity.this, "Error al actualizar la orden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Orden> call, Throwable t) {
                Toast.makeText(EditarOrdenActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}