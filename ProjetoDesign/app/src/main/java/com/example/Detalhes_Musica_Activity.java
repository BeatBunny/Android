package com.example;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;

import java.io.IOException;

import static com.example.projectdesign.R.drawable.logo_white;


public class Detalhes_Musica_Activity extends AppCompatActivity {

    public Musica musica;
    private int idMusica;
    private Boolean is_bought;
    private TextView Artist, Muisic;
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

    public Detalhes_Musica_Activity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        currentIP = BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput();
        idMusica = getIntent().getIntExtra("DETALHES", -1);
        is_bought = getIntent().getBooleanExtra("IS_BOUGHT", false);

        musica = BeatBunnySingleton.getInstance(getApplicationContext()).getMusica(idMusica);

        textViewTituloJava = findViewById(R.id.nomeMusica);
        imageViewCoverMusicJava = findViewById(R.id.imageViewCoverMusica);
        nomeArtistaJava = findViewById(R.id.nomeArtistaMusicaActivityPlayer);
        buttonBuySong = findViewById(R.id.buttonBuySong);
        play = this.findViewById(R.id.playMusic);
        back = this.findViewById(R.id.backMusic);
        sbar = findViewById(R.id.seekBarMusic);
        next = findViewById(R.id.nextMusic);

        if(musica != null) {
            setTitle(musica.getTitle());
            textViewTituloJava.setText(musica.getTitle());
            nomeArtistaJava.setText(musica.getProducer());
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
        play.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        sbar.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
    }

    private void temCompradoPorIssoMostra(){
        buttonBuySong.setVisibility(View.GONE);
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
        finish();
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



}



//    public void back(View view) {
//        go_back.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
//                synchronized (this) {
//                    i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT));
//                }
//            }
//        });
//    }

