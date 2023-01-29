package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidosRepartidor extends AppCompatActivity {

    private RecyclerView recyclerViewRepartidor;
    private RecyclerViewAdaptador adaptadorPedidos;
    Button btnEntregar;
    TextView textViewRepartidor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_repartidor);
        btnEntregar = findViewById(R.id.btnEntregar);
        Entregar();
        Bundle extras = getIntent().getExtras();
        String repartidor = extras.getString("textViewRepartidor1");
        textViewRepartidor1 = (TextView) findViewById(R.id.textViewRepartidor1);
        textViewRepartidor1.setText(repartidor);

        recyclerViewRepartidor = (RecyclerView) findViewById(R.id.recyclerRepartidor);
        recyclerViewRepartidor.setLayoutManager(new LinearLayoutManager(this));

        adaptadorPedidos = new RecyclerViewAdaptador(obtenerPedidosBD());
        recyclerViewRepartidor.setAdapter(adaptadorPedidos);
    }

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
            ResultSet rs = st.executeQuery("SELECT id_pedido, b.nom_usuario, c.nom_rep, fecha, estado, subtotal from pedidos, usuario as b, repartidor as c where  b.id_usuario = id_usu_per and id_rep_per='"+ textViewRepartidor1.getText() +"' and c.id_rep = id_rep_per and estado='Repartiendo'");
            while(rs.next()){
                pedido.add(new PedidosModelo("Pedido: " + rs.getString("id_pedido"), "Cliente: " + rs.getString("nom_usuario"), "Repartidor: " + rs.getString("nom_rep"), "Fecha: " + rs.getString("fecha"), "Estado del pedido: " + rs.getString("estado"), "Subtotal: $ " + rs.getString("subtotal")));
            }
        }catch (SQLException e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return pedido;
    }

    private void Entregar(){
        btnEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EntregarRepartidor.class);
                startActivity(intent);
            }
        });
    }
}