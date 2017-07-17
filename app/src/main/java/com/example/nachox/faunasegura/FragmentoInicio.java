
package com.example.nachox.faunasegura;

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
    public class FragmentoInicio extends Fragment {

        private static final String INDICE_SECCION
                = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

        private RecyclerView reciclador;
        private GridLayoutManager layoutManager;
        private AdaptadorQuines adaptador;

        public static FragmentoInicio nuevaInstancia(int indiceSeccion) {
            FragmentoInicio fragment = new FragmentoInicio();
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
            final LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
            reciclador.setLayoutManager(mLayoutManager);

            int indiceSeccion = getArguments().getInt(INDICE_SECCION);

            switch (indiceSeccion) {
                case 0:
                    adaptador = new AdaptadorQuines(About.ABOUT);
                    break;

            }

            reciclador.setAdapter(adaptador);

            return view;
        }

    }


