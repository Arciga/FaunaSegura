package com.example.nachox.faunasegura;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

;import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FragmentoNoticias extends Fragment {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private static final String INDICE_SECCION = "INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorNoticias adaptador;

    String[] arr2={"a","b"} ;
    String value1="";
    String[] stringArray = new String[0];
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
        String consulta = "http://35.193.221.19/FaunaSeguraProyect/Noticias/consultanoticias.php";

        EnviarRecibirDatos(consulta);

        switch (indiceSeccion) {
            case 0:


                adaptador = new AdaptadorNoticias(stringArray);
                break;

        }

        reciclador.setAdapter(adaptador);

        return view;
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


                        stringArray = new String[ja.length()];

                        for (int i = 0; i < ja.length(); i++) {
                            stringArray[i] = ja.getJSONObject(i).getString("noticia");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   adaptador = new AdaptadorNoticias(stringArray);
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






