package com.example.nachox.faunasegura;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistraMascota extends ActionBarActivity {

    protected EditText nombre;
    protected EditText edad;
    protected EditText especie;
    protected EditText raza;
    protected EditText fechanacimiento;

    protected EditText genero;
   // protected EditText usuario;
String nombres;
String especiee;
    String razaa;
    //TextView textouser;
    String edadd;
    String generoo;
    String usuaio="nacho";
    String fecha="12/12/12";
TextView u;
    TextView textouser;
    public  String Sexofinal="M";
    private final String serverUrl = "http://104.198.61.117/mascotas/index.php";
String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registra_mascota);
        agregarToolbar();

        nombre =(EditText)findViewById(R.id.non);
        edad =(EditText)findViewById(R.id.ed);
        especie =(EditText)findViewById(R.id.especietext);
        raza =(EditText)findViewById(R.id.razatext);
       // textouser = (TextView) headerView.findViewById(R.id.usertex);

        fechanacimiento =(EditText)findViewById(R.id.fechanacimietoo);
          u=(TextView)findViewById(R.id.usertex);
        genero =(EditText)findViewById(R.id.generotext);

        Button signUpButton = (Button)findViewById(R.id.Guardarmascota);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                especiee = especie.getText().toString();
                edadd = edad.getText().toString();
                generoo= genero.getText().toString();
                razaa = raza.getText().toString();
                nombres= nombre.getText().toString();
                Dbase db = new Dbase( getApplicationContext() );
                usuaio=db.obtener(1);

                // request authentication with remote server4
                Intent intent=getIntent();
                Bundle extras =intent.getExtras();


                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, especiee,  nombres,edadd,razaa,fecha,generoo,usuaio);

                //fonga ionic backbond
            }
        });
        //prueba


    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

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
                nameValuePairs.add(new BasicNameValuePair("especie", params[1]));
                nameValuePairs.add(new BasicNameValuePair("nombre", params[2]));
                nameValuePairs.add(new BasicNameValuePair("edad", params[3]));
                nameValuePairs.add(new BasicNameValuePair("raza", params[4]));
                nameValuePairs.add(new BasicNameValuePair("fechanacimiento", params[5]));
                nameValuePairs.add(new BasicNameValuePair("genero", params[6]));
                nameValuePairs.add(new BasicNameValuePair("usuario", params[7]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                //System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(RegistraMascota.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                Toast.makeText(RegistraMascota.this, "Invalid username or password or email", Toast.LENGTH_LONG).show();
                return;
            }
            if(jsonResult == 1){
                Intent intent = new Intent(RegistraMascota.this, MainActivity.class);
                startActivity(intent);
            }
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
    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_action_name);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Soy una Persona");

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();

            }
        });

    }

}

