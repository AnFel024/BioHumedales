package com.constantup.biofinal.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.constantup.biofinal.MainActivity;
import com.constantup.biofinal.Models.Item;
import com.constantup.biofinal.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class AgregarFragment extends Fragment implements View.OnClickListener {

    private static final String API_KEY = String.valueOf(R.string.google_api_key);
    private EditText edt_Nombre, edt_Detalles, edt_Tam, edt_Img, edt_Ub;
    private TextView edt_Lat, edt_Lng;
    private ImageView img_preview;
    private Button btn_add, btn_actualizar, btn_volver, btn_borrar;
    private DatabaseReference databaseReference;
    private LatLng Coordenadas;
    private ArrayList<Item> arlItem;
    private LinearLayout linearLayout;
    private String Id = "#", Latitud, Longitud;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private int flag = 0;

    public AgregarFragment() {
    }

    public static AgregarFragment newInstance(String param1, String param2) {
        AgregarFragment fragment = new AgregarFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Places.isInitialized()){
            Places.initialize(getActivity(), API_KEY);
        }
        flag=0;
        PlacesClient placesClient = Places.createClient(getContext());
        arlItem = new ArrayList<>();
        databaseReference = ((MainActivity) Objects.requireNonNull(getActivity())).getDatabaseReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Coordenadas = new LatLng(0.0,0.0);
        Id = "#";
        this.Latitud = "";
        this.Longitud = "";
        View rootView = inflater.inflate(R.layout.fragment_agregar, container, false);
        btn_add = rootView.findViewById(R.id.btn_add);
        btn_actualizar = rootView.findViewById(R.id.btn_actualizar);
        btn_volver = rootView.findViewById(R.id.btn_volver);
        btn_borrar = rootView.findViewById(R.id.btn_borrar);
        edt_Nombre = rootView.findViewById(R.id.edt_nombre);
        edt_Detalles = rootView.findViewById(R.id.edt_Detalles);
        edt_Img = rootView.findViewById(R.id.edt_Img);
        edt_Tam = rootView.findViewById(R.id.edt_Tam);
        edt_Ub = rootView.findViewById(R.id.edt_Ub);
        edt_Lat = rootView.findViewById(R.id.txt_lat);
        edt_Lng = rootView.findViewById(R.id.txt_lng);
        linearLayout = rootView.findViewById(R.id.lay_buttons);
        img_preview = rootView.findViewById(R.id.img_preview);
        databaseReference.child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot nDataSnapchot : dataSnapshot.getChildren()){
                        Item nItem  = nDataSnapchot.getValue(Item.class);
                        arlItem.add(nItem);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        edt_Img.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    Picasso.get()
                            .load(s.toString())
                            .resize(370,200)
                            .into(img_preview);
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        });

        btn_add.setOnClickListener(this);
        btn_actualizar.setOnClickListener(this);
        btn_borrar.setOnClickListener(this);
        btn_volver.setOnClickListener(this);

        autocompleteSupportFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteSupportFragment != null;
        autocompleteSupportFragment.setHint("Busca un humedal");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Coordenadas = place.getLatLng();
                edt_Nombre.setText(place.getName());
                edt_Detalles.setText(place.getName() + "\n" + place.getAddress() + "\n" +
                        place.getPhoneNumber() + "\n" + place.getWebsiteUri() + "\n");
                edt_Lat.setText(String.valueOf(place.getLatLng().latitude));
                edt_Lng.setText(String.valueOf(place.getLatLng().longitude));
                flag++;
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        if(getArguments() !=null) {
            linearLayout.setVisibility(View.VISIBLE);
            Latitud = getArguments().getString("pto_X");
            Longitud = getArguments().getString("pto_Y");
            llenarEditores();
        }

        return rootView;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.btn_add:
                agregarItem(view);
                break;

            case R.id.btn_actualizar:
                agregarItem(view);
                break;

            case R.id.btn_borrar:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Desea eliminar este item?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                databaseReference.child("Item").child(Id).removeValue();
                                pasarAlInicio(view);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create();
                builder.show();
                break;

            case R.id.btn_volver:
                pasarAlInicio(view);
                break;
        }
    }

    private void pasarAlInicio(View view) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new InicioFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void agregarItem(View view) {
        String Nombre = edt_Nombre.getText().toString().trim();
        String Detalles = edt_Detalles.getText().toString().trim();
        String Img_Ruta = edt_Img.getText().toString().trim();
        String Tam = edt_Tam.getText().toString().trim();
        String Ubicacion = edt_Ub.getText().toString().trim();
        if(getArguments() == null){
            Latitud = String.valueOf(Coordenadas.latitude);
            Longitud = String.valueOf(Coordenadas.longitude);
        }
        try{
        if ((Nombre.equals("") || Detalles.equals("") || Img_Ruta.equals("") || Tam.equals("")
                || Ubicacion.equals("")) || ( Latitud.equals("0.0") || Longitud.equals("0.0")))
            Toast.makeText(getActivity(), "Rellene, por favor, todos los campos", Toast.LENGTH_SHORT).show();
        else {
            Item item = new Item();
            if (getArguments() != null){
                item.setId_Item(Id);
                Latitud = getArguments().getString("pto_X");
                Longitud = getArguments().getString("pto_Y");
            }
            item.setNombre(Nombre);
            item.setDetalles(Detalles);
            item.setImg_ruta(Img_Ruta);
            item.setTam(Tam);
            item.setUbicacion(Ubicacion);
            item.setPunto_X(Latitud);
            item.setPunto_Y(Longitud);

            if(!itemExiste(item) || getArguments() != null){
                databaseReference.child("Item").child(item.getId_Item()).setValue(item);
                limpiarEditores();
                pasarAlInicio(view);
            }
            else
                Toast.makeText(getActivity(), "El humedal ya se encuentra dentro de la lista", Toast.LENGTH_SHORT).show();
        }

        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    private boolean itemExiste(Item nItem){
        boolean resultado = false;

        for(Item item : arlItem){
            Log.e("TAG", item.getNombre());
            Log.e("TAG", nItem.getNombre());
            if((item.getNombre().equals(nItem.getNombre())) || (item.getPunto_X().equals(nItem.getPunto_X())) ||
                    (item.getPunto_Y().equals(nItem.getPunto_Y()))){
                resultado = true;
                break;
            }
        }

        return resultado;
    }

    private void limpiarEditores() {
        this.edt_Nombre.getText().clear();
        this.edt_Detalles.getText().clear();
        this.edt_Img.getText().clear();
        this.edt_Tam.getText().clear();
        this.edt_Ub.getText().clear();
    }

    private void llenarEditores(){
        btn_add.setVisibility(View.INVISIBLE);
        btn_actualizar.setVisibility(View.INVISIBLE);
        if(getArguments() != null){
            edt_Nombre.setText(getArguments().getString("Nombre"));
            edt_Detalles.setText(getArguments().getString("Detalles"));
            edt_Img.setText(getArguments().getString("Img"));
            edt_Tam.setText(getArguments().getString("Tam"));
            edt_Ub.setText(getArguments().getString("Ubicacion"));
            Id = getArguments().getString("Id");
            autocompleteSupportFragment.setText(getArguments().getString("Nombre"));
            edt_Lat.setText(Latitud);
            edt_Lng.setText(Longitud);
        }
    }
}
