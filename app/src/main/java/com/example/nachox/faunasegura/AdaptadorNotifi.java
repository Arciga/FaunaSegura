package com.example.nachox.faunasegura;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorNotifi
        extends RecyclerView.Adapter<AdaptadorNotifi.ViewHolder> {


    private final List<Notificaciones> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView edad;
        public TextView raza;
        public TextView genero;


        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombrecard);
            edad = (TextView) v.findViewById(R.id.edadcard);
            raza = (TextView) v.findViewById(R.id.razacard);
            genero = (TextView) v.findViewById(R.id.generocard);
        }
    }


    public AdaptadorNotifi(List<Notificaciones> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_notificaciones, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Notificaciones item = items.get(i);

        viewHolder.nombre.setText(item.getNombre());

        viewHolder.edad.setText(item.getEdad());
        viewHolder.raza.setText(item.getRaza());
        viewHolder.genero.setText(item.getGenero());


    }


}