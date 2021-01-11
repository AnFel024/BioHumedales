package com.constantup.biofinal.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.constantup.biofinal.Fragments.AgregarFragment;
import com.constantup.biofinal.Fragments.MapaFragment;
import com.constantup.biofinal.Models.Item;
import com.constantup.biofinal.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdaptadorViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<Item> listaItems;

    public Adapter(Context mContext, ArrayList<Item> listaItems) {
        this.context = mContext;
        this.listaItems = listaItems;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AdaptadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, final int position) {
        holder.txt_Nombre.setText(String.valueOf(listaItems.get(position).getNombre()));
        holder.txt_Ubicacion.setText(String.valueOf(listaItems.get(position).getUbicacion()));
        holder.txt_Tam.setText(String.valueOf(listaItems.get(position).getTam()));
        holder.btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                alerta.setView(inflater.inflate(R.layout.dialog_cadena, null));
                AlertDialog alert = alerta.create();
                alert.setContentView(R.layout.dialog_cadena);
                alert.show();
                TextView txt = alert.findViewById(R.id.txt_detalles);
                txt.setText(listaItems.get(position).getDetalles());
            }
        });
        holder.btn_compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, listaItems.get(position).getDetalles());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            }
        });
        holder.btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment miFragmento= new MapaFragment();
                Bundle argms = new Bundle();
                argms.putDouble("pto_X",Double.parseDouble(listaItems.get(position).getPunto_X()));
                argms.putDouble("pto_Y",Double.parseDouble(listaItems.get(position).getPunto_Y()));
                argms.putString("Nombre",listaItems.get(position).getNombre());
                miFragmento.setArguments(argms);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, miFragmento)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                activity.getSupportActionBar().setTitle("Mapa");
            }
        });

        holder.btn_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment miFragmento= new AgregarFragment();
                Bundle argms = new Bundle();
                argms.putString("pto_X",listaItems.get(position).getPunto_X());
                argms.putString("pto_Y",listaItems.get(position).getPunto_Y());
                argms.putString("Nombre",listaItems.get(position).getNombre());
                argms.putString("Tam",listaItems.get(position).getTam());
                argms.putString("Img",listaItems.get(position).getImg_ruta());
                argms.putString("Ubicacion",listaItems.get(position).getUbicacion());
                argms.putString("Detalles",listaItems.get(position).getDetalles());
                argms.putString("Id",listaItems.get(position).getId_Item());
                miFragmento.setArguments(argms);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, miFragmento)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                activity.getSupportActionBar().setTitle("Modificar Humedal");
            }
        });

        Picasso.get()
                .load(listaItems.get(position).getImg_ruta())
                .resize(900,500)
                .into(holder.img_Foto);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_compartir:
                break;

            case R.id.btn_detalles:
                break;
        }
    }

    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        TextView txt_Nombre, txt_Ubicacion, txt_Tam;
        Button btn_compartir, btn_detalles, btn_mapa, btn_opciones;
        ImageView img_Foto;

        AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_Nombre = itemView.findViewById(R.id.txt_Nombre);
            this.txt_Ubicacion = itemView.findViewById(R.id.txt_Ubicacion);
            this.txt_Tam = itemView.findViewById(R.id.txt_Tam);
            this.img_Foto = itemView.findViewById(R.id.imageView);
            this.btn_compartir = itemView.findViewById(R.id.btn_compartir);
            this.btn_detalles = itemView.findViewById(R.id.btn_detalles);
            this.btn_mapa = itemView.findViewById(R.id.btn_mapa);
            this.btn_opciones = itemView.findViewById(R.id.btn_menu);
        }
    }

}
