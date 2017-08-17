package com.example.nachox.faunasegura;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistraMascota extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Seleccionado" ,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected EditText nombre;
    protected EditText edad;
    protected EditText especie;
    protected EditText raza;
    protected EditText fechanacimiento;
   // protected EditText genero;
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
    private Spinner spinnerFood;
   private Spinner spinner ;
    private ArrayList<Categories> categoriesList;
    ProgressDialog pDialog;
    String[] stringnombre = new String[0];
    public  String Sexofinal="M";
    private String URL_CATEGORIES = "http://104.198.61.117/listar/consultaespecies.php";
    private Spinner spinnerfruta;
    private final String serverUrl = "http://104.198.61.117/mascotas/index.php";
String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registra_mascota);
       agregarToolbar();
        //spinnerfruta = (Spinner) findViewById(R.id.spinner4);
        nombre =(EditText)findViewById(R.id.non);
        edad =(EditText)findViewById(R.id.ed);
       // especie =(EditText)findViewById(R.id.especietext);
        raza =(EditText)findViewById(R.id.razatext);
       // textouser = (TextView) headerView.findViewById(R.id.usertex);
        spinnerFood = (Spinner) findViewById(R.id.spinner4);
        fechanacimiento =(EditText)findViewById(R.id.fechanacimietoo);
          u=(TextView)findViewById(R.id.usertex);
        //genero =(EditText)findViewById(R.id.generotext);
        categoriesList = new ArrayList<Categories>();
        FloatingActionButton signUpButton = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        new GetCategories().execute();
        spinnerFood.setOnItemSelectedListener(this);






        String colors[] = {"Hembra","Macho","No lo se"};

// Selection of the spinner
         spinner = (Spinner) findViewById(R.id.spinnerGenero);

// Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);




        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   especiee = especie.getText().toString();
                edadd = edad.getText().toString();
//                generoo= genero.getText().toString();
                razaa = raza.getText().toString();
                nombres= nombre.getText().toString();
                Dbase db = new Dbase( getApplicationContext() );
                usuaio=db.obtener(1);

                // request authentication with remote server4

                validarDatos();


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

                finish();
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

    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistraMascota.this);
            pDialog.setMessage("Obteniendo Animales ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {

                    //JSONObject jsonObj = new JSONObject(json);
                    JSONArray ja = new JSONArray(json);
                    if (ja != null) {

                       // JSONArray categories = jsonObj.getJSONArray("especiesdome");


                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject catObj = (JSONObject) ja.get(i);
                            Categories cat = new Categories(ja.getJSONObject(i).getString("especiesdome"));
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();



        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerFood.setAdapter(spinnerAdapter);
    }
    private boolean esNombreValido(String nombree) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombree).matches() || nombree.length() > 30) {
            nombre.setError("Nombre inválido");
            return false;
        } else {
            nombre.setError(null);
        }

        return true;
    }
    private boolean esRazaValido(String nombree) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombree).matches() || nombree.length() > 30) {
            raza.setError("Raza inválido");
            return false;
        } else {
            raza.setError(null);
        }

        return true;
    }
    private boolean esedadValido(String nombree) {
        Pattern patron = Pattern.compile("[0-9]{1,5}");
        if (!patron.matcher(nombree).matches() || nombree.length() > 30) {
            edad.setError("Edad inválido");
            return false;
        } else {
            edad.setError(null);
        }

        return true;
    }
    private void validarDatos() {

        boolean a = esNombreValido(nombres);
        boolean b = esRazaValido(razaa);
        boolean c = esedadValido(edadd);
        // boolean d = esvCorreoValido(co,vco);

        if (a && b && c  ) {
            AsyncDataClass asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(serverUrl, spinnerFood.getSelectedItem().toString(),  nombres,edadd,razaa,fecha,spinner.getSelectedItem().toString(),usuaio);

        }

    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_action_name);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Registra a tus Mascotas");

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });

    }}

