package com.example.nachox.faunasegura;
import android.content.Context;
import android.content.Intent;
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
    private String[] idd;
    public static final String EXTRA_POSICION = "com.herprogramacion.galerajaponesa.extra.POSICION";

    private EscuchaEventosClick escucha;
    private Context context;
    interface EscuchaEventosClick {
        void onItemClick(RecyclerView.ViewHolder holder, int posicion);
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        public TextView nombred;
        public TextView razad;
        public TextView generod;
        public TextView edadd;
        public TextView iddd;

        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            nombred = (TextView) v.findViewById(R.id.nombrecard);
            razad = (TextView) v.findViewById(R.id.razacard);
            generod = (TextView) v.findViewById(R.id.generocard);
            edadd = (TextView) v.findViewById(R.id.edadcard);
            imageView = (ImageView) v.findViewById(R.id.imagenmascota);
            iddd = (TextView) v.findViewById(R.id.idmascota);
            //v.setOnClickListener(this);
        }
@Override
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public void AdaptadorMisMascotas(AdaptadorMisMascotas.EscuchaEventosClick escucha){

this.escucha = escucha;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorMisMascotas(String[] nombre, String[] edad, String[] raza, String[] genero,String[] url,String[] id,Context context) {
        //AdaptadorMisMascotas.EscuchaEventosClick escucha;

        nombree = nombre;
   edadd=edad;
        razaa=raza;
        generoo=genero;
        urll=url;
        idd=id;
        this.context = context;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.nombred.setText(nombree[position]);
        holder.edadd.setText(edadd[position]);
        holder.razad.setText(razaa[position]);
        holder.generod.setText(generoo[position]);
        holder.iddd.setText(idd[position]);

        Picasso.with(holder.imageView.getContext()).load(urll[position]).into(holder.imageView);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActividadDetalleMisMascotas.class);
                intent.putExtra("nombre", nombree[position]);
                intent.putExtra("edad", edadd[position]);
                intent.putExtra("raza", razaa[position]);
                intent.putExtra("genero", generoo[position]);
                intent.putExtra("id", idd[position]);

                intent.putExtra("url", urll[position]);
                context.startActivity(intent);

            }
        });
    }
        //  viewHolder.imageView.setImageResource(mResIds[position]);


        //Glide.with(holder.imageView.getContext()).load(urll[position]).placeholder(R.drawable.adopta).crossFade().signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).error(R.drawable.busqueda).into(holder.imageView);
    // Glide.with( holder.itemView.getContext()).load(urll[position]).centerCrop().into(holder.imageView);







    public int getItemCount() {
        return nombree.length;
    }
}