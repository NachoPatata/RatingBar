package com.example.fab_contextmenu_ratingbar;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Declaración de los componentes de la interfaz
    RatingBar ratingBar;
    TextView txtCalificacion;
    TextView txtMenuFlotante;
    TextView txtSalida;
    FloatingActionButton fabBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar); // Barra de estrellas para calificar
        txtCalificacion = findViewById(R.id.txtCalificacion); // Texto que muestra las estrellas actuales
        txtMenuFlotante = findViewById(R.id.txtMenuFlotante); // Cuadro que abre el menú flotante al mantener presionado
        txtSalida = findViewById(R.id.txtSalida); // Texto de prueba que se muestra o esconde
        fabBoton = findViewById(R.id.fabBoton); // Botón flotante (FloatingActionButton)

        // Registra el TextView para que abra el FloatingContextMenu al hacer pulsación larga
        registerForContextMenu(txtMenuFlotante);

        // ajuste de márgenes del sistema (barras de estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // evento de ratingbar que detecta cuando el usuario hace un cambio en el rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Actualiza el texto con la nueva cantidad de estrellas seleccionadas
                txtCalificacion.setText("Calificación actual: " + rating + " estrellas");
            }
        });

        // el FloatingActionButton se activa al presionar el boton inferior derecho
        fabBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene las estrellas actuales del RatingBar y muestra un mensaje en pantalla
                float estrellas = ratingBar.getRating();
                Toast.makeText(MainActivity.this,
                        "¡Calificación enviada con el FAB: " + estrellas + " estrellas!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // FloatingContextMenu se abre automáticamente al mantener presionado txtMenuFlotante
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menú Contextual Flotante");
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    //opciones activas al abrir el floatingcontextmenu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.opcion_mostrar) {
            // Muestra el texto oculto en pantalla que esta activado por defecto
            txtSalida.setVisibility(TextView.VISIBLE);
            return true;
        } else if (id == R.id.opcion_cinco_estrellas) {
            // Asigna 5 estrellas directamente en el RatingBar
            ratingBar.setRating(5.0f);
            return true;
        } else if (id == R.id.opcion_ocultar) {
            // Oculta el texto de salida
            txtSalida.setVisibility(TextView.INVISIBLE);
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
