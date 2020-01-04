package com.example;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.projectdesign.R;

import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;


public class Detalhes_Musica_Activity extends AppCompatActivity {

    public Musica musica;
    private int idMusica;
    private TextView Artist, Muisic;
    private ImageView MusicCover;
    private ProgressBar progressBar;
    private Button go_back, skip, play, stop;
    private MediaPlayer mediaPlayer;
    SeekBar sbar;

    private TextView textViewTituloJava;
    private TextView textViewSerieJava;
    private TextView textViewAnoJava;
    private TextView textViewAutorJava;
    private TextView nomeArtistaJava;

    private ImageView imageViewCoverMusicJava;

    private String currentIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        sbar = findViewById(R.id.seekBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentIP = BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput();
        idMusica = getIntent().getIntExtra("DETALHES", -1);

        musica = BeatBunnySingleton.getInstance(getApplicationContext()).getMusica(idMusica);

        textViewTituloJava = findViewById(R.id.nomeMusica);
        imageViewCoverMusicJava = findViewById(R.id.imageViewCoverMusica);
        nomeArtistaJava = findViewById(R.id.nomeArtistaMusicaActivityPlayer);

        if(musica != null){
            setTitle(musica.getTitle());
            textViewTituloJava.setText(musica.getTitle());
            nomeArtistaJava.setText(musica.getProducer());
            Glide.with(getApplicationContext())
                    .load("http://"+currentIP+":80/BeatBunny/advanced/frontend/web/"+musica.getMusiccover()+"/image_"+musica.getId()+".png")
                    .placeholder(R.drawable.logo_white)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter().into(imageViewCoverMusicJava);

            play = this.findViewById(R.id.play);
            mediaPlayer = new MediaPlayer();
            play.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                    String musicURL= "http://"+currentIP+":80/BeatBunny/advanced/frontend/web/" +musica.getMusicpath()+"/music_"+musica.getId()+"_"+musica.getTitle()+".mp3";
                    try {
                        mediaPlayer.setDataSource(musicURL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            });
            Thread runSekBar = new runSeekBar();
            runSekBar.start();
        }else{
            setTitle("Music X");
        }
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
                if (mediaPlayer != null) {
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
//}
}
