package com.example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.projectdesign.R;

public class Detalhes_Musica_Activity extends AppCompatActivity {

    public Musica musica;
    private int idMusica;


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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_player);
        //TER A SETINHA DE TRAS
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
            /*textViewAnoJava.setText(""+musica.getAno());
            textViewAutorJava.setText(musica.getAutor());
            textViewSerieJava.setText(musica.getSerie());*/
            //imageViewCoverMusicJava.setImageResource(musica.getMusiccover());
            Glide.with(getApplicationContext())
                    .load("http://"+currentIP+":80/BeatBunny/advanced/frontend/web/"+musica.getMusiccover()+"/image_"+musica.getId()+".png")
                    .placeholder(R.drawable.logo_white)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter().into(imageViewCoverMusicJava);
        }else{
            setTitle("Music X");
        }
    }


}
