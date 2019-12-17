package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.models.Musica;
import com.example.projectdesign.R;

import java.util.ArrayList;

public class GrelhaMusicaAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Musica> musicas;


    public GrelhaMusicaAdapter(Context context, ArrayList<Musica> musicas) {
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

        GrelhaMusicaAdapter.ViewHolderGrelha viewHolderGrelha = (GrelhaMusicaAdapter.ViewHolderGrelha) convertView.getTag();
        if (viewHolderGrelha == null) {
            viewHolderGrelha = new ViewHolderGrelha(convertView);
            convertView.setTag(viewHolderGrelha);
        }
        viewHolderGrelha.update(position, musicas);
        return convertView;
    }

    public class ViewHolderGrelha {
        private ImageView image;
        private TextView tituloMusica;
        public ViewHolderGrelha(View view) {
            image = view.findViewById(R.id.imageViewGrelha);
            tituloMusica = view.findViewById(R.id.title);
        }
        public void update(int position, ArrayList<Musica> musicas){
            Musica musica = musicas.get(position);
            tituloMusica.setText(musica.getTitle());
            Glide.with(context)
                    .load("http://10.200.7.141:80/BeatBunny/advanced/frontend/web/"+musica.getMusiccover()+"/image_"+musica.getId()+".png")
                    .placeholder(R.drawable.logo_white)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter().into(image);
        }
    }
}
