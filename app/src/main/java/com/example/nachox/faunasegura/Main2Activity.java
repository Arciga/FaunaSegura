package com.example.nachox.faunasegura;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.juang.jplot.PlotPastelito;
import com.juang.jplot.PlotPlanitoXY;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {

    Timer timer;
    private PlotPlanitoXY plot;
    private PlotPastelito pastel;

    private LinearLayout pantalla;
    private Button inicia,butpastel;
    Context context;
    int i=0; // contador de datos

    float [] Xd,Yd,Yd2,Xd2,Xd3,Yd3,Xd4,Yd4;
    private float[] X=new float [4000];//almacenado de
    private float[] Y=new float [4000];//datos
    private float[] X2=new float [4000];//almacenado de
    private float[] Y2=new float [4000];//datos
    private float[] X3=new float [4000];//almacenado de
    private float[] Y3=new float [4000];//datos
    private float[] X4=new float [4000];//almacenado de
    private float[] Y4=new float [4000];//datos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pantalla= (LinearLayout) (findViewById(R.id.pantalla));

        butpastel=(Button)(findViewById(R.id.butPastel));
        context=this;

        //boton plot2d


        // boton grafica de pastelito

        butpastel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GraphPastel();

            }
        });



    }


    public void GraphPastel(){

        pastel=new PlotPastelito(this,"Mascotas Registradas");
        float[] datapoints = {0,1};
        String[] etiquetas={"Perros", "Gatos"};
        pastel.SetDatos(datapoints,etiquetas);
        pastel.SetHD(true);
        pastel.SetSizeAcot(90);
        pastel.SetSizeTextGrafico(45);
        pastel.SetSizeTitulo(80);
        pastel.SetTouch(false);
        pastel.SetCentro(5);
        pastel.SetColorDato(5,0,0,0);//el dato 5 o sea "23" de color negro los ultimos 3 enteros son colores rgb
        pastel.SetColorDato(3,255,0,0);//el dato 3 o sea "8" de color rojo los ultimos 3 enteros son colores rgb

        pantalla.removeAllViews();
        pantalla.addView(pastel);






    }



}
