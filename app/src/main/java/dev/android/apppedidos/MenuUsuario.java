package dev.android.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuUsuario extends AppCompatActivity {

    Button btnPedir, btnVisualizar, btnSalir;
    TextView tvUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        btnPedir = findViewById(R.id.btnPedir);
        btnVisualizar = findViewById(R.id.btnRevisar);
        btnSalir = findViewById(R.id.btnSalir);
        tvUsuario = findViewById(R.id.tvUsuario);
        recibirUsuario();
        Pedir();
        Visualizar();
        Salir();
    }

    private void recibirUsuario(){
        Bundle extras = getIntent().getExtras();
        String usuario = extras.getString("tvUsuario");
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvUsuario.setText(usuario);
    }

    private void Pedir(){
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PedidosUsuario.class);
                intent.putExtra("textViewUsuario2", tvUsuario.getText());
                startActivity(intent);
            }
        });
    }

    private void Visualizar(){
        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VisualizarUsuario.class);
                intent.putExtra("textViewUsuario", tvUsuario.getText());
                startActivity(intent);
            }
        });
    }

    private void Salir(){
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PantallaPrincipal.class);
                startActivity(intent);
            }
        });
    }
}