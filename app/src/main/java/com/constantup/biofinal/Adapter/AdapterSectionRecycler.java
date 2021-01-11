package com.constantup.biofinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.constantup.biofinal.section.ChildViewHolder;
import com.constantup.biofinal.section.SectionHeader;
import com.constantup.biofinal.section.SectionViewHolder;
import com.constantup.biofinal.R;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.ArrayList;


public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, Adapter, SectionViewHolder, ChildViewHolder>  {
    private Context context;
    private ArrayList<SectionHeader> sectionItemList;
    private TextView tName;

    public AdapterSectionRecycler(Context context, ArrayList<SectionHeader> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
        this.sectionItemList = sectionItemList;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sectionheader, sectionViewGroup, false);
        SectionViewHolder sVH = new SectionViewHolder(view);
        this.tName = sVH.getName();
        return sVH;
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sectionchild, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader section) {
        sectionViewHolder.name.setText(section.sectionText);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int sectionPosition, int childPosition, Adapter Cadena) {

        Adapter adapter = sectionItemList.get(sectionPosition).getChildItems().get(childPosition);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        childViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        childViewHolder.recyclerView.setAdapter(adapter);

    }
}
