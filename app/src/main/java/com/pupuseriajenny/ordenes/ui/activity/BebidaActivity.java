package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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
import com.pupuseriajenny.ordenes.data.model.Producto;
import com.pupuseriajenny.ordenes.ui.adapter.BebidaAdapter;
import com.pupuseriajenny.ordenes.ui.listener.BebidaActionsListener;
import com.pupuseriajenny.ordenes.utils.TokenUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaActivity extends AppCompatActivity implements BebidaActionsListener {
    private List<Producto> bebidasSeleccionadas = new ArrayList<>();  // Lista para las bebidas seleccionadas

    // Declaración de vistas
    private TextView txtTitulo, txtCantidad;
    private RecyclerView recyclerView;
    private Button btnDetalles;
    private BebidaAdapter bebidaAdapter;
    private List<Producto> productoList = new ArrayList<>();  // Lista de productos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activar el modo EdgeToEdge
        setContentView(R.layout.activity_bebida); // Establecer el layout de la actividad

        // Inicializar vistas
        initializeViews();

        // Configurar datos y título
        setupBebidaList();

        // Configurar RecyclerView


        // Recuperar el token desde SharedPreferences
        TokenUtil tokenUtil = new TokenUtil(this);
     // Recuperar la categoría desde el Intent
        Intent intent = getIntent();
        String categoria = intent.getStringExtra("categoria");

        if (categoria != null && tokenUtil.getToken() != null) {
            ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);

            // Llamada para obtener los productos por categoría
            Call<List<Producto>> call = apiService.obtenerProductosPorCategoria(categoria);

            call.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        productoList = response.body();

                        // Inicializa el adaptador solo después de recibir los productos
                        if (bebidaAdapter == null) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(BebidaActivity.this));
                            bebidaAdapter = new BebidaAdapter(productoList, BebidaActivity.this); // Inicializa el adaptador aquí
                            recyclerView.setAdapter(bebidaAdapter); // Asocia el adaptador al RecyclerView
                        } else {
                            bebidaAdapter.notifyDataSetChanged(); // Si el adaptador ya está inicializado, solo notifica cambios
                        }

                        // Mostrar la cantidad de productos
                       txtCantidad.setText("Total de productos: " + productoList.size());
                    } else {
                        Toast.makeText(BebidaActivity.this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<List<Producto>> call, Throwable t) {
                    Toast.makeText(BebidaActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Configurar el botón de detalles
        btnDetalles.setOnClickListener(view -> {
            Intent intent2 = new Intent(BebidaActivity.this, OrdenActivity.class);
            intent2.putExtra("productoslista", (ArrayList<Producto>) bebidasSeleccionadas);  // Se pasa la lista de productos seleccionados
            startActivity(intent2);
        });

        // Manejar los márgenes del sistema (barra de estado, navegación, etc.)
        handleSystemBarsInsets();
    }

    /**
     * Inicializa las vistas de la actividad.
     */
    private void initializeViews() {
        txtTitulo = findViewById(R.id.txtTitulo);
        txtCantidad = findViewById(R.id.txtCantidad);
        recyclerView = findViewById(R.id.recyclerViewBebidas);
        btnDetalles = findViewById(R.id.btnDetalles);
    }

    /**
     * Configura los datos de las bebidas y actualiza el título de la actividad.
     */
    private void setupBebidaList() {
        // Obtener la categoría pasada en el intent

    }

    /**
     * Configura el RecyclerView con el adaptador y el layout manager.
     */


    /**
     * Método que se llama desde el adaptador para actualizar la lista de bebidas seleccionadas.
     * @param bebida Producto cuyo estado de cantidad ha cambiado.
     */
    public void actualizarBebidasSeleccionadas(Producto bebida) {
        if (bebida.getCantidad() > 0) {
            if (!bebidasSeleccionadas.contains(bebida)) {
                bebidasSeleccionadas.add(bebida);
            }
        } else {
            bebidasSeleccionadas.remove(bebida);
        }

        // Actualiza el texto de cantidad para reflejar el total en bebidasSeleccionadas
        txtCantidad.setText("Total de productos seleccionados: " + bebidasSeleccionadas.size());
    }

    /**
     * Maneja los márgenes de los "system bars" (barra de estado y navegación).
     */
    private void handleSystemBarsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
