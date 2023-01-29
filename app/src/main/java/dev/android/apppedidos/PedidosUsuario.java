package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PedidosUsuario extends AppCompatActivity {

    Spinner spinnerProductos;
    TextView textViewUsuario2, textViewPrecio, textViewPrecioFinal;
    Button btnIngresar, btnAnadir;
    EditText edtCantidad;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = sdf.format(c.getTime());

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_usuario);
        spinnerProductos = findViewById(R.id.spinnerProductos);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnAnadir = findViewById(R.id.btnAnadir);
        edtCantidad = findViewById(R.id.edtCantidad);
        textViewPrecio = findViewById(R.id.textViewPrecio);
        textViewPrecioFinal = findViewById(R.id.textViewPrecioFinal);
        Bundle extras = getIntent().getExtras();
        String usuario = extras.getString("textViewUsuario2");
        textViewUsuario2 = (TextView) findViewById(R.id.textViewUsuario2);
        textViewUsuario2.setText(usuario);
        Context context2 = getApplicationContext();
        try {
            Statement ps2 = ConectarBD().createStatement();
            ps2.executeUpdate("INSERT INTO pedidos (id_usu_per, id_rep_per, subtotal, estado, fecha) VALUES ('"+ textViewUsuario2.getText() +"', '1', 0, 'Pendiente', '"+ strDate +"')");
        }catch(SQLException e){
            Toast.makeText(context2, e.toString(), Toast.LENGTH_SHORT).show();
        }
        String url = "http://appdistribuidasproyecto.somee.com/ProyectoAD/wsAppPedidos.asmx/";
        Context context = getApplicationContext();
        List<String> lista = new ArrayList<String>();
        try {
            Statement st = ConectarBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT nom_producto FROM productos");
            while(rs.next()){
                lista.add(rs.getString("nom_producto"));
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,lista);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerProductos.setAdapter(spinnerArrayAdapter);
        }catch (SQLException e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        MostrarPrecio();
        AgregarDetalle();
        AgregarPedido();
    }

    private void MostrarPrecio() {
        Context context = getApplicationContext();
        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Statement st6 = ConectarBD().createStatement();
                    ResultSet rs6 = st6.executeQuery("SELECT pre_producto FROM productos WHERE nom_producto = '" + spinnerProductos.getSelectedItem().toString() + "'");
                    rs6.next();
                    textViewPrecio.setText(rs6.getString("pre_producto"));
                } catch (SQLException e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void AgregarDetalle(){
        btnAnadir.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            @Override
            public void onClick(View view) {
                try {
                    Statement st2 = ConectarBD().createStatement();
                    ResultSet rs2 = st2.executeQuery("Select Max(id_pedido) as id_pedido FROM pedidos");
                    rs2.next();
                    String ultimo = rs2.getString("id_pedido");
                    Statement st3 = ConectarBD().createStatement();
                    ResultSet rs3 = st3.executeQuery("Select id_producto, pre_producto FROM productos WHERE nom_producto = '"+ spinnerProductos.getSelectedItem().toString() +"'");
                    rs3.next();
                    String id_producto = rs3.getString("id_producto");
                    Float pre_producto = rs3.getFloat("pre_producto");
                    String cantidad2 = edtCantidad.getText().toString();
                    Integer cantidad3 = Integer.parseInt(cantidad2);
                    Float precio = pre_producto * cantidad3;
                    // Sumar los precios
                    String precioFinal1 = textViewPrecioFinal.getText().toString();
                    Float precioFinal2 = Float.parseFloat(precioFinal1);
                    Float precioF = precioFinal2 + precio;
                    textViewPrecioFinal.setText(precioF.toString());
                    Statement ps = ConectarBD().createStatement();
                    ps.executeUpdate("INSERT INTO detalle_pedido (id_ped_per, id_pro_per, cantidad) VALUES ('"+ ultimo +"', '"+ id_producto +"', "+ edtCantidad.getText() +")");
                    Toast.makeText(context, "Se agregó correctamente a tu pedido", Toast.LENGTH_SHORT).show();
                }catch(SQLException e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private void AgregarPedido(){
        btnIngresar.setOnClickListener(new View.OnClickListener() {
          Context context = getApplicationContext();
            @Override
            public void onClick(View view) {
                try {
                    Statement st2 = ConectarBD().createStatement();
                    ResultSet rs2 = st2.executeQuery("Select Max(id_pedido) as id_pedido FROM pedidos");
                    rs2.next();
                    String ultimo = rs2.getString("id_pedido");
                    Statement ps = ConectarBD().createStatement();
                    ps.executeUpdate("UPDATE pedidos SET subtotal="+ textViewPrecioFinal.getText() +" WHERE id_pedido='"+ ultimo +"'");
                    Intent intent = new Intent(view.getContext(), VisualizarUsuario.class);
                    startActivity(intent);
                }catch(SQLException e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}