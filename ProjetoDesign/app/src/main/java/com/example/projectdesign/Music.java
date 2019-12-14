package com.example.projectdesign;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Music extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
    }

    public void Playlists(View view) {
        TextView music = (TextView) findViewById(R.id.playlists);
        music.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, Playlist.class));
            }
        });
    }
    public void search(View view) {
        Button search = (Button) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, Search.class));
            }
        });
    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {
        Button player = (Button) findViewById(R.id.playerButton);
        player.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, Player.class));
            }
        });

    }

    public void lists(View view) {
        Button lists = (Button) findViewById(R.id.playlistButton);
        lists.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, Playlist.class));
            }
        });
    }

    public void settings(View view) {
        Button setting = (Button) findViewById(R.id.settingsButton);
        setting.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Music.this, Settings.class));
            }
        });
    }
}
