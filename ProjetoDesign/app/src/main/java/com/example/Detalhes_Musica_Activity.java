package com.example;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.listeners.PlaylistListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.Playlist;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;
import com.example.utils.PlaylistJSONParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.projectdesign.R.drawable.logo_white;


public class Detalhes_Musica_Activity extends AppCompatActivity implements PlaylistListener {

    public Musica musica;
    private int idMusica;
    private Boolean is_bought;
    private TextView Artist, Muisic,pvp;
    private ImageView MusicCover;
    private ProgressBar progressBar;
    private ImageButton play;
    private Button stop,back;
    private Button next;
    private MediaPlayer mediaPlayer;
    private Button buttonBuySong;
    private SeekBar sbar;
    private TextView textViewTituloJava;
    private TextView textViewSerieJava;
    private TextView textViewAnoJava;
    private TextView textViewAutorJava;
    private TextView nomeArtistaJava;

    private ImageView imageViewCoverMusicJava;
    private int isPlaying;
    private String currentIP;
    private Toolbar mToolbar;
    private ArrayList<Playlist> listaComTodasAsPlaylists;

    public Detalhes_Musica_Activity()  {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final boolean[] stoped = {false};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarTop);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK));
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); b


        BeatBunnySingleton.getInstance(getApplicationContext()).getAllPlaylistsFromUserAPI(getApplicationContext(), PlaylistJSONParser.isConnectionInternet(getApplicationContext()));


        BeatBunnySingleton.getInstance(getApplicationContext()).setPlaylistListener(this);

        currentIP = BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput();
        idMusica = getIntent().getIntExtra("DETALHES", -1);
        is_bought = getIntent().getBooleanExtra("IS_BOUGHT", false);

        musica = BeatBunnySingleton.getInstance(getApplicationContext()).getMusica(idMusica);
        textViewTituloJava = findViewById(R.id.nomeMusica);
        imageViewCoverMusicJava = findViewById(R.id.imageViewCoverMusica);
        nomeArtistaJava = findViewById(R.id.nomeArtistaMusicaActivityPlayer);
        buttonBuySong = findViewById(R.id.buttonBuySong);
        pvp = findViewById(R.id.pvpText);
        play = this.findViewById(R.id.playMusic);
        back = this.findViewById(R.id.backMusic);
        sbar = findViewById(R.id.seekBarMusic);
        next = findViewById(R.id.nextMusic);

        if(musica != null) {
            setTitle(musica.getTitle());
            textViewTituloJava.setText(musica.getTitle());
            nomeArtistaJava.setText(musica.getLaunchdate());
            pvp.setText(String.valueOf(musica.getPvp())+" €");
            Glide.with(getApplicationContext())
                    .load("http://" + currentIP + ":80/BeatBunny/advanced/frontend/web/" + musica.getMusiccover() + "/image_" + musica.getId() + ".png")
                    .placeholder(logo_white)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter().into(imageViewCoverMusicJava);

            mediaPlayer = new MediaPlayer();
            if(is_bought){
                temCompradoPorIssoMostra();
            }
            else{
                naoTemCompradoPorIssoEsconde();
            }


        }else{
            finish();
        }
    }

    private void naoTemCompradoPorIssoEsconde(){
        buttonBuySong.setVisibility(View.VISIBLE);
        pvp.setVisibility(View.VISIBLE);
        play.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        sbar.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
    }

    private void temCompradoPorIssoMostra(){
        buttonBuySong.setVisibility(View.GONE);
        pvp.setVisibility(View.GONE);
        final String musicURL = "http://" + currentIP + ":80/BeatBunny/advanced/frontend/web/" + musica.getMusicpath() + "/music_" + musica.getId() + "_" + musica.getTitle() + ".mp3";
        try {
            mediaPlayer.setDataSource(musicURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        play.setOnClickListener(new View.OnClickListener() {
            private boolean Playing = false;
            private boolean GotMusic = false;
            public void onClick(View v) {
                if (back.getText()==" "){
                    this.Playing=false;
                    this.GotMusic=false;
                }
                if (!this.Playing) {
                    back.setText(".");
                    if(this.GotMusic ) {
                        mediaPlayer.start();
                    }else {
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                                sbar.setProgress(0);
                                sbar.setMax(mediaPlayer.getDuration());
                            }
                        });
                    }
                    this.Playing = true;
                    play.setBackgroundResource(R.drawable.stop);
                } else {
                    mediaPlayer.pause();
                    play.setBackgroundResource(R.drawable.plays);
                    this.Playing=false;
                    this.GotMusic=true;
                }
            }

        });
        final Thread runSekBar = new runSeekBar();
        runSekBar.start();
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                play.setBackgroundResource(R.drawable.plays);
                back.setText(" ");
                back.setTextScaleX(1);
                mediaPlayer.stop();
            }
        });
    }


    private void setSupportActionBar(Toolbar toolbarTop) {

    }

    @Override
    public boolean onSupportNavigateUp(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        finish();
        return true;
    }

    public void onClickBuyMusic(View view) {
        //TODO: BUY MUSIC IN SINGLETON
        BeatBunnySingleton.getInstance(getApplicationContext()).buySongAPI(getApplicationContext(), MusicaJSONParser.isConnectionInternet(getApplicationContext()),idMusica);
        BeatBunnySingleton.getInstance(getApplicationContext()).getSaldoAfterPurchase(getApplicationContext(), MusicaJSONParser.isConnectionInternet(getApplicationContext()));
        finish();
    }

    public void play(View view) {
    }

    @Override
    public void onRefreshListaPlaylist(ArrayList<Playlist> playlist) {
        listaComTodasAsPlaylists = playlist;
    }

    @Override
    public void onUpdateListaMusica(Playlist playlist, int operacao) {

    }

    public class runSeekBar extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sbar.post(new Runnable() {
                    @Override
                    public void run() {
                        sbar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                });
            }
        }
    }



    public Dialog createDialog2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        ArrayAdapter<Playlist> arrayAdapter = new ArrayAdapter<Playlist>(Detalhes_Musica_Activity.this, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.addAll(listaComTodasAsPlaylists);
        builder.setTitle("Add this song to a Playlist")
                .setIcon(R.drawable.logo)
                .setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        return builder.show();
    }

    public Dialog createDialog() {

        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.setContentView(R.layout.add_song_to_playlist);
        dialog.setTitle("New Playlist");
/*
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSaveAddSongToPlaylist);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancelNotAddSongToPlaylist);

        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BeatBunnySingleton.getInstance(getContext()).setIP(textNomePlaylist.getText().toString());
                //Toast.makeText(getContext(), BeatBunnySingleton.getInstance(getContext()).getIPInput(), Toast.LENGTH_SHORT).show();

                //BeatBunnySingleton.getInstance(getContext()).createNewPlaylist(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()), textNomePlaylist.getText().toString());

                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }*
        });
        if(!isFinishing()){
            dialog.show();
        }*/
        return dialog;
    }


    @Override
    public boolean isFinishing() {
        return super.isFinishing();
    }

    @Override
    public void onResume() {
        BeatBunnySingleton.getInstance(getApplicationContext()).getAllPlaylistsFromUserAPI(getApplicationContext(), PlaylistJSONParser.isConnectionInternet(getApplicationContext()));
        FloatingActionButton fab = findViewById(R.id.fabAddSongToPlaylist);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!PlaylistJSONParser.isConnectionInternet(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else{
                    createDialog();
                }
            }
        });
        super.onResume();
    }


}

