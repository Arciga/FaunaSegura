package com.example.nachox.faunasegura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

;import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.share.widget.LikeView;
import com.facebook.FacebookSdk;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentoNoticias extends Fragment {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private static final String INDICE_SECCION = "INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorNoticias adaptador;

    String[] nomb={"a","b"} ;
    String[] ra={"PERRO","GATO"} ;
    String[] ed={"4","5"} ;
    String[] gen={"H","M"} ;
    String value1="";
    String[] stringnombre = new String[0];

    String[] stringsedad = new String[0];
    String[] stringraza = new String[0];
    String[] stringenro = new String[0];


    public FragmentoNoticias() {
        // Required empty public constructor
    }
    ArrayList<String> lista = new ArrayList<>();
    public static FragmentoNoticias nuevaInstancia(int indiceSeccion) {
        FragmentoNoticias fragment = new FragmentoNoticias();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        // final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        reciclador.setLayoutManager(layoutManager);
        /*LikeView likeView = (LikeView) v.findViewById(R.id.like_view);
        likeView.setObjectIdAndType(
                "https://www.facebook.com/FacebookDevelopers",
                LikeView.ObjectType.PAGE);*/
        int indiceSeccion = getArguments().getInt(INDICE_SECCION);
        Dbase db = new Dbase( getActivity() );
        String consulta = "http://104.198.61.117/mascotas/consultamascotas.php?use=";
        EnviarRecibirDatos(consulta+db.obtener(1));

        switch (indiceSeccion) {
            case 0:


                adaptador = new AdaptadorNoticias(nomb,ed,ra,gen);
                break;

        }

        reciclador.setAdapter(adaptador);

        return view;
    }

    public void onItemClick(MyAdapter.MyViewHolder holder, int posicion) {
        Intent intent = new Intent(getActivity(), ActividadDetalle.class);
        intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
    }


    public ArrayList<String> obtDatosJSON(String resupesta)
    {
        ArrayList<String> listado=new ArrayList<>();
        try
        {
            JSONObject jsonObject= new JSONObject(resupesta);
            String texto=jsonObject.getString("noticia");

            listado.add(texto);

        }catch (Exception error)
        {

        }
        return listado;
    }

    public void EnviarRecibirDatos(String URL){



        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        JSONArray ja = new JSONArray(response);


                        stringnombre = new String[ja.length()];
                        stringsedad = new String[ja.length()];
                        stringenro = new String[ja.length()];
stringraza= new String[ja.length()];
                        for (int i = 0; i < ja.length(); i++) {
                            stringnombre[i] = ja.getJSONObject(i).getString("nombre");
                            stringsedad[i] = ja.getJSONObject(i).getString("edad");
                            stringenro[i] = ja.getJSONObject(i).getString("genero");
                            stringraza[i] = ja.getJSONObject(i).getString("raza");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adaptador = new AdaptadorNoticias(stringnombre,stringsedad,stringraza,stringenro);
                    reciclador.setAdapter(adaptador);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);









    }

}






