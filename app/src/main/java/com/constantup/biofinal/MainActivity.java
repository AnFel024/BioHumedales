package com.constantup.biofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.constantup.biofinal.Fragments.AgregarFragment;
import com.constantup.biofinal.Fragments.InicioFragment;
import com.constantup.biofinal.Fragments.MapaFragment;
import com.constantup.biofinal.Models.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int band_salir = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarFirebase();
        BottomNavigationView btmView = findViewById(R.id.btmView);
        fragmentoSeleccionado(new InicioFragment(), "Humedales");
        btmView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btn_inicio) {
                    fragmentoSeleccionado(new InicioFragment(), "Humedales");
                }

                if (item.getItemId() == R.id.btn_mapa) {
                    fragmentoSeleccionado(new MapaFragment(), "Google Maps");
                }

                if (item.getItemId() == R.id.btn_crud) {
                    fragmentoSeleccionado(new AgregarFragment(), "Agregar Humedal");
                }

                return true;
            }
        });
    }

    private void prueba() {
        Item item = new Item();
        databaseReference.child("Item").child(item.getId_Item()).setValue(item);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void fragmentoSeleccionado(Fragment fragmento, String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragmento)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        /*
        if(band_salir == 0) {
            Toast.makeText(this, "Presina ATRAS para salir de la app", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                    if(getOnBackPressedDispatcher().hasEnabledCallbacks()){
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        System.exit(0);
                    }
                }
            },2000);
<<<<<<< HEAD
=======
    public void onBackPressed() {
        if(band_salir == 0) {
            Toast.makeText(this, "Presina ATRAS para salir de la app", Toast.LENGTH_SHORT).show();
>>>>>>> 5044be0 (Cambio de nombre de la app, y se añade la funcion de salir presionando el boton -Volver-)
=======
>>>>>>> a541e45 (Solucionando y creando bugs)
            band_salir++;
        }

        else if (band_salir == 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(0);
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a541e45 (Solucionando y creando bugs)
        } */
    }
}
