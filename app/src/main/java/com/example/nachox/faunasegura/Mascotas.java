package com.example.nachox.faunasegura;

/**
 * Created by nacho on 23/08/2017.
 */

public class Mascotas {
    private String[] nombree;
    private String[] edadd;
    private String[] razaa;
    private String[] urll;
    private String[] generoo;
    private String[] idd;


    public Mascotas(String[] nombre, String[] edad, String[] raza, String[] genero,String[] url,String[] id) {
        //AdaptadorMisMascotas.EscuchaEventosClick escucha;

        nombree = nombre;
        edadd=edad;
        razaa=raza;
        generoo=genero;
        urll=url;
        idd=id;
    }
    public String[] getNombre(){
        return nombree;
    }
    public String[] getEdadd(){
        return edadd;
    }
    public String[] getRazaa(){
        return razaa;
    }
    public String[] getUrl(){
        return urll;
    }
    public String[] getGenero(){
        return generoo;
    }
    public String[] getId(){
        return idd;
    }
}
