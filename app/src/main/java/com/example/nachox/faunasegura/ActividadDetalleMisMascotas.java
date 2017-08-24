package com.example.nachox.faunasegura;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

/**
 * Actividad para proyectar el detalle de cada imagen de la lista
 */
public class ActividadDetalleMisMascotas extends AppCompatActivity {
    String datoNombre;
    String datoId;
    String datoGenero;
    String datoRaza;
    String DatoEdad;
    private final String REGMAS_URL = "http://35.193.54.105/FaunaSeguraProyect/RegistrarMascotas/Dardebaja.php";
    String datourl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detallemismascotas);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        Intent intent=getIntent();
        Bundle extras =intent.getExtras();

        datoNombre=(String)extras.get("nombre");
        datoRaza=(String)extras.get("raza");
        DatoEdad=(String)extras.get("edad");
        datoGenero=(String)extras.get("genero");
        datoId=(String)extras.get("id");
        datourl=(String)extras.get("url");
        agregarToolbar();
        Dbase db = new Dbase( getApplicationContext() );

        TextView nombretext= (TextView)findViewById(R.id.nombrecard);
        TextView edadtext= (TextView)findViewById(R.id.edadcard);
        TextView generotext= (TextView)findViewById(R.id.generocard);
        TextView razaterzt= (TextView)findViewById(R.id.razacard);
        ImageView imagen = (ImageView)findViewById(R.id.imagenmascota);
       // TextView idtext= (TextView)findViewById(R.id.idcard);
        Button  baja= (Button)findViewById(R.id.eliminarmascota);
        nombretext.setText(datoNombre);
        edadtext.setText(DatoEdad);
        generotext.setText(datoGenero);
        razaterzt.setText(datoRaza);
        //imagen.setImageURI();
setupWindowAnimations();
        final AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Esta seguro de eliminar a "+ datoNombre+" ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });

        baja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo1.show();

            }
        });


    }

    public void aceptar() {
       dardebaja();

        super.finish();
    }

    public void cancelar() {
        finish();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().setReenterTransition(new Explode());
        getWindow().setExitTransition(new Explode().setDuration(500));
    }

    private void dardebaja(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGMAS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ActividadDetalleMisMascotas.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActividadDetalleMisMascotas.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                Dbase db = new Dbase( getApplicationContext() );
                params.put( "id",datoId);
                params.put("estado", "0");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_action_name);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Admnistra a "+datoNombre);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
