package com.example.nachox.faunasegura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

;import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.share.widget.LikeView;
import com.facebook.FacebookSdk;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentoNotificaciones extends Fragment {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private static final String INDICE_SECCION = "INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorNotificaciones adaptador;

    String[] arr2={"a","b"} ;
    String value1="";
    String[] stringArray = new String[0];
    public FragmentoNotificaciones() {
        // Required empty public constructor
    }
    ArrayList<String> lista = new ArrayList<>();
    public static FragmentoNotificaciones nuevaInstancia(int indiceSeccion) {
        FragmentoNotificaciones fragment = new FragmentoNotificaciones();
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

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);
        String consulta = "http://104.198.61.117/mascotas/consultauser.php?use=";
        FragmentoNotificaciones.AsyncDataClass asyncRequestObject = new FragmentoNotificaciones.AsyncDataClass();
        Dbase db = new Dbase( getActivity() );

        EnviarRecibirDatos(consulta+db.obtener(1));

       // asyncRequestObject.execute(consulta,db.obtener(1) );


        adaptador = new AdaptadorNotificaciones(stringArray);

        reciclador.setAdapter(adaptador);

        return view;
    }

    public void onItemClick(MyAdapter.MyViewHolder holder, int posicion) {
        Intent intent = new Intent(getActivity(), ActividadDetalle.class);
        intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
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
                            stringArray[i] = ja.getJSONObject(i).getString("email");
System.out.print("email "+stringArray[i]);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adaptador = new AdaptadorNotificaciones(stringArray);
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



    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        HttpURLConnection conn;
        URL url = null;



        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("use", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();


                    try {
                        JSONArray ja = new JSONArray(response);


                        stringArray = new String[ja.length()];

                        for (int i = 0; i < ja.length(); i++) {
                            stringArray[i] = ja.getJSONObject(i).getString("email");

                        }

                        System.out.println("array"+stringArray);
                       // adaptador = new MyAdapter(stringArray);
                       // reciclador.setAdapter(adaptador);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



System.out.println("salida"+jsonResult);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }






        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }



}







