package com.example.nachox.faunasegura;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.squareup.picasso.Picasso;

public class AdaptadorMisMascotas extends RecyclerView.Adapter<AdaptadorMisMascotas.MyViewHolder> {
    private String[] nombree;
    private String[] edadd;
    private String[] razaa;
    private String[] urll;
    private String[] generoo;
    private EscuchaEventosClick escucha;

    interface EscuchaEventosClick {
        void onItemClick(RecyclerView.ViewHolder holder, int posicion);
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView nombred;
        public TextView razad;
        public TextView generod;
        public TextView edadd;
        private EscuchaEventosClick escucha;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            nombred = (TextView) v.findViewById(R.id.nombrecard);
            razad = (TextView) v.findViewById(R.id.razacard);
            generod = (TextView) v.findViewById(R.id.generocard);
            edadd = (TextView) v.findViewById(R.id.edadcard);
            imageView =  (ImageView) v.findViewById(R.id.imagenmascota);
            //v.setOnClickListener(this);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorMisMascotas(String[] nombre, String[] edad, String[] raza, String[] genero,String[] url) {
        nombree = nombre;
   edadd=edad;
        razaa=raza;
        generoo=genero;
        urll=url;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdaptadorMisMascotas.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragmento_mis_mascotas, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.nombred.setText(nombree[position]);
        holder.edadd.setText(edadd[position]);
        holder.razad.setText(razaa[position]);
        holder.generod.setText(generoo[position]);

        Picasso.with(holder.imageView.getContext()).load(urll[position]).into(holder.imageView);
        //  viewHolder.imageView.setImageResource(mResIds[position]);


        //Glide.with(holder.imageView.getContext()).load(urll[position]).placeholder(R.drawable.adopta).crossFade().signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).error(R.drawable.busqueda).into(holder.imageView);
    // Glide.with( holder.itemView.getContext()).load(urll[position]).centerCrop().into(holder.imageView);





    }


    public int getItemCount() {
        return nombree.length;
    }
}