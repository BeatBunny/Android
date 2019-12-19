package com.example.projectdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Playlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicas);
    }

    public void music(View view) {
        TextView playlis = (TextView) findViewById(R.id.asMusicas);
        playlis.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Playlist.this, Music.class));
            }
        });
    }
    public void search(View view) {
        Button seach = (Button) findViewById(R.id.searchButton);
        seach.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Playlist.this, Search.class));
            }
        });
    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Playlist.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {
        Button player = (Button) findViewById(R.id.playerButton);
        player.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Playlist.this, Player.class));
            }
        });

    }

}
