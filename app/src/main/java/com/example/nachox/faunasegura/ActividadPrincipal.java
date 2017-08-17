package com.example.nachox.faunasegura;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    TextView textouser;
   // String datoNombre;
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
       Intent intent=getIntent();
       Bundle extras =intent.getExtras();
         //datoNombre=(String)extras.get("nombre");
        agregarToolbar();
         // textouser= (TextView) findViewById(R.id.usertex);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlContainer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        textouser = (TextView) headerView.findViewById(R.id.usertex);
        Dbase db = new Dbase( getApplicationContext() );
       textouser.setText(db.obtener(1));

        Toast.makeText(ActividadPrincipal.this, "Esta Aplicacion puede Contener Errores :c", Toast.LENGTH_LONG).show();
       /* ---------------

       refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );*/   //------------------



        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));

        }





    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }


    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }
    private void seleccionarItem(MenuItem itemDrawer) {
        Dbase db = new Dbase( getApplicationContext() );

        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoNQN();
                break;
            case R.id.wiki:
                fragmentoGenerico = new FragmentoFauna();
                break;
            case R.id.tips:
                fragmentoGenerico = new FragmentotIPS();
                break;

            case R.id.denuncia:
                startActivity(new Intent(this, Denuncias.class));
                break;
            case R.id.registra:


              //  System.out.println(id);

                startActivity(new Intent(this, RegistraMascota.class));
                break;
            case R.id.adopta:
                Toast.makeText(ActividadPrincipal.this, "No Disponible :c", Toast.LENGTH_LONG).show();
                break;
            case R.id.club:
                startActivity(new Intent(this, GuardianesDelaFauna.class));
                /// /Toast.makeText(ActividadPrincipal.this, "No Disponible :c", Toast.LENGTH_LONG).show();

                break;
            case R.id.micuenta:
                startActivity(new Intent(this, PerfilActivity.class));
                break;
            case R.id.cerrar:
               // db.eliminar();
                startActivity(new Intent(getBaseContext(), ActividadPrincipal.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                super.finish();


                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
