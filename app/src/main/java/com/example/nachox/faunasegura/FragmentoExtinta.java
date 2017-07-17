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
public class FragmentoExtinta extends Fragment implements AdaptadorExtinta.EscuchaEventosClick {
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";


    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    LinearLayoutManager layoutManager;
    private AdaptadorExtinta adaptador;

    public static FragmentoExtinta nuevaInstancia(int indiceSeccion) {
        FragmentoExtinta fragment = new FragmentoExtinta();
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(getActivity(), 1);
        else
            layoutManager = new LinearLayoutManager(getActivity());

        reciclador.setLayoutManager(layoutManager);


        int indiceSeccion = getArguments().getInt(INDICE_SECCION);


        switch (indiceSeccion) {
            case 2:
                adaptador = new AdaptadorExtinta(this);

        }

        reciclador.setAdapter(adaptador);

        return view;
    }
    public void onItemClick(AdaptadorExtinta.ViewHolder holder, int posicion) {
        Intent intent = new Intent(getActivity(), ActividadDetalleExtinta.class);
        intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
    }

}
