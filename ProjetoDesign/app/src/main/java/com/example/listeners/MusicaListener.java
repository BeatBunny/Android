package com.example.listeners;

import com.example.modelo.Musica;

import java.util.ArrayList;

public interface MusicaListener {
    void onRefreshListaMusica(ArrayList<Musica> musicas);
    void onUpdateListaMusica(Musica musica, int operacao);
}
