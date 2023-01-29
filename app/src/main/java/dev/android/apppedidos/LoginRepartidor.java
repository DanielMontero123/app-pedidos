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

public class LoginRepartidor extends AppCompatActivity {

    Button btnIngresarR;
    EditText edtNombreR, edtContrasenaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_repartidor);
        btnIngresarR = findViewById(R.id.btnIngresarR);
        edtNombreR = findViewById(R.id.edtNombreR);
        edtContrasenaR = findViewById(R.id.edtContrasenaR);
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
        btnIngresarR.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            @Override
            public void onClick(View view) {
                try {
                    Statement ps = ConectarBD().createStatement();
                    ResultSet rs = ps.executeQuery("SELECT id_rep FROM repartidor WHERE nom_rep='"+ edtNombreR.getText() +"' AND pasw_rep='"+ edtContrasenaR.getText() +"' AND estad_cuent_rep='Activo'");
                    if(rs.next()){
                        String repartidor = rs.getString("id_rep");
                        Intent intent = new Intent(view.getContext(), MenuRepartidor.class);
                        intent.putExtra("tvRepartidor", repartidor);
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