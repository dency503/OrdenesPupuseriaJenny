package com.pupuseriajenny.ordenes.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.data.model.Orden;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<Orden> ordenes;
    private OnOrdenInteractionListener listener;

    public interface OnOrdenInteractionListener {
        void onEditarOrden(Orden orden);
        void onEliminarOrden(Orden orden);
    }

    public HistorialAdapter(List<Orden> ordenes, OnOrdenInteractionListener listener) {
        this.ordenes = ordenes;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Orden orden = ordenes.get(position);
        holder.bind(orden);
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtClienteOrden, txtFechaOrden, txtTipoOrden, txtEstadoOrden, txtComentarioOrden;

        public ViewHolder(View itemView) {
            super(itemView);
            txtClienteOrden = itemView.findViewById(R.id.nombreCliente);
            txtFechaOrden = itemView.findViewById(R.id.fechaOrden);
            txtTipoOrden = itemView.findViewById(R.id.tipoOrden);
            txtEstadoOrden = itemView.findViewById(R.id.estadoOrden);


            // Evento para editar (toque corto)
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditarOrden(ordenes.get(position));
                }
            });

            // Evento para eliminar (mantener presionado)
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEliminarOrden(ordenes.get(position));
                }
                return true; // Retorna true para indicar que el evento fue manejado
            });
        }

        public void bind(Orden orden) {
            txtClienteOrden.setText(orden.getClienteOrden());
            txtFechaOrden.setText(orden.getFechaOrden());
            txtTipoOrden.setText(orden.getTipoOrden());
            txtEstadoOrden.setText(orden.getEstadoOrden());

        }
    }
}
