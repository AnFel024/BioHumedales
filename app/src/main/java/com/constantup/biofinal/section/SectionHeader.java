package com.constantup.biofinal.section;

import com.constantup.biofinal.Adapter.Adapter;
import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;

public class SectionHeader implements Section<Adapter> {

    private ArrayList<Adapter> childList;
    public String sectionText;

    public SectionHeader(ArrayList<Adapter> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }

    @Override
    public ArrayList<Adapter> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }

}