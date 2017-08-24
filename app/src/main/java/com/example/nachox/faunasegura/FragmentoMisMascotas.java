package com.example.nachox.faunasegura;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentoMisMascotas extends Fragment implements AdaptadorMisMascotas.EscuchaEventosClick,SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private static final String INDICE_SECCION = "INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorMisMascotas adaptador;
SwipeRefreshLayout swipeContainer;
    String[] nomb={"a","b"} ;
    String[] ra={"PERRO","GATO"} ;
    String[] ed={"4","5"} ;
    String[] gen={"H","M"} ;
    String value1="";
    String[] stringnombre = new String[0];
    String[] stringurl = new String[0];
    String[] stringsedad = new String[0];
    String[] stringraza = new String[0];
    String[] stringenro = new String[0];
    String[] stringid = new String[0];
    RecyclerView.ViewHolder holder;
    String consulta;
    public FragmentoMisMascotas() {
        // Required empty public constructor
    }
    ArrayList<String> lista = new ArrayList<>();
    public static FragmentoMisMascotas nuevaInstancia(int indiceSeccion) {
        FragmentoMisMascotas fragment = new FragmentoMisMascotas();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);
        Dbase db = new Dbase( getActivity() );
        db.eliminartablamascotas();
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        // final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        reciclador.setLayoutManager(layoutManager);
        swipeContainer=(SwipeRefreshLayout)view.findViewById(R.id.refress);
        /*LikeView likeView = (LikeView) v.findViewById(R.id.like_view);
        likeView.setObjectIdAndType(
                "https://www.facebook.com/FacebookDevelopers",
                LikeView.ObjectType.PAGE);*/
        int indiceSeccion = getArguments().getInt(INDICE_SECCION);
      //  Dbase db = new Dbase( getActivity() );
         consulta = "http://35.193.54.105/FaunaSeguraProyect/RegistrarMascotas/consultamascotas.php?use=";

        EnviarRecibirDatos(consulta+db.obtener(1));

       /* switch (indiceSeccion) {
            case 0:

                adaptador = new AdaptadorMisMascotas(stringnombre,stringsedad,stringraza,stringenro, stringurl,stringid, onItemClick(, 2));
                //adaptador = new AdaptadorMisMascotas(nomb,ed,ra,gen, stringurl);
                break;

        }*/
       // adaptador = new AdaptadorMisMascotas(stringnombre,stringsedad,stringraza,stringenro, stringurl,stringid,getContext());
        //adaptador.notifyDataSetChanged();
       // reciclador.setAdapter(adaptador);

        swipeContainer.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update data in ListView

adaptador.notifyDataSetChanged();

                EnviarRecibirDatos(consulta);
              //  adaptador = new AdaptadorMisMascotas(stringnombre,stringsedad,stringraza,stringenro, stringurl,stringid,getContext());
                //reciclador.setAdapter(adaptador);
                // Remove widget from screen.
                swipeContainer.setRefreshing(false);
            }
        }, 3000);
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
                        stringurl = new String[ja.length()];
                        stringsedad = new String[ja.length()];
                        stringenro = new String[ja.length()];
                        stringraza= new String[ja.length()];
                        stringid= new String[ja.length()];
                        Dbase db = new Dbase( getActivity() );
                        for (int i = 0; i < ja.length(); i++) {
                            stringnombre[i] = ja.getJSONObject(i).getString("name");
                            db.agregarmascota(stringnombre[i]);
                            stringsedad[i] = ja.getJSONObject(i).getString("edad");
                            stringenro[i] = ja.getJSONObject(i).getString("genero");
                            stringraza[i] = ja.getJSONObject(i).getString("raza");
                            stringurl[i] = ja.getJSONObject(i).getString("url");
                            stringid[i] = ja.getJSONObject(i).getString("id");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    adaptador = new AdaptadorMisMascotas(stringnombre,stringsedad,stringraza,stringenro, stringurl,stringid,getContext());
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


    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int posicion) {

            Intent intent = new Intent(getActivity(), ActividadDetalleMisMascotas.class);
            intent.putExtra(EXTRA_POSICION, posicion);
        //reciclador.invalidate();

            startActivity(intent);

    }
}






