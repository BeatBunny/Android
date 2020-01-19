package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.models.Playlist;
import com.example.projectdesign.R;

import java.util.ArrayList;

public class ListaPlaylistsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Playlist> playlists;

    public ListaPlaylistsAdapter(Context context, ArrayList<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return playlists.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_playlist, null);

        ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
        if (viewHolderLista == null){
            viewHolderLista = new ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(position, playlists);

        return convertView;
    }

    public class ViewHolderLista{

        private TextView nomePlaylist;

        public ViewHolderLista(View view){
            nomePlaylist = view.findViewById(R.id.nomePlaylist);
        }

        public void update(int position, ArrayList<Playlist> playlists){
            Playlist playlist = playlists.get(position);
            nomePlaylist.setText(playlist.getNome());
        }
    }
}
