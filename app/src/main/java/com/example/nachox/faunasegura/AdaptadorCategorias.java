package com.example.nachox.faunasegura;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>   {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    private List<Animales> items;

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }



    public AdaptadorCategorias(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }
    @Override
    public int getItemCount() {
        return Animales.ANIMALES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);

        return new ViewHolder(v);
    }
   /* private  AdaptadorCategorias(List<Animales> items) {
        this.items = items;
    }*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Animales item = Animales.ANIMALES.get(i);

        Glide.with(

        viewHolder.itemView.getContext()).load(item.getIdDrawable()).centerCrop().into(viewHolder.imagen);



    }


}