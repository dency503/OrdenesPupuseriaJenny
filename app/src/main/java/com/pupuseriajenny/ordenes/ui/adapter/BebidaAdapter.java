package com.pupuseriajenny.ordenes.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.data.model.Bebida;
import com.pupuseriajenny.ordenes.ui.activity.BebidaActivity;

import java.util.List;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaAdapter.BebidaViewHolder> {

    private final List<Bebida> bebidaList;
    private final BebidaActivity bebidaActivity;

    public BebidaAdapter(List<Bebida> bebidaList, BebidaActivity bebidaActivity) {
        this.bebidaList = bebidaList;
        this.bebidaActivity = bebidaActivity; // Inicializar referencia
    }

    @NonNull
    @Override
    public BebidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bebida, parent, false);
        return new BebidaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BebidaViewHolder holder, int position) {
        Bebida bebida = bebidaList.get(position);

        // Configurar los datos en la vista
        holder.nombreBebida.setText(bebida.getNombre());
        holder.precioBebida.setText(bebida.getPrecio());
        holder.imagenBebida.setImageResource(bebida.getImagenResourceId());
        holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));

        // Configurar el botón para aumentar la cantidad
        holder.aumentarCantidad.setOnClickListener(v -> {
            int cantidadActual = bebida.getCantidad();
            bebida.setCantidad(cantidadActual + 1);
            holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));

            // Notificar a la actividad sobre el cambio
            bebidaActivity.actualizarBebidasSeleccionadas(bebida);
        });

        // Configurar el botón para restar la cantidad
        holder.restarCantidad.setOnClickListener(v -> {
            int cantidadActual = bebida.getCantidad();
            if (cantidadActual > 0) { // Evitar cantidad menor a 1
                bebida.setCantidad(cantidadActual - 1);
                holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));

                // Notificar a la actividad sobre el cambio
                bebidaActivity.actualizarBebidasSeleccionadas(bebida);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bebidaList.size();
    }

    // ViewHolder interno para el adaptador
    public static class BebidaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreBebida;
        private final TextView precioBebida;
        private final ImageView imagenBebida;
        private final TextView cantidadBebida;
        private final Button aumentarCantidad;
        private final Button restarCantidad;

        public BebidaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreBebida = itemView.findViewById(R.id.tvtBebida);
            precioBebida = itemView.findViewById(R.id.tvtPrecio);
            imagenBebida = itemView.findViewById(R.id.imgBebida);
            cantidadBebida = itemView.findViewById(R.id.cantidadBebida);
            aumentarCantidad = itemView.findViewById(R.id.aumentarCantidad);
            restarCantidad = itemView.findViewById(R.id.restarCantidad);
        }
    }
}
