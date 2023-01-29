package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaPrincipal extends AppCompatActivity {

    Button btnCliente, btnRepartidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        btnCliente = findViewById(R.id.btnUsuario);
        btnRepartidor = findViewById(R.id.btnRepartidor);
        IngresarCliente();
        IngresarRepartidor();
    }

    private void IngresarCliente(){
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void IngresarRepartidor(){
        btnRepartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginRepartidor.class);
                startActivity(intent);
            }
        });
    }
}