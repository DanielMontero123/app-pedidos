package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuRepartidor extends AppCompatActivity {

    Button btnPedidosRepartidor, btnHistorial, btnSalirRepartidor;
    TextView tvRepartidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_repartidor);
        btnPedidosRepartidor = findViewById(R.id.btnPedidosRepartidor);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnSalirRepartidor = findViewById(R.id.btnSalirRepartidor);
        recibirRepartidor();
        Pedidos();
        Historial();
        Salir();
    }

    private void recibirRepartidor(){
        Bundle extras = getIntent().getExtras();
        String repartidor = extras.getString("tvRepartidor");
        tvRepartidor = (TextView) findViewById(R.id.tvRepartidor);
        tvRepartidor.setText(repartidor);
    }

    private void Pedidos(){
        btnPedidosRepartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PedidosRepartidor.class);
                intent.putExtra("textViewRepartidor1", tvRepartidor.getText());
                startActivity(intent);
            }
        });
    }

    private void Historial(){
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HistorialRepartidor.class);
                intent.putExtra("textViewRepartidor2", tvRepartidor.getText());
                startActivity(intent);
            }
        });
    }

    private void Salir(){
        btnSalirRepartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PantallaPrincipal.class);
                startActivity(intent);
            }
        });
    }
}