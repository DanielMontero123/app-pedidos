package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VisualizarUsuario extends AppCompatActivity {

    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador adaptadorPedidos;
    TextView textViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_usuario);
        Bundle extras = getIntent().getExtras();
        String usuario = extras.getString("textViewUsuario");
        textViewUsuario = (TextView) findViewById(R.id.textViewUsuario);
        textViewUsuario.setText(usuario);

        recyclerViewPedidos = (RecyclerView) findViewById(R.id.recyclerPedidos);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));

        adaptadorPedidos = new RecyclerViewAdaptador(obtenerPedidosBD());
        recyclerViewPedidos.setAdapter(adaptadorPedidos);

    }

    //public List<PedidosModelo> obtenerPedidos(){
      //  List<PedidosModelo> pedido = new ArrayList<>();
      //  pedido.add(new PedidosModelo("1", "Pendiente", "4", "Marcelo Pérez"));
      //  pedido.add(new PedidosModelo("2", "Entregado", "5", "Daniel Montero"));
      //  pedido.add(new PedidosModelo("3", "Pendiente", "7", "Santiago Yambay"));
      //  return pedido;
    //}

    public Connection ConectarBD(){
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://SantiagoPrueba.mssql.somee.com;databasename=SantiagoPrueba;user=santiagoy_SQLLogin_1;password=wmbhrblmt1;");
        } catch (Exception e) {
            Log.e("error", "Error, no se conectó");
        }
        return conexion;
    }

    public List<PedidosModelo> obtenerPedidosBD(){
        Context context = getApplicationContext();
        List<PedidosModelo> pedido = new ArrayList<>();
        try {
            Statement st = ConectarBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT id_pedido, b.nom_usuario, c.nom_rep, fecha, estado, subtotal from pedidos, usuario as b, repartidor as c where id_usu_per ='"+ textViewUsuario.getText() +"' and b.id_usuario = id_usu_per and c.id_rep = id_rep_per");
            while(rs.next()){
                pedido.add(new PedidosModelo("Pedido: " + rs.getString("id_pedido"), "Cliente: " + rs.getString("nom_usuario"), "Repartidor: " + rs.getString("nom_rep"), "Fecha: " + rs.getString("fecha"), "Estado del pedido: " + rs.getString("estado"), "Subtotal: $ " + rs.getString("subtotal")));
            }
        }catch (SQLException e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return pedido;
    }

}