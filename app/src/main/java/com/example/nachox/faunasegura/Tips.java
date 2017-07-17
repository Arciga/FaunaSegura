package com.example.nachox.faunasegura;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Tips {
    private String texto;
    private String nombre;
    private int idDrawable;

    public Tips(int idDrawable) {


        this.idDrawable = idDrawable;
    }


    public static final List<Tips> TIPS = new ArrayList<Tips>();


    static {
        TIPS.add(new Tips(  R.drawable.informa));
        TIPS.add(new Tips(  R.drawable.semana_antirabica));
        TIPS.add(new Tips(  R.drawable.prevencion_rabia));


    }


    public String getNombre() {
        return nombre;
    }
    public String gettexto() {
        return texto;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
