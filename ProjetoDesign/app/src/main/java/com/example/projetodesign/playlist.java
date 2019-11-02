package com.example.projetodesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class playlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicas);
    }

    public void music(View view) {
        TextView playlis = (TextView) findViewById(R.id.asMusicas);
        playlis.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(playlist.this, music.class));
            }
        });
    }
    public void search(View view) {
        Button seach = (Button) findViewById(R.id.search);
        seach.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(playlist.this, search.class));
            }
        });
    }

    public void menu(View view) {
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(playlist.this, MainActivity.class));
            }
        });

    }

    public void player(View view) {
        Button player = (Button) findViewById(R.id.player);
        player.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(playlist.this, Player.class));
            }
        });

    }

    public void lists(View view) {
    }

    public void settings(View view) {
        Button setting = (Button) findViewById(R.id.settings);
        setting.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(playlist.this, settings.class));
            }
        });
    }
}