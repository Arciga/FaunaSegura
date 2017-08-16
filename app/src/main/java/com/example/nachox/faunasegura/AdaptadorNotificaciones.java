package com.example.nachox.faunasegura;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdaptadorNotificaciones extends RecyclerView.Adapter<AdaptadorNotificaciones.MyViewHolder> {
    private String[] mDataset;
    private EscuchaEventosClick escucha;

    interface EscuchaEventosClick {
        void onItemClick(RecyclerView.ViewHolder holder, int posicion);
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView nombre;
        public TextView raza;

        public TextView genero;
        public TextView edad;

        private EscuchaEventosClick escucha;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v); mCardView = (CardView) v.findViewById(R.id.card_view);
            nombre = (TextView) v.findViewById(R.id.nombrecard);
            raza = (TextView) v.findViewById(R.id.razacard);
            genero = (TextView) v.findViewById(R.id.generocard);
            edad = (TextView) v.findViewById(R.id.edadcard);
           imageView =  (ImageView) v.findViewById(R.id.iv_image);
            //v.setOnClickListener(this);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorNotificaciones(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdaptadorNotificaciones.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_notificaciones, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.nombre.setText(mDataset[position]);

        holder.raza.setText(mDataset[position]);


    }

    public AdaptadorNotificaciones(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}