package com.example.nachox.faunasegura;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentotIPS extends Fragment implements AdaptadortIPS.EscuchaEventosClick{
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";


    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    LinearLayoutManager layoutManager;
    private AdaptadortIPS adaptador;

    public static FragmentotIPS nuevaInstancia(int indiceSeccion) {
        FragmentotIPS fragment = new FragmentotIPS();
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
        /*layoutManager = new GridLayoutManager(getActivity(), 1);
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadortIPS(this);
                break;

        }

        reciclador.setAdapter(adaptador);
*/ if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(getActivity(), 1);
        else
            layoutManager = new LinearLayoutManager(getActivity());

        reciclador.setLayoutManager(layoutManager);

        AdaptadortIPS adaptador = new AdaptadortIPS(this);
        reciclador.setAdapter(adaptador);
        return view;
    }
    public void onItemClick(AdaptadortIPS.ViewHolder holder, int posicion) {
        Intent intent = new Intent(getActivity(), ActividadDetalleTips.class);
        intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
    }

}
