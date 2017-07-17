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
public class AdaptadorQuines extends RecyclerView.Adapter<AdaptadorQuines.ViewHolder> {


    private final List<About> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView que;
        public TextView qui;

        public ViewHolder(View v) {
            super(v);

            que = (TextView) v.findViewById(R.id.quehacen);
            qui = (TextView) v.findViewById(R.id.textoquinesson);
        }
    }


    public AdaptadorQuines(List<About> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        About item = items.get(i);

        viewHolder.que.setText(item.getquehacen());
        viewHolder.qui.setText(item.getQuienessono());


    }


}