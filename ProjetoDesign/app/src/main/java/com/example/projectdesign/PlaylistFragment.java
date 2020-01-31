package com.example.projectdesign;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.Detalhes_Musica_Activity;
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
                    final ArrayList<Musica> listaDeMusicasDaPlaylist = pl.getListaMusicasDestaPlaylist();
                    for (int i = 0; i < pl.getListaMusicasDestaPlaylist().size() ; i++){
                        View subItem = item.getSubItemView(i);
                        LinearLayout linearLayout1 = subItem.findViewById(R.id.sub_linear_layout_1);
                        linearLayout1.setVisibility(View.VISIBLE);
                        LinearLayout linearLayout2 = subItem.findViewById(R.id.sub_linear_layout_2);
                        linearLayout2.setVisibility(View.GONE);
                        TextView musicThingy = subItem.findViewById(R.id.sub_playlist_textview);
                        Button removeMusicFromPlaylist = subItem.findViewById(R.id.buttonRemoveMusicFromPlaylist);
                        Button deletePlaylistButton = subItem.findViewById(R.id.buttonDeletePlaylist);
                        musicThingy.setVisibility(View.VISIBLE);
                        removeMusicFromPlaylist.setVisibility(View.VISIBLE);
                        deletePlaylistButton.setVisibility(View.GONE);
                        musicThingy.setText(listaDeMusicasDaPlaylist.get(i).getTitle());
                        final int finalI1 = i;
                        musicThingy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean isYours = false;
                                isYours = BeatBunnySingleton.getInstance(getContext()).checkIfYouOwnMusic(listaDeMusicasDaPlaylist.get(finalI1).getId());

                                Intent intent = new Intent(getContext(), Detalhes_Musica_Activity.class);
                                intent.putExtra("DETALHES",listaDeMusicasDaPlaylist.get(finalI1).getId());
                                intent.putExtra("IS_BOUGHT", isYours);
                                startActivity(intent);
                            }
                        });
                        final int finalI = i;
                        removeMusicFromPlaylist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createDialogRemoveMusicFromPlaylist(pl, listaDeMusicasDaPlaylist.get(finalI));
                                //createDialogDeletePlaylist(pl);
                            }
                        });


                    }
                    View dubItem = item.getSubItemView(pl.getListaMusicasDestaPlaylist().size());
                    LinearLayout linearLayout1 = dubItem.findViewById(R.id.sub_linear_layout_1);
                    linearLayout1.setVisibility(View.GONE);
                    LinearLayout linearLayout2 = dubItem.findViewById(R.id.sub_linear_layout_2);
                    linearLayout2.setVisibility(View.VISIBLE);
                    Button deletePlaylist = dubItem.findViewById(R.id.buttonDeletePlaylist);
                    Button removeMusicFromPlaylist = dubItem.findViewById(R.id.buttonRemoveMusicFromPlaylist);
                    removeMusicFromPlaylist.setVisibility(View.GONE);
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
                    LinearLayout linearLayout1 = subItem.findViewById(R.id.sub_linear_layout_1);
                    linearLayout1.setVisibility(View.GONE);
                    LinearLayout linearLayout2 = subItem.findViewById(R.id.sub_linear_layout_2);
                    linearLayout2.setVisibility(View.VISIBLE);
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

    private void createDialogRemoveMusicFromPlaylist(final Playlist pl, final Musica musicaParaRetirarDaPlaylist) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.delete_playlist_dialog);
        TextView titulo = dialog.findViewById(R.id.delete_playlist_dialog_title);
        titulo.setText("Remove Song");
        final TextView textNomePlaylist = dialog.findViewById(R.id.playlistToDelete);
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonDeleteSavePlaylist);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonDeleteCancelPlaylist);
        textNomePlaylist.setText(musicaParaRetirarDaPlaylist.getTitle());
        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeatBunnySingleton.getInstance(getContext()).removeSongFromPlaylist(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()), pl.getId(), musicaParaRetirarDaPlaylist.getId());
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
