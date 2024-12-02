package com.pupuseriajenny.ordenes.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.data.model.Orden;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.OrdenViewHolder> {

    private List<Orden> ordenes;
    private OnOrdenInteractionListener onOrdenInteractionListener;

    public HistorialAdapter(List<Orden> ordenes, OnOrdenInteractionListener onOrdenInteractionListener) {
        this.ordenes = ordenes;
        this.onOrdenInteractionListener = onOrdenInteractionListener;
    }

    @NonNull
    @Override
    public OrdenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial_pedido, parent, false);
        return new OrdenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenViewHolder holder, int position) {
        Orden orden = ordenes.get(position);
        holder.nombreCliente.setText(orden.getClienteOrden());
        holder.fechaOrden.setText(orden.getFechaOrden());
        holder.tipoOrden.setText(orden.getTipoOrden());
        holder.estadoOrden.setText(orden.getEstado());

        // Listener para editar con un toque
        holder.itemView.setOnClickListener(v -> onOrdenInteractionListener.onEditarOrden(orden));

        // Listener para eliminar con un toque largo
        holder.itemView.setOnLongClickListener(v -> {
            onOrdenInteractionListener.onEliminarOrden(orden);
            return true; // Retorna true para indicar que el evento se manejó
        });
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    public static class OrdenViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCliente, fechaOrden, tipoOrden, estadoOrden;

        public OrdenViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCliente = itemView.findViewById(R.id.nombreCliente);
            fechaOrden = itemView.findViewById(R.id.fechaOrden);
            tipoOrden = itemView.findViewById(R.id.tipoOrden);
            estadoOrden = itemView.findViewById(R.id.estadoOrden);
        }
    }

    // Interface para manejar interacciones (editar y eliminar)
    public interface OnOrdenInteractionListener {
        void onEditarOrden(Orden orden); // Método para editar
        void onEliminarOrden(Orden orden); // Método para eliminar
    }
}
