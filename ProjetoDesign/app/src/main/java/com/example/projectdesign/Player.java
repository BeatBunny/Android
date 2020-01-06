package com.example.projectdesign;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Player extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
    public void search(View view) {
        Button seach = (Button) findViewById(R.id.searchButton);
        seach.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Player.this, Search.class));
            }
        });
    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Player.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {

    }

    public void lists(View view) {
        Button lists = (Button) findViewById(R.id.playlistButton);
        lists.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Player.this, Playlist.class));
            }
        });
    }

    public void play(View view) {
    }
}
