package com.pupuseriajenny.ordenes.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.data.model.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private List<Producto> productoList;

    public ProductoAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.tvNombre.setText(producto.getNombreProducto());
        holder.tvCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.tvPrecio.setText(String.valueOf(producto.getPrecioProducto()));
        holder.tvTotal.setText(String.valueOf(producto.getSubTotalProducto()));
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCantidad, tvPrecio, tvTotal;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.nombreProducto);
            tvCantidad = itemView.findViewById(R.id.cantidadProducto);
            tvPrecio = itemView.findViewById(R.id.precioProducto);
            tvTotal = itemView.findViewById(R.id.totalProducto);
        }
    }

}
