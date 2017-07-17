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
public class AdaptadorNotificaciones
        extends RecyclerView.Adapter<AdaptadorNotificaciones.ViewHolder> {


    private final List<Notificaciones> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView notifica;


        public ViewHolder(View v) {
            super(v);

            notifica = (TextView) v.findViewById(R.id.not);

        }
    }


    public AdaptadorNotificaciones(List<Notificaciones> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_noticias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Notificaciones item = items.get(i);

        viewHolder.notifica.setText(item.getNotifi());


    }


}