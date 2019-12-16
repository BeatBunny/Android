package com.example.listeners;

import com.example.models.Musica;

import java.util.ArrayList;

public interface MusicaListener {
    void onRefreshListaMusica(ArrayList<Musica> musicas);
    void onUpdateListaMusica(Musica musica, int operacao);
}
