package com.example.nachox.faunasegura;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Notificaciones {
    private String noticificacioness;
    private String nombre;
    private int idDrawable;

    public Notificaciones(String notificaciones) {

        this.noticificacioness = notificaciones;

    }


    public static final List<Notificaciones> NOTIFICACIONES = new ArrayList<Notificaciones>();

    static {
        NOTIFICACIONES.add(new Notificaciones("Notificaiones"));

    }


    public String getNotifi() {
        return noticificacioness;
    }
}
