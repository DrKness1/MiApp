package com.devst.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ContactosActivity extends AppCompatActivity {

    private TextView tvNombreContacto;
    private ImageView ivFotoContacto;
    private Button btnLlamar, btnMensaje;
    private LinearLayout layoutContacto;

    private String numeroTelefono = null;

    // Launcher para seleccionar contacto
    private final ActivityResultLauncher<Intent> seleccionarContactoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri contactoUri = result.getData().getData();
                    mostrarInformacionContacto(contactoUri);
                } else {
                    Toast.makeText(this, "No se seleccionó ningún contacto", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // Referencias UI
        tvNombreContacto = findViewById(R.id.tvNombreContacto);
        ivFotoContacto = findViewById(R.id.ivFotoContacto);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnMensaje = findViewById(R.id.btnMensaje);
        layoutContacto = findViewById(R.id.layoutContacto);
        ImageButton btnAgregar = findViewById(R.id.btnAgregar);

        // Evento: seleccionar contacto
        btnAgregar.setOnClickListener(v -> {
            Intent intentSeleccionar = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            seleccionarContactoLauncher.launch(intentSeleccionar);
        });

        // Botones inicial desactivados
        btnLlamar.setEnabled(false);
        btnMensaje.setEnabled(false);
    }

    private void mostrarInformacionContacto(Uri contactoUri) {
        if (contactoUri == null) return;

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(contactoUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            String tieneTelefono = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            tvNombreContacto.setText(nombre);

            // Foto de contacto (si tiene)
            Uri fotoUri = Uri.withAppendedPath(contactoUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            ivFotoContacto.setImageURI(fotoUri);
            if (ivFotoContacto.getDrawable() == null) {
                ivFotoContacto.setImageResource(android.R.drawable.sym_def_app_icon);
            }

            // Buscar número de teléfono
            if ("1".equals(tieneTelefono)) {
                Cursor phoneCursor = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null
                );
                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    numeroTelefono = phoneCursor.getString(
                            phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    );
                    phoneCursor.close();
                }
            }

            cursor.close();
        }

        // Activar botones si hay número
        if (numeroTelefono != null) {
            btnLlamar.setEnabled(true);
            btnMensaje.setEnabled(true);

            btnLlamar.setOnClickListener(v -> {
                Intent intentLlamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numeroTelefono));
                startActivity(intentLlamar);
            });

            btnMensaje.setOnClickListener(v -> {
                Intent intentSMS = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + numeroTelefono));
                startActivity(intentSMS);
            });
        } else {
            Toast.makeText(this, "El contacto no tiene número de teléfono", Toast.LENGTH_SHORT).show();
        }
    }
}