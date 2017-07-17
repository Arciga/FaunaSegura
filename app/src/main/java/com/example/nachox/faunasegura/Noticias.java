package com.example.nachox.faunasegura;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Noticias {
    private String notici;
    private String nombre;
    private int idDrawable;
    private int posicion=0;

    public Noticias(int idDrawable) {

        this.idDrawable = idDrawable;

    }


    public static final List<Noticias> NOTICIAS = new ArrayList<Noticias>();

    static {





        NOTICIAS.add(new Noticias(R.drawable.noticia2));

    }


    public String getNoticiass() {
        return notici;
    }


    public int getIdDrawable() {
        return idDrawable;
    }
}


