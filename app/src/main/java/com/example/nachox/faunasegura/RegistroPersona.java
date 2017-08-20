package com.example.nachox.faunasegura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class RegistroPersona extends ActionBarActivity {

    protected EditText username;
    private EditText password;
    private EditText vpassword;
    private EditText email;
    private EditText vcoreo;
    private EditText nombre;
    private EditText edad;
    private RadioGroup radio;
    private EditText direccion;
    protected String enteredUsername;
    private ArrayList<Categories> estadosLIST;
    private ArrayList<Categories> municipiolist;
    protected String enteredPassword;
    protected String vPass;
    protected String enteredEmail;
    protected String nombreS;
    protected String edadd;
    protected String co;
    protected String vco;
    ProgressDialog pDialog;
    ProgressDialog pDialog2;
    private Spinner estadospiner ;
    private Spinner municipiospiner ;
    protected String direc;
    private Spinner spinner ;
    public  String Sexofinal="M";
    private final String serverUrl = "http://104.198.61.117/FaunaSeguraProyect/RegistratUsuaios/index.php";

    private String URL_ESTADOS = "http://104.198.61.117/FaunaSeguraProyect/Estados/consultaestados.php";

    private String URL_MUNICIPIO = "http://104.198.61.117/FaunaSeguraProyect/Estados/Municipios/cosultamunicipios.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        agregarToolbar();

        username = (EditText)findViewById(R.id.nick);
        password = (EditText)findViewById(R.id.contracen);
        vpassword = (EditText)findViewById(R.id.vcontracen);
        email = (EditText)findViewById(R.id.correo);
        vcoreo = (EditText)findViewById(R.id.vcorreo);
        nombre =(EditText)findViewById(R.id.nombres);
        edad =(EditText)findViewById(R.id.edad);
        radio =(RadioGroup) findViewById(R.id.gruop);
        direccion =(EditText)findViewById(R.id.direcion);
        nombre =(EditText)findViewById(R.id.nombres);
        String colors[] = {"Hombre","Mujer"};
        estadospiner = (Spinner) findViewById(R.id.estadospiner);
        municipiospiner = (Spinner) findViewById(R.id.municipiospiner);
        new GetEstados().execute();
        new GetMunicipio().execute();
// Selection of the spinner
        spinner = (Spinner) findViewById(R.id.spinner3);

// Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        Button signUpButton = (Button)findViewById(R.id.RegPersona);
        estadosLIST = new ArrayList<Categories>();
        municipiolist = new ArrayList<Categories>();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredUsername = username.getText().toString();
                 enteredPassword = password.getText().toString();
                 vPass= vpassword.getText().toString();
                 enteredEmail = email.getText().toString();
                 nombreS= nombre.getText().toString();
                 edadd =edad.getText().toString();
                 co =vcoreo.getText().toString();
                 direc=direccion.getText().toString();







                // request authentication with remote server4
                validarDatos();



            }
        });
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
                nameValuePairs.add(new BasicNameValuePair("username", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                nameValuePairs.add(new BasicNameValuePair("email", params[3]));
                nameValuePairs.add(new BasicNameValuePair("nombre", params[4]));
                nameValuePairs.add(new BasicNameValuePair("edad", params[5]));
                nameValuePairs.add(new BasicNameValuePair("sexo", params[6]));
                nameValuePairs.add(new BasicNameValuePair("direccion", params[7]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

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
                Toast.makeText(RegistroPersona.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                Toast.makeText(RegistroPersona.this, "Invalid username or password or email", Toast.LENGTH_LONG).show();
                return;
            }
            if(jsonResult == 1){
                Intent intent = new Intent(RegistroPersona.this, MainActivity.class);
                finish();
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
    private boolean esPassValido(String nombree) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombree).matches() || nombree.length() > 30) {
            password.setError("Contraseña inválido");
            return false;
        } else {
            nombre.setError(null);
        }

        return true;
    }
    private boolean esNic(String nombree) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombree).matches() || nombree.length() > 30) {
            username.setError("Nick inválido");
            return false;
        } else {
            nombre.setError(null);
        }

        return true;
    }
    private void validarDatos() {

        boolean a = esNombreValido(nombreS);
        boolean b = esNic(enteredUsername);
        boolean c = esCorreoValido(enteredEmail);
       // boolean d = esvCorreoValido(co,vco);
          boolean e = esPassValido(enteredPassword);
         boolean f =escontrarectificada(enteredPassword,vPass);
        if (a && b && c  && e && f) {
            AsyncDataClass asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword, enteredEmail,nombreS,edadd,spinner.getSelectedItem().toString(),direc);


        }

    }
   /* private boolean esTelefonoValido(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            tilTelefono.setError("Teléfono inválido");
            return false;
        } else {
            tilTelefono.setError(null);
        }

        return true;
    }*/

    private boolean esCorreoValido(String correoo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correoo).matches()) {



            email.setError("Correo electrónico inválido");
            return false;
        } else {
            email.setError(null);


        return true;
    }
    }
    private boolean escontrarectificada(String contraa,String vcon) {

        if(contraa.equals(vcon)){




            return true;
        } else {

            vpassword.setError("verifique contraceña");

            return false;
        }
    }
    private void llenarestado() {
        List<String> lables = new ArrayList<String>();



        for (int i = 0; i < estadosLIST.size(); i++) {
            lables.add(estadosLIST.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        estadospiner.setAdapter(spinnerAdapter);
    }
    private void llenarmunicipio() {
        List<String> lables = new ArrayList<String>();



        for (int i = 0; i < municipiolist.size(); i++) {
            lables.add(municipiolist.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        municipiospiner.setAdapter(spinnerAdapter);
    }
    private class GetEstados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(RegistroPersona.this);
            pDialog2.setMessage("Obteniendo Estados ");
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_ESTADOS, ServiceHandler.GET);


            if (json != null) {
                try {

                    JSONArray ja = new JSONArray(json);
                    if (ja != null) {


                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject catObj = (JSONObject) ja.get(i);
                            Categories cat = new Categories(ja.getJSONObject(i).getString("estado"));
                            estadosLIST.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                if (pDialog2.isShowing())
                    pDialog2.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog2.isShowing())
                pDialog2.dismiss();
            llenarestado();
        }

    }
    private class GetMunicipio extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistroPersona.this);
            pDialog.setMessage("Obteniendo Municipios ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_MUNICIPIO, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {

                    //JSONObject jsonObj = new JSONObject(json);
                    JSONArray ja = new JSONArray(json);
                    if (ja != null) {

                        // JSONArray categories = jsonObj.getJSONArray("especiesdome");


                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject catObj = (JSONObject) ja.get(i);
                            Categories cat = new Categories(ja.getJSONObject(i).getString("municipio"));
                            municipiolist.add(cat);
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
            llenarmunicipio();
        }

    }

}













