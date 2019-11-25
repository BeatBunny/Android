package com.example.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.modelo.Musica;
import com.example.projetodesign.R;

import java.util.ArrayList;

public class GrelhaMusicaAdaptor extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Musica> musicas;


    public GrelhaMusicaAdaptor(Context context, ArrayList<Musica> musicas) {
        this.context = context;
        this.musicas = musicas;
    }

    @Override
    public int getCount() {
        return musicas.size();
    }

    @Override
    public Object getItem(int position) {
        return musicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.item_grelha_musica, null);

        GrelhaMusicaAdaptor.ViewHolderGrelha viewHolderGrelha = (GrelhaMusicaAdaptor.ViewHolderGrelha) convertView.getTag();
        if (viewHolderGrelha == null) {
            viewHolderGrelha = new ViewHolderGrelha(convertView);
            convertView.setTag(viewHolderGrelha);
        }
        viewHolderGrelha.update(position, musicas);
        return convertView;
    }

    public class ViewHolderGrelha {
        private ImageView image;
        public ViewHolderGrelha(View view) {
            image = view.findViewById(R.id.imageViewGrelha);
        }
        public void update(int position, ArrayList<Musica> musicas) {
            Musica musica = musicas.get(position);
            image.setImageResource(musica.getMusiccover());
        }
    }
}
