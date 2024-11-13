package com.pupuseriajenny.ordenes.ui.activity;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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
import com.pupuseriajenny.ordenes.data.model.Bebida;
import com.pupuseriajenny.ordenes.data.model.Producto;
import com.pupuseriajenny.ordenes.ui.adapter.BebidaAdapter;
import com.pupuseriajenny.ordenes.ui.listener.BebidaActionsListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdenActivity extends AppCompatActivity implements BebidaActionsListener {
    private ArrayList<Producto> bebidasSeleccionadas;
    private Button btnEnviar, btnCancelar;
    private TextView txtTotal;
    private RecyclerView recyclerView;

    private BebidaAdapter bebidaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orden);

        // Inicializar vistas
        btnEnviar = findViewById(R.id.btnEnviar);
        btnCancelar = findViewById(R.id.btnCancelar);
        txtTotal = findViewById(R.id.txtCantidad); // Asegúrate de tener un TextView en el layout con este ID
        recyclerView = findViewById(R.id.recyclerViewBebidas);

        Intent intent = getIntent();
        btnCancelar.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, HomeActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        });

        // Obtener la lista de bebidas seleccionadas
        if (intent != null && intent.hasExtra("productoslista")) {
            bebidasSeleccionadas = (ArrayList<Producto>) intent.getSerializableExtra("productoslista");
            if (bebidasSeleccionadas != null) {
                setupRecyclerView();
                actualizarTotal(); // Calcular el total al inicio
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bebidaAdapter = new BebidaAdapter(bebidasSeleccionadas, this);
        recyclerView.setAdapter(bebidaAdapter);
    }

    @Override
    public void actualizarBebidasSeleccionadas(Producto bebida) {
        actualizarTotal();
    }

    /**
     * Calcula y muestra el total de las bebidas seleccionadas.
     */
    private void actualizarTotal() {
        float total = 0;

        for (Producto bebida : bebidasSeleccionadas) {
            if (bebida.getCantidad() > 0) {
                double precio = bebida.getPrecioProducto();
                total += precio * bebida.getCantidad();
            }
        }
        // Mostrar el total en el TextView (formatear como "$0.00")
        txtTotal.setText(String.format("$%.2f", total));
    }
}
