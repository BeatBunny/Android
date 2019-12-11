package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Musica;
import com.example.models.BeatBunnySingleton;
import com.example.projectdesign.R;

public class Detalhes_Musica_Activity extends AppCompatActivity {

    public Musica musica;
    private int idMusica;


    private TextView textViewTituloJava;
    private TextView textViewSerieJava;
    private TextView textViewAnoJava;
    private TextView textViewAutorJava;

    private ImageView imageViewCoverMusicJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //TER A SETINHA DE TRAS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idMusica = getIntent().getIntExtra("DETALHES", -1);

        musica = BeatBunnySingleton.getInstance(getApplicationContext()).getMusica(idMusica);

        textViewTituloJava = findViewById(R.id.tituloMusica);

        imageViewCoverMusicJava = findViewById(R.id.imageViewMusicCover);

        if(musica != null){
            setTitle(musica.getTitle());
            textViewTituloJava.setText(musica.getTitle());
            /*textViewAnoJava.setText(""+musica.getAno());
            textViewAutorJava.setText(musica.getAutor());
            textViewSerieJava.setText(musica.getSerie());*/
            imageViewCoverMusicJava.setImageResource(musica.getMusiccover());
        }else{
            setTitle("Music X");
        }
    }


}
