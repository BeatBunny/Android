package com.example.projectdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vsearch);
    }

    public void search(View view) {

    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Search.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {
        Button player = (Button) findViewById(R.id.playerButton);
        player.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Search.this, Player.class));
            }
        });

    }

    public void lists(View view) {
        Button lists = (Button) findViewById(R.id.playlistButton);
        lists.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Search.this, Playlist.class));
            }
        });
    }
}
