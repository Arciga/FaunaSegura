package com.example.nachox.faunasegura;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class AdaptadortIPS extends RecyclerView.Adapter<AdaptadortIPS.ViewHolder>   {

    private EscuchaEventosClick escucha;
    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public static final List<Tips> tips = new ArrayList<>();
    private List<Tips> items;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        public TextView nombre;

        public ImageView imagen;
        public TextView texto;

        public ViewHolder(View v) {
            super(v);



            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {escucha.onItemClick(this, getAdapterPosition());}
    }




    public AdaptadortIPS(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {return Tips.TIPS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Tips item = Tips.TIPS.get(i);

        Glide.with(

        viewHolder.itemView.getContext()).load(item.getIdDrawable()).centerCrop().into(viewHolder.imagen);



    }


}