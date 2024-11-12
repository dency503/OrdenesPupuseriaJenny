package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.data.model.Bebida;
import com.pupuseriajenny.ordenes.ui.adapter.BebidaAdapter;
import com.pupuseriajenny.ordenes.ui.listener.BebidaActionsListener;

import java.util.ArrayList;
import java.util.List;

public class BebidaActivity extends AppCompatActivity implements BebidaActionsListener {
    private List<Bebida> bebidasSeleccionadas = new ArrayList<>();

    // Declaración de vistas
    private TextView txtTitulo, txtCantidad;
    private RecyclerView recyclerView;

    // Declaración de datos y adaptador
    private BebidaAdapter bebidaAdapter;
    private List<Bebida> bebidaList;
private Button btnDetalles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activar el modo EdgeToEdge
        setContentView(R.layout.activity_bebida); // Establecer el layout de la actividad
btnDetalles = findViewById(R.id.btnDetalles);
btnDetalles.setOnClickListener(view -> {

    Intent intent = new Intent(BebidaActivity.this,OrdenActivity.class);
    intent.putExtra("productoslista", (ArrayList<Bebida>) bebidasSeleccionadas);
    startActivity(intent);

    }
);
        // Inicializar vistas
        initializeViews();

        // Configurar datos y título
        setupBebidaList();

        // Configurar RecyclerView
        setupRecyclerView();

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
    }

    /**
     * Configura los datos de las bebidas y actualiza el título de la actividad.
     */
    private void setupBebidaList() {
        // Obtener la categoría pasada en el intent
        Intent intent = getIntent();
        String categoria = intent.getStringExtra("categoria");

        // Establecer el título de la actividad
        if (categoria != null) {
            txtTitulo.setText(categoria);
        }

        // Inicializar y llenar la lista de bebidas
        bebidaList = new ArrayList<>();
        bebidaList.add(new Bebida("Coca-Cola", 1.50, R.drawable.cuadro));
        bebidaList.add(new Bebida("Pepsi", 1.60, R.drawable.cuadro));
        bebidaList.add(new Bebida("Fanta", 1.40, R.drawable.cuadro));

        // Mostrar la cantidad total de productos
        txtCantidad.setText("Total de productos: " + bebidaList.size());
    }

    /**
     * Configura el RecyclerView con el adaptador y el layout manager.
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bebidaAdapter = new BebidaAdapter(bebidaList, this);
        recyclerView.setAdapter(bebidaAdapter);
    }

    /**
     * Método que se llama desde el adaptador para actualizar la lista de bebidas seleccionadas.
     * @param bebida Bebida cuyo estado de cantidad ha cambiado.
     */
    public void actualizarBebidasSeleccionadas(Bebida bebida) {
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
