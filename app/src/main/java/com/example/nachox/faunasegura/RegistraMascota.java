package com.example.nachox.faunasegura;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class RegistraMascota extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    private Button buttonUpload;
    private String mPath;
    protected EditText nombre;
    protected EditText edad;
    protected EditText especie;
    protected EditText raza;
    protected EditText fechanacimiento;
   // protected EditText genero;
   // protected EditText usuario;
String nombres;
    String razaa;
    String edadd;
    String url="http://35.193.54.105/FaunaSeguraProyect/Uploads/Mascotas/uploads/6.jpg";
    String usuaio="nacho";
    String fecha="12/12/12";
TextView u;
    private Spinner spinnergenero;
   private Spinner especiespiner ;
    private ArrayList<Categories> categoriesList;
    ProgressDialog pDialog;
    private String URL_CATEGORIES = "http://35.193.54.105/FaunaSeguraProyect/Especies/Domesticas/consultaespecies.php";
    private final String REGMAS_URL = "http://35.193.54.105/FaunaSeguraProyect/RegistrarMascotas/Regmascota2.php";

    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registra_mascota);
       agregarToolbar();
        mSetImage = (ImageView) findViewById(R.id.imagen);
        mOptionButton = (Button) findViewById(R.id.foto);
        mRlView = (RelativeLayout) findViewById(R.id.rl_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(mayRequestStoragePermission  ())
            mOptionButton.setEnabled(true);
        else
            mOptionButton.setEnabled(false);


        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        nombre =(EditText)findViewById(R.id.non);
        edad =(EditText)findViewById(R.id.ed);
        raza =(EditText)findViewById(R.id.razatext);
        especiespiner = (Spinner) findViewById(R.id.spinner4);
        fechanacimiento =(EditText)findViewById(R.id.fechanacimietoo);
          u=(TextView)findViewById(R.id.usertex);
        categoriesList = new ArrayList<Categories>();
        FloatingActionButton signUpButton = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        new GetCategories().execute();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String colors[] = {"Hembra","Macho","No lo se"};
         spinnergenero = (Spinner) findViewById(R.id.spinnerGenero);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnergenero.setAdapter(spinnerArrayAdapter);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dbase db = new Dbase( getApplicationContext() );

                edadd = edad.getText().toString();
                razaa = raza.getText().toString();
                nombres= nombre.getText().toString();
                usuaio=db.obtener(1);
              //  AsyncDataClass asyncRequestObject = new AsyncDataClass();
                //asyncRequestObject.execute(serverUrl,url,nombres, especiespiner.getSelectedItem().toString(),edadd,razaa,fecha,spinnergenero.getSelectedItem().toString(),usuaio);

                uploadMultipart();
             // registratMascota();
                finish();

            }
        });


    }
    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS);
        }

        return false;
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
                nameValuePairs.add(new BasicNameValuePair("url", params[8]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Insercion : "+nombres+","+edadd+","+razaa+","+usuaio);

                System.out.println("Respuesta del server : " + jsonResult.toString());

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
                Toast.makeText(RegistraMascota.this, "exelente", Toast.LENGTH_LONG).show();


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
    private void registerMascota(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGMAS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegistraMascota.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistraMascota.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                Dbase db = new Dbase( getApplicationContext() );
                        params.put( "nombre",nombres);
                        params.put("especie", especiespiner.getSelectedItem().toString());
                        params.put("edad", edadd);
                        params.put("raza", razaa);
                         params.put("url", url);

                        params.put("fechanacimiento", fecha);

                        params.put("genero", spinnergenero.getSelectedItem().toString());

                        params.put("usuario", db.obtener(1));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        especiespiner.setAdapter(spinnerAdapter);
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
            //AsyncDataClass asyncRequestObject = new AsyncDataClass();
            //asyncRequestObject.execute(serverUrl, spinnerFood.getSelectedItem().toString(),  nombres,edadd,razaa,fecha,spinner.getSelectedItem().toString(),usuaio,url);

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

    }
    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistraMascota.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }
    public void uploadMultipart() {
        Dbase db = new Dbase( getApplicationContext() );

        //getting name for the image
        String name = nombre.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(uriphat);
        //Uploading code
        try {

            if(path != null){
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        //  .addParameter("nombre", name)
                        .addParameter("especie", especiespiner.getSelectedItem().toString())
                        .addParameter("edad", edadd)
                        .addParameter("raza", razaa)

                        .addParameter("fechanacimiento", fecha)

                        .addParameter("genero", spinnergenero.getSelectedItem().toString())

                        .addParameter("usuario", db.obtener(1))


                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

                finish();
            }else{
                registerMascota();
            }

        } catch (Exception exc) {

            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }
    Uri uriphat ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri urii) {

                                    uriphat=urii;
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + urii);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);

                    break;
                case SELECT_PICTURE:
                    Uri uri = data.getData();
                    uriphat=uri;
                    mSetImage.setImageURI(uri);

                    break;

            }
        }
    }
    public String getPath(Uri uri) {

        if(uri!=null){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

            return path;

        }else{
            return null;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(RegistraMascota.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistraMascota.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }
}



