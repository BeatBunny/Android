package com.example.projectdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void search(View view) {
        Button seach = (Button) findViewById(R.id.searchButton);
        seach.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Search.class));
            }
        });
    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {
        Button player = (Button) findViewById(R.id.playerButton);
        player.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Player.class));
            }
        });

    }

    public void lists(View view) {
        Button lists = (Button) findViewById(R.id.playlistButton);
        lists.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Playlist.class));
            }
        });
    }

    public void settings(View view) {
    }


    public void white(View view) {
        Switch white = (Switch)findViewById(R.id.whitemode);
        Boolean checked = white.isChecked();
        if(checked==true){

        }
    }
}
