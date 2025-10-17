package com.devst.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    //-----VARIABLES----->

    private String emailUsuario = "";
    private TextView tvBienvenida;
    private ImageView imageView;
    private LinearLayout layoutPrincipal;

    // Nombre del archivo de preferencias
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_COLOR = "color_fondo";

    // Launcher para seleccionar imagen de la galería
    private final ActivityResultLauncher<String> seleccionarImagenLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imageView.setImageURI(uri);
                } else {
                    Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referencias a las vistas
        layoutPrincipal = findViewById(R.id.layoutPrincipal);
        tvBienvenida = findViewById(R.id.tvBienvenida);
        Button btnAbrirWeb = findViewById(R.id.btnAbrirWeb);
        Button btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        Button btnImagen = findViewById(R.id.btnImagen);
        Button btnConfiguracion = findViewById(R.id.btnConfiguracion);
        Button btnContactos = findViewById(R.id.btnContactos);
        imageView = findViewById(R.id.imageView);

        // Recibir datos del Login
        emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario == null) emailUsuario = "";
        tvBienvenida.setText("Bienvenido: " + emailUsuario);



        // -------------------- EVENTOS IMPLÍCITOS --------------------

        // 1- Abrir Sitio Web (Steam)
        btnAbrirWeb.setOnClickListener(v -> {
            // Datos que antes pasaríamos a DetalleActivity
            String titulo = "Página Steam";
            String pais = "Chile";
            String url = "https://store.steampowered.com/?l=spanish";

            // Construir mensaje de detalles en lista
            String mensaje = "Página a abrir: " + titulo + "\n" + "\n" +
                    "Desde: " + pais + "\n" + "\n" +
                    "Dirección de la página: " + "\n" +  url;

            // Crear "AlertDialog" como cuadro flotante
            new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Detalles")
                    .setMessage(mensaje)
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Abrir la web si pulsa Sí
                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intentWeb);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();



            // Opcional: animación personalizada
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        // 2- Enviar correo
        btnEnviarCorreo.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUsuario});
            email.putExtra(Intent.EXTRA_SUBJECT, "Asunto predeterminado");
            email.putExtra(Intent.EXTRA_TEXT, "Hola, soy un correo enviado desde Android Studio");
            startActivity(Intent.createChooser(email, "Enviar correo con:"));
        });

        // 3- Seleccionar imagen
        btnImagen.setOnClickListener(v -> seleccionarImagenLauncher.launch("image/*"));

        // 4- Abrir configuración del sistema (Bluetooth)
        btnConfiguracion.setOnClickListener(v -> {
            Intent intentBluetooth = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intentBluetooth);
        });

        // 5- Abrir contactos
        btnContactos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ContactosActivity.class);
            startActivity(intent);
        });
    }



    // -------------------- MENÚ DE 3 PUNTOS --------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_configuracion) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);

            // Aquí aplicamos la animación personalizada
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // -------------------- APLICAR COLOR GUARDADO --------------------

    @Override
    protected void onResume() {
        super.onResume();
        // Cada vez que se vuelve a HomeActivity, se aplica el color guardado
        android.content.SharedPreferences prefs = getSharedPreferences("AjustesApp", MODE_PRIVATE);
        boolean fondoGris = prefs.getBoolean("fondoGris", false);

        if (fondoGris)
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        else
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));
    }




}