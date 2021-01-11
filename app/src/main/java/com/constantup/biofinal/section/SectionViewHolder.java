package com.constantup.biofinal.section;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.constantup.biofinal.R;

public class SectionViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.section);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}