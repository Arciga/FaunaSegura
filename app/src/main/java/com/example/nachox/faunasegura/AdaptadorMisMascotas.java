package com.example.nachox.faunasegura;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorMisMascotas extends RecyclerView.Adapter<AdaptadorMisMascotas.MyViewHolder> {
    private String[] nombree;
    private String[] edadd;
    private String[] razaa;
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
            imageView =  (ImageView) v.findViewById(R.id.iv_image);
            //v.setOnClickListener(this);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorMisMascotas(String[] nombre, String[] edad, String[] raza, String[] genero) {
        nombree = nombre;
   edadd=edad;
        razaa=raza;
        generoo=genero;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdaptadorMisMascotas.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_notificaciones, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.nombred.setText(nombree[position]);
        holder.edadd.setText(edadd[position]);
        holder.razad.setText(razaa[position]);
        holder.generod.setText(generoo[position]);



    }


    public int getItemCount() {
        return nombree.length;
    }
}