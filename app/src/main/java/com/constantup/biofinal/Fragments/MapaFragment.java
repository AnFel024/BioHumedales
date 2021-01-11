package com.constantup.biofinal.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constantup.biofinal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    private Double pto_X, pto_Y;
    private String Nombre;

    public MapaFragment() {
    }

    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fMap);
        if(mapFragment == null){
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment =  SupportMapFragment.newInstance();
            ft.replace(R.id.fMap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        if(getArguments() != null){
            this.pto_X = getArguments().getDouble("pto_X");
            this.pto_Y = getArguments().getDouble("pto_Y");
            this.Nombre = getArguments().getString("Nombre");
        }
        else{
            pto_X = 0.00000;
            pto_Y = 0.00000;
            this.Nombre = "Abre una ubicacion";
        }

        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng villaLuz = new LatLng(pto_X, pto_Y);
        googleMap.addMarker(new MarkerOptions().position(villaLuz).title(Nombre));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(villaLuz));
    }
}
