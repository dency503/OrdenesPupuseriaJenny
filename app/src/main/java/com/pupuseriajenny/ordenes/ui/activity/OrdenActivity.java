package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class OrdenActivity extends AppCompatActivity {
    private ArrayList<Bebida> bebidasSeleccionadas;


    private RecyclerView recyclerView;

    // Declaración de datos y adaptador
    private BebidaAdapter bebidaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orden);
        recyclerView = findViewById(R.id.recyclerViewBebidas);
        Intent intent = getIntent();

        // Obtener la lista de bebidas pasadas como extra (ArrayList<Bebida>)
        if (intent != null && intent.hasExtra("productoslista")) {
            bebidasSeleccionadas = (ArrayList<Bebida>) intent.getSerializableExtra("productoslista");

            // Verificar si la lista fue recuperada correctamente
            if (bebidasSeleccionadas != null) {
                setupRecyclerView();
                // Aquí puedes usar la lista de bebidas seleccionadas como lo desees
                // Por ejemplo, puedes mostrar la lista en un RecyclerView, etc.
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
        //bebidaAdapter = new BebidaAdapter(bebidasSeleccionadas, OrdenActivity.this);
        recyclerView.setAdapter(bebidaAdapter);
    }
}