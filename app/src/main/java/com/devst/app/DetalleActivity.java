package com.devst.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    // Declaración de los elementos visuales que se usarán
    private TextView tvDescripcion;
    private Button btnSi, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle); // Asocia esta clase con su layout XML

        // Referencias a los elementos del layout
        tvDescripcion = findViewById(R.id.tvDescripcion);
        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);


        // Recibe los datos enviados desde HomeActivity mediante putExtra()
        String descripcion = getIntent().getStringExtra("descripcion");
        String url = getIntent().getStringExtra("url");

        // Muestra en pantalla la descripción recibida
        tvDescripcion.setText(descripcion);


        // Si el usuario acepta, se abre el navegador con la URL recibida
        btnSi.setOnClickListener(v -> {
            if (url != null && !url.isEmpty()) {
                // Crea un Intent implícito para abrir la página web
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentWeb);
            }
            // Finaliza esta Activity para volver a la anterior
            finish();
        });


        // Si el usuario cancela, simplemente se cierra la ventana actual
        btnNo.setOnClickListener(v -> finish());
    }
}