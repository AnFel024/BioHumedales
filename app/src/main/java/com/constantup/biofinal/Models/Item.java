package com.constantup.biofinal.Models;

import org.parceler.Parcel;

import java.util.UUID;

@Parcel
public class Item {
    String Id_Item;
    String Img_ruta;
    String Nombre;
    String Ubicacion;
    String Tam;
    String Punto_X;
    String Punto_Y;
    String Detalles;

    public Item(String id_Item, String img_ruta, String nombre, String ubicacion, String tam,
                String punto_X, String punto_Y, String detalles) {
        Id_Item = id_Item;
        Img_ruta = img_ruta;
        Nombre = nombre;
        Ubicacion = ubicacion;
        Tam = tam;
        Punto_X = punto_X;
        Punto_Y = punto_Y;
        Detalles = detalles;
    }


    public Item() {
        Id_Item = (UUID.randomUUID().toString());
        Img_ruta = "img_ruta";
        Nombre = "nombre";
        Ubicacion = "ubicacion";
        Tam = "tam";
        Punto_X = "punto_X";
        Punto_Y = "punto_Y";
        Detalles = "detalles";
    }

    public String getId_Item() {
        return Id_Item;
    }

    public void setId_Item(String id_Item) {
        Id_Item = id_Item;
    }

    public String getImg_ruta() {
        return Img_ruta;
    }

    public void setImg_ruta(String img_ruta) {
        Img_ruta = img_ruta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getTam() {
        return Tam;
    }

    public void setTam(String tam) {
        Tam = tam;
    }

    public String getPunto_X() {
        return Punto_X;
    }

    public void setPunto_X(String punto_X) {
        Punto_X = punto_X;
    }

    public String getPunto_Y() {
        return Punto_Y;
    }

    public void setPunto_Y(String punto_Y) {
        Punto_Y = punto_Y;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String detalles) {
        Detalles = detalles;
    }
}
