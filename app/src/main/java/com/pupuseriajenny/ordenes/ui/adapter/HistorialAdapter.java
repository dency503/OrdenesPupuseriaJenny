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
    private OnOrdenClickListener onOrdenClickListener;

    public HistorialAdapter(List<Orden> ordenes, OnOrdenClickListener onOrdenClickListener) {
        this.ordenes = ordenes;
        this.onOrdenClickListener = onOrdenClickListener;
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
        holder.nombreCliente.setText(orden.getNombreCliente());
        holder.fechaOrden.setText(orden.getFechaOrden());
        holder.tipoOrden.setText(orden.getTipoOrden());
        holder.estadoOrden.setText(orden.getEstadoOrden());

        holder.itemView.setOnClickListener(v -> onOrdenClickListener.onOrdenClick(orden));
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

    public interface OnOrdenClickListener {
        void onOrdenClick(Orden orden);
    }
}
