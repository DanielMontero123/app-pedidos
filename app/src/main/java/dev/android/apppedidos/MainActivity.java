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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtNombre, edtContrasena;
    // TEXT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        edtNombre = findViewById(R.id.edtNombre);
        edtContrasena = findViewById(R.id.edtContrasena);
        Login();
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

    private void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            @Override
            public void onClick(View view) {
                try {
                    Statement ps = ConectarBD().createStatement();
                    ResultSet rs = ps.executeQuery("SELECT id_usuario FROM usuario WHERE nom_usuario='"+ edtNombre.getText() +"' AND pass_usuario='"+edtContrasena.getText() +"'");
                    if(rs.next()){
                        String usuario = rs.getString("id_usuario");
                        Intent intent = new Intent(view.getContext(), MenuUsuario.class);
                        intent.putExtra("tvUsuario", usuario);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Usuario o Contraseña Incorrecto", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}