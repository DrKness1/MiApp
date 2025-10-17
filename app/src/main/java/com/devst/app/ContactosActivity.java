package com.devst.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactosActivity extends AppCompatActivity {

    // Contenedor principal donde se añadirán los contactos
    private LinearLayout layoutContacto;

    // Vista de "No hay contactos" que se ocultará
    private TextView tvNombreContacto;
    private ImageView ivFotoContacto;
    private Button btnLlamar, btnMensaje;

    // Lista para evitar duplicados.
    private final ArrayList<String> idsContactosAgregados = new ArrayList<>();

    // Launcher para seleccionar contacto
    private final ActivityResultLauncher<Intent> seleccionarContactoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri contactoUri = result.getData().getData();
                    agregarContactoALaLista(contactoUri);
                } else {
                    Toast.makeText(this, "No se seleccionó ningún contacto", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // --- Referencias ---
        layoutContacto = findViewById(R.id.layoutContacto);
        ImageButton btnAgregar = findViewById(R.id.btnAgregar);

        // Referencias a las vistas del contacto "placeholder" inicial
        tvNombreContacto = findViewById(R.id.tvNombreContacto);
        ivFotoContacto = findViewById(R.id.ivFotoContacto);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnMensaje = findViewById(R.id.btnMensaje);

        // Ocultamos las vistas de ejemplo iniciales
        ocultarPlaceholderInicial();

        // Evento para el botón de agregar
        btnAgregar.setOnClickListener(v -> {
            Intent intentSeleccionar = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            seleccionarContactoLauncher.launch(intentSeleccionar);
        });
    }

    private void ocultarPlaceholderInicial() {
        tvNombreContacto.setText("Agrega tu primer contacto");
        ivFotoContacto.setVisibility(View.GONE);
        btnLlamar.setVisibility(View.GONE);
        btnMensaje.setVisibility(View.GONE);
    }

    //Crea nueva vista para el contacto añadido
    private void agregarContactoALaLista(Uri contactoUri) {
        if (contactoUri == null) return;

        ContentResolver resolver = getContentResolver();
        String contactId = null;
        String nombre = null;
        String numeroTelefono = null;
        Uri fotoUri = null;

        // 1. Obtener la información del contacto
        try (Cursor cursor = resolver.query(contactoUri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                // Evitar añadir duplicados
                if (idsContactosAgregados.contains(contactId)) {
                    Toast.makeText(this, "Este contacto ya está en la lista", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener foto
                fotoUri = Uri.withAppendedPath(contactoUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                // Obtener número de teléfono
                String tieneTelefono = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if ("1".equals(tieneTelefono)) {
                    try (Cursor phoneCursor = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null)) {
                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
                            numeroTelefono = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                    }
                }
            }
        }

        // Si no se obtuvo nombre, no hacemos nada
        if (nombre == null) return;

        // 2. Crear una nueva "fila" de contacto dinámicamente
        // Usamos el mismo layout 'activity_contactos' como plantilla para la fila.
        // LayoutInflater nos permite "inflar" (crear) una vista desde un XML.
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout nuevaFilaContacto = (LinearLayout) inflater.inflate(R.layout.activity_contactos, layoutContacto, false);

        // 3. Configurar la vista recién creada con la info del contacto
        TextView tvNombreNuevo = nuevaFilaContacto.findViewById(R.id.tvNombreContacto);
        ImageView ivFotoNuevo = nuevaFilaContacto.findViewById(R.id.ivFotoContacto);
        Button btnLlamarNuevo = nuevaFilaContacto.findViewById(R.id.btnLlamar);
        Button btnMensajeNuevo = nuevaFilaContacto.findViewById(R.id.btnMensaje);
        ImageButton btnAgregarNuevo = nuevaFilaContacto.findViewById(R.id.btnAgregar);

        // Ocultamos el botón de agregar en las filas de la lista
        btnAgregarNuevo.setVisibility(View.GONE);

        tvNombreNuevo.setText(nombre);
        ivFotoNuevo.setImageURI(fotoUri);
        // Si no tiene foto, ponemos un icono por defecto
        if (ivFotoNuevo.getDrawable() == null) {
            ivFotoNuevo.setImageResource(R.mipmap.ic_launcher); // Usamos el icono de la app como fallback
        }

        // 4. Configurar botones de la nueva fila (si tiene número)
        if (numeroTelefono != null) {
            final String numeroFinal = numeroTelefono; // Necesario para usar en lambda
            btnLlamarNuevo.setEnabled(true);
            btnMensajeNuevo.setEnabled(true);

            btnLlamarNuevo.setOnClickListener(v -> {
                Intent intentLlamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numeroFinal));
                startActivity(intentLlamar);
            });

            btnMensajeNuevo.setOnClickListener(v -> {
                Intent intentSMS = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + numeroFinal));
                startActivity(intentSMS);
            });
        } else {
            // Si no tiene número, los botones de la fila se quedan desactivados
            btnLlamarNuevo.setEnabled(false);
            btnMensajeNuevo.setEnabled(false);
            Toast.makeText(this, "El contacto no tiene número de teléfono", Toast.LENGTH_SHORT).show();
        }

        // 5. Añadir la nueva fila al contenedor principal y registrar el ID
        // Ocultamos el mensaje de "Agrega tu primer contacto" si es el primero
        if (idsContactosAgregados.isEmpty()) {
            tvNombreContacto.setVisibility(View.GONE);
        }
        layoutContacto.addView(nuevaFilaContacto);
        idsContactosAgregados.add(contactId);
    }
}
