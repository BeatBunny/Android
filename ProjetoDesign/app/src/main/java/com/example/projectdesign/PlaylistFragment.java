package com.example.projectdesign;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.listeners.PlaylistListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.Playlist;
import com.example.utils.PlaylistJSONParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PlaylistFragment extends Fragment implements PlaylistListener {

    private int musicNumber = 0;
    private ArrayList<Playlist> playlists;
    private ExpandingList expandingList;


    public PlaylistFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        BeatBunnySingleton.getInstance(getContext()).getAllPlaylistsFromUserAPI(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()));

        expandingList = (ExpandingList) view.findViewById(R.id.expanding_list_playlists);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!PlaylistJSONParser.isConnectionInternet(getContext())){
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else{
                    createDialog();
                }
            }
        });

        BeatBunnySingleton.getInstance(getContext()).setPlaylistListener(this);

        return view;
    }

    private void createDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.new_playlist_dialog);
        TextView titulo = dialog.findViewById(R.id.new_playlist_dialog_title);
        titulo.setText("New Playlist");
        final EditText textNomePlaylist = dialog.findViewById(R.id.newPlaylist);
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSavePlaylist);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancelPlaylist);

        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeatBunnySingleton.getInstance(getContext()).createNewPlaylist(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()), textNomePlaylist.getText().toString());
                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onRefreshListaPlaylist(ArrayList<Playlist> playlist) {
        expandingList.removeAllViews();
        playlists = playlist;
        for( final Playlist pl : playlists ) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout_playlists);

            ((TextView) item.findViewById(R.id.title_playlist)).setText(pl.getNome());

            System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            if(pl.getListaMusicasDestaPlaylist() != null){
                if( pl.getListaMusicasDestaPlaylist().size() > 0){
                    int itemsParaForeach = (pl.getListaMusicasDestaPlaylist().size() + 1);
                    item.createSubItems(itemsParaForeach);
                    ArrayList<Musica> listaDeMusicasDaPlaylist = pl.getListaMusicasDestaPlaylist();
                    for (int i = 0; i < pl.getListaMusicasDestaPlaylist().size() ; i++){
                        View subItem = item.getSubItemView(i);
                        TextView musicThingy = subItem.findViewById(R.id.sub_playlist_textview);
                        Button deletePlaylistButton = subItem.findViewById(R.id.buttonDeletePlaylist);
                        musicThingy.setVisibility(View.VISIBLE);
                        deletePlaylistButton.setVisibility(View.GONE);
                        musicThingy.setText(listaDeMusicasDaPlaylist.get(i).getTitle());

                    }
                    View dubItem = item.getSubItemView(pl.getListaMusicasDestaPlaylist().size());
                    Button deletePlaylist = dubItem.findViewById(R.id.buttonDeletePlaylist);
                    deletePlaylist.setVisibility(View.VISIBLE);
                    deletePlaylist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createDialogDeletePlaylist(pl);
                        }
                    });
                }
                else{
                    int itemsParaForeach = 1;
                    item.createSubItems(itemsParaForeach);
                    View subItem = item.getSubItemView(0);
                    Button deletePlaylist = subItem.findViewById(R.id.buttonDeletePlaylist);
                    deletePlaylist.setVisibility(View.VISIBLE);
                    deletePlaylist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createDialogDeletePlaylist(pl);
                        }
                    });
                }

            }

            item.setIndicatorColorRes(R.color.common_google_signin_btn_text_light_default);
            item.setIndicatorIconRes(R.drawable.ic_musicbeat);

        }
    }


    private void createDialogDeletePlaylist(final Playlist playlist) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.delete_playlist_dialog);
        TextView titulo = dialog.findViewById(R.id.delete_playlist_dialog_title);
        titulo.setText("Delete Playlist");
        dialog.setTitle("Delete Playlist");
        final TextView textNomePlaylist = dialog.findViewById(R.id.playlistToDelete);
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonDeleteSavePlaylist);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonDeleteCancelPlaylist);
        textNomePlaylist.setText(playlist.getNome());
        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeatBunnySingleton.getInstance(getContext()).deletePlayistAPI(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()), playlist.getId());
                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onUpdateListaMusica(Playlist playlist, int operacao) {

    }

    @Override
    public void onResume() {
        BeatBunnySingleton.getInstance(getContext()).getAllPlaylistsFromUserAPI(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()));
        super.onResume();

    }

}
