package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EntregarRepartidor extends AppCompatActivity {

    EditText edtNumero;
    Button btnEntregarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregar_repartidor);
        edtNumero = findViewById(R.id.edtNumero);
        btnEntregarPedido = findViewById(R.id.btnEntregarPedido);
        Pedir();
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

    private void Pedir(){
        btnEntregarPedido.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            @Override
            public void onClick(View view) {
                try {
                    Statement ps = ConectarBD().createStatement();
                    ps.executeUpdate("UPDATE pedidos SET estado='Entregado' WHERE id_pedido='"+ edtNumero.getText() +"'");
                    Intent intent = new Intent(view.getContext(), MenuRepartidor.class);
                    startActivity(intent);
                }catch(SQLException e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}