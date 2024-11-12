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
import com.pupuseriajenny.ordenes.ui.listener.BebidaActionsListener;

import java.util.List;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaAdapter.BebidaViewHolder> {

    private final List<Bebida> bebidaList;
    private final BebidaActionsListener actionsListener;

    public BebidaAdapter(List<Bebida> bebidaList, BebidaActionsListener actionsListener) {
        this.bebidaList = bebidaList;
        this.actionsListener = actionsListener;
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
        holder.nombreBebida.setText(bebida.getNombre());
        holder.precioBebida.setText("$"+bebida.getPrecio());
        holder.imagenBebida.setImageResource(bebida.getImagenResourceId());
        holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));

        holder.aumentarCantidad.setOnClickListener(v -> {
            bebida.setCantidad(bebida.getCantidad() + 1);
            holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));
            actionsListener.actualizarBebidasSeleccionadas(bebida);
        });

        holder.restarCantidad.setOnClickListener(v -> {
            if (bebida.getCantidad() > 0) {
                bebida.setCantidad(bebida.getCantidad() - 1);
                holder.cantidadBebida.setText(String.valueOf(bebida.getCantidad()));
                actionsListener.actualizarBebidasSeleccionadas(bebida);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bebidaList.size();
    }

    public static class BebidaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreBebida, precioBebida, cantidadBebida;
        private final ImageView imagenBebida;
        private final Button aumentarCantidad, restarCantidad;

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
