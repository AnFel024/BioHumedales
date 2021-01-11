package com.constantup.biofinal.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constantup.biofinal.MainActivity;
import com.constantup.biofinal.Models.Item;
import com.constantup.biofinal.R;
import com.constantup.biofinal.Adapter.Adapter;
import com.constantup.biofinal.Adapter.AdapterSectionRecycler;
import com.constantup.biofinal.section.SectionHeader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class InicioFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ArrayList<Item> arlItem;

    public InicioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arlItem = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        DatabaseReference databaseReference = ((MainActivity) Objects.requireNonNull(getActivity())).getDatabaseReference();
        recyclerView = rootView.findViewById(R.id.recyclerLista);
        SearchView searchView = rootView.findViewById(R.id.mSearch_bar);
        searchView.setOnQueryTextListener(this);
        databaseReference.child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<SectionHeader> sections = new ArrayList<>();
                if(dataSnapshot.exists()) {
                    ArrayList<Adapter> arlAdapter = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Item nItem  = dataSnapshot1.getValue(Item.class);
                        arlItem.add(nItem);
                        ArrayList<Item> a = new ArrayList<>();
                        a.add(nItem);
                        arlAdapter.add(new Adapter(getContext(), a));
                    }
                    sections.add(new SectionHeader(arlAdapter, "Humedales"));
                }
                initRecyclerView(sections);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return rootView;
    }

    private void initRecyclerView (ArrayList<SectionHeader> sections){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        AdapterSectionRecycler adaptador = new AdapterSectionRecycler(getContext(), sections);
        recyclerView.setAdapter(adaptador);
    }

    private ArrayList<SectionHeader> arlFiltro (String texto){
        ArrayList<SectionHeader> arlPrueba = new ArrayList<>();
        ArrayList<Item> arlNItems;
        ArrayList<Adapter> arlNAdapter = new ArrayList<>();

        for (Item item : arlItem) {
            String Nombre = item.getNombre().toLowerCase();
            if(Nombre.contains(texto.toLowerCase())){
                arlNItems = new ArrayList<>();
                arlNItems.add(item);
                arlNAdapter.add(new Adapter(getContext(),arlNItems));
            }
        }

        if(arlNAdapter.size() == 0)
            arlPrueba.add(new SectionHeader(arlNAdapter,"No se encontro nada similar :("));
        else
            arlPrueba.add(new SectionHeader(arlNAdapter,"Resultado del filtro: '" + texto + "'"));
        return arlPrueba;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        initRecyclerView(arlFiltro(query));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
