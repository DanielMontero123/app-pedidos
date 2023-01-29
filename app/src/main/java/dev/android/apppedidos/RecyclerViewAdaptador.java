package dev.android.apppedidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pedido, usuario, precio, repartidor, fecha, estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pedido = (TextView) itemView.findViewById(R.id.txtPedido);
            precio = (TextView) itemView.findViewById(R.id.txtPrecio);
            repartidor = (TextView) itemView.findViewById(R.id.txtRepartidor);
            estado = (TextView) itemView.findViewById(R.id.txtEstado);
            usuario = (TextView) itemView.findViewById(R.id.txtUsuario);
            fecha = (TextView) itemView.findViewById(R.id.txtFecha);
        }
    }

    public List<PedidosModelo> pedidoLista;

    public RecyclerViewAdaptador(List<PedidosModelo> pedidoLista) {
        this.pedidoLista = pedidoLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pedido.setText(pedidoLista.get(position).getPedido());
        holder.usuario.setText(pedidoLista.get(position).getUsuario());
        holder.precio.setText(pedidoLista.get(position).getPrecio());
        holder.repartidor.setText(pedidoLista.get(position).getRepartidor());
        holder.fecha.setText(pedidoLista.get(position).getFecha());
        holder.estado.setText(pedidoLista.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return pedidoLista.size();
    }
}
