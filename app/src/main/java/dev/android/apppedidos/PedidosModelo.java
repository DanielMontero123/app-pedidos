package dev.android.apppedidos;

public class PedidosModelo {
    private String pedido, usuario, repartidor, fecha, estado, precio;

    public PedidosModelo() {
    }

    public PedidosModelo(String pedido, String usuario, String repartidor, String fecha, String estado, String precio) {
        this.pedido = pedido;
        this.usuario = usuario;
        this.repartidor = repartidor;
        this.fecha = fecha;
        this.estado = estado;
        this.precio = precio;
    }

    public String getPedido() {
        return pedido;
    }

    public String getEstado() {
        return estado;
    }

    public String getPrecio() {
        return precio;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
