package com.constantup.biofinal.section;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.constantup.biofinal.R;

public class ChildViewHolder extends RecyclerView.ViewHolder {

    //TextView name;
    public RecyclerView recyclerView;

    public ChildViewHolder(View itemView) {
        super(itemView);
        //name = (TextView) itemView.findViewById(R.id.child);
        this.recyclerView = itemView.findViewById(R.id.recyclerProteina);
    }
}