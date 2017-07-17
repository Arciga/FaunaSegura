package com.example.nachox.faunasegura;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorNoticias
        extends RecyclerView.Adapter<AdaptadorNoticias.ViewHolder> {


    private final List<Noticias> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView noticiassss;


        public ViewHolder(View v) {
            super(v);

            noticiassss = (TextView) v.findViewById(R.id.not);

        }
    }


    public AdaptadorNoticias(List<Noticias> items) {
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
        Noticias item = items.get(i);

        viewHolder.noticiassss.setText(item.getNoticiass());


    }


}