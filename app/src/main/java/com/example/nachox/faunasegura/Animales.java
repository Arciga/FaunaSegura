package com.example.nachox.faunasegura;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Animales {
    private String texto;
    private String nombre;
    private int idDrawable;

    public Animales( String nombre, int idDrawable,String texto) {

        this.nombre = nombre;
        this.texto = texto;
        this.idDrawable = idDrawable;
    }


    public static final List<Animales> ANIMALES_POPULARES = new ArrayList<Animales>();
    public static final List<Animales> ANIMALES_EN_PELIGRO = new ArrayList<>();
    public static final List<Animales> ANIMALES_CASEROS = new ArrayList<>();
    public static final List<Animales> ANIMALES = new ArrayList<>();

    static {
        ANIMALES_POPULARES.add(new Animales( "Conejo ", R.drawable.tigre,""));


        ANIMALES_EN_PELIGRO.add(new Animales("Mono Aullador ", R.drawable.mono_aullador,""));
        ANIMALES_EN_PELIGRO.add(new Animales( "Jaguar", R.drawable.jaguar,""));
        ANIMALES_EN_PELIGRO.add(new Animales( "Oso Panda", R.drawable.panda,""));
        ANIMALES_EN_PELIGRO.add(new Animales( "Tucan    ", R.drawable.tucan_amarillo,""));
        ANIMALES_EN_PELIGRO.add(new Animales( "Tigre", R.drawable.tigre,""));
        ANIMALES_EN_PELIGRO.add(new Animales( "Tigre Blanco", R.drawable.tigre_blanco,""));

        ANIMALES_CASEROS.add(new Animales( "Gallinas", R.drawable.gallinas,""));
        ANIMALES_CASEROS.add(new Animales( "Gato", R.drawable.gato,""));
        ANIMALES_CASEROS.add(new Animales( "Perro", R.drawable.perro,""));
        ANIMALES_CASEROS.add(new Animales( "Pato", R.drawable.pato,""));
        ANIMALES_CASEROS.add(new Animales( "Vaca", R.drawable.vaca,""));

        ANIMALES.add(new Animales("Pájaro carpintero pico de marfil", R.drawable.pajaro_carpinter,""));
        ANIMALES.add(new Animales( "Cabra de los pirineos", R.drawable.cabra,""));
        ANIMALES.add(new Animales( "Rinoceronte negro del oeste", R.drawable.riniceronte,""));
        ANIMALES.add(new Animales( "Tortuga de la isla Pinta ", R.drawable.tortuga,""));

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
