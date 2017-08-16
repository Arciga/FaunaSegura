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

public class BlankFragment extends Fragment {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private static final String INDICE_SECCION = "INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private MyAdapter adaptador;

    String[] arr2={"a","b"} ;
    String value1="";
    String[] stringArray = new String[0];
    public BlankFragment() {
        // Required empty public constructor
    }
    ArrayList<String> lista = new ArrayList<>();
    public static BlankFragment nuevaInstancia(int indiceSeccion) {
        BlankFragment fragment = new BlankFragment();
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
        String consulta = "http://104.198.61.117/consultanoticias.php";

        EnviarRecibirDatos(consulta);

        switch (indiceSeccion) {
            case 0:


                adaptador = new MyAdapter(stringArray);
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


                        stringArray = new String[ja.length()];

                        for (int i = 0; i < ja.length(); i++) {
                            stringArray[i] = ja.getJSONObject(i).getString("noticia");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adaptador = new MyAdapter(stringArray);
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






