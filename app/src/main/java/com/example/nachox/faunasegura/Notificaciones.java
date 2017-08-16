package com.example.nachox.faunasegura;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Notificaciones {
    private String edad;
    private String genero;
    private String raza;
    private String nombre;
    private int idDrawable;

    public Notificaciones(String nombre,String edad,String raza,String genero) {

        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.genero = genero;

    }


    public static final List<Notificaciones> NOTIFICACIONES = new ArrayList<Notificaciones>();

    static {
        NOTIFICACIONES.add(new Notificaciones("eva","5","perro","hembra"));
       // NOTIFICACIONES.add(new Notificaciones("rock","5","perro","hembra"));

        //NOTIFICACIONES.add(new Notificaciones("HHOHOHO"));

    }


    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }
    public String getGenero() {
        return genero;
    }
    public String getRaza() {
        return raza;
    }


}
