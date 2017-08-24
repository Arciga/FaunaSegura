package com.example.nachox.faunasegura;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class MainActivity extends AppCompatActivity {
    protected EditText username;
    private TextInputLayout password;
    protected String enteredUsername;
    private SmallBang mSmallBang;

    public static int MILISEGUNDOS_ESPERA = 5000;
    private final String serverUrl = "http://35.193.54.105/FaunaSeguraProyect/RegistrarUsuarios/Registra.php";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSmallBang = SmallBang.attach2Window(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();
        username = (EditText)findViewById(R.id.editTextusuario);
        password = (TextInputLayout)findViewById(R.id.paord);
        Button loginButton = (Button)findViewById(R.id.boton_inicio);
        Button registerButton = (Button)findViewById(R.id.buttonre);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                addNumber(v);

                enteredUsername = username.getText().toString();
                String enteredPassword = password.getEditText().getText().toString();

                    if(username.getText().toString().trim().equalsIgnoreCase("")){
                        username.setError("Ingrese su correo");
                        return;
                    }

                if(password.getEditText().toString().trim().equalsIgnoreCase("")){
                    password.setError("Ingrese su Contraseña");
                    return;
                }
                // request authentication with remote server4

                MainActivity.AsyncDataClass asyncRequestObject = new MainActivity.AsyncDataClass();
                asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword);

            }

        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuRegistro.class);
                startActivity(intent);

            }
        });

    }

    public void addNumber(View view){
        mSmallBang.bang(view,new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
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
                nameValuePairs.add(new BasicNameValuePair("username", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }




        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tEspere un Momento...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                pdLoading.dismiss();

                Toast.makeText(MainActivity.this, "Falla de conecxion al Servidor", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                pdLoading.dismiss();

                Toast.makeText(MainActivity.this, "Usuario y/o Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                return;

            }
            if(jsonResult == 1){
                pdLoading.dismiss();

                Dbase db = new Dbase( getApplicationContext() );
                db.eliminar();
                db.agregar(enteredUsername);

                Intent intent = new Intent(MainActivity.this, ActividadPrincipal.class);
                intent.putExtra("nombre", enteredUsername);
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().setReenterTransition(new Explode());
        getWindow().setExitTransition(new Explode().setDuration(500));
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
    private void toast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }



}
