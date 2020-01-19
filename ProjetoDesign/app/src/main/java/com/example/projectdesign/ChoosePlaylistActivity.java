package com.example.projectdesign;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listeners.PlaylistListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Playlist;
import com.example.utils.PlaylistJSONParser;

import java.util.ArrayList;

public class ChoosePlaylistActivity extends AppCompatActivity implements PlaylistListener {

    private ListView listaComPlaylists;
    private int idMusica;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_playlist);
        BeatBunnySingleton.getInstance(getApplicationContext()).getAllPlaylistsFromUserAPI(getApplicationContext(), PlaylistJSONParser.isConnectionInternet(getApplicationContext()));

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarTop);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK));
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);


        idMusica = getIntent().getIntExtra("idMusica", -1);
        listaComPlaylists = findViewById(R.id.listaComTodasAsPlaylists);
        listaComPlaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Playlist playlist = (Playlist) parent.getItemAtPosition(position);
                BeatBunnySingleton.getInstance(getApplicationContext()).putSongInPlaylist(getApplicationContext(), PlaylistJSONParser.isConnectionInternet(getApplicationContext()), idMusica, playlist.getId());

            }
        });

        BeatBunnySingleton.getInstance(getApplicationContext()).setPlaylistListener(this);
    }

    private void setSupportActionBar(Toolbar toolbarTop) {
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onResume() {

        BeatBunnySingleton.getInstance(getApplicationContext()).getAllPlaylistsFromUserAPI(getApplicationContext(), PlaylistJSONParser.isConnectionInternet(getApplicationContext()));
        super.onResume();
    }

    @Override
    public void onRefreshListaPlaylist(ArrayList<Playlist> playlists) {
        ArrayAdapter<Playlist> adapter = new ArrayAdapter<Playlist>(this, R.layout.item_lista_playlist, R.id.nomePlaylist, playlists);

        //ListaPlaylistsAdapter adapter = new ListaPlaylistsAdapter(getApplicationContext(), playlists);

        listaComPlaylists.setAdapter(adapter);
    }

    @Override
    public void onUpdateListaMusica(Playlist playlist, int operacao) {

    }
}
