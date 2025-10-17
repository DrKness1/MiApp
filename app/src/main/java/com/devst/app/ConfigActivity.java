package com.devst.app;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfigActivity extends AppCompatActivity {

    //-----VARIABLES----->

    private Switch switchNotificaciones;


    private Button btnCambiarColor;

    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Toolbar con botón atrás
        Toolbar toolbar = findViewById(R.id.toolbarConfig);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferencias = getSharedPreferences("AjustesApp", MODE_PRIVATE);

        switchNotificaciones = findViewById(R.id.switchNotificaciones);
        btnCambiarColor = findViewById(R.id.btnCambiarColor);

        // Cargar el ajuste de las notificaciones
        boolean notificaciones = preferencias.getBoolean("notificaciones", true);
        switchNotificaciones.setChecked(notificaciones);

        boolean fondoGris = preferencias.getBoolean("fondoGris", false);
        actualizarFondo(fondoGris);

        // Cambia el estado de las notificaciones
        switchNotificaciones.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            preferencias.edit().putBoolean("notificaciones", isChecked).apply();
        });

        // Evento: Cambiar Tema
        btnCambiarColor.setOnClickListener(v -> {
            boolean actual = preferencias.getBoolean("fondoGris", false);
            boolean nuevo = !actual;
            preferencias.edit().putBoolean("fondoGris", nuevo).apply();
            actualizarFondo(nuevo);
        });
    }

    private void actualizarFondo(boolean gris) {
        if (gris)
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        else
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}