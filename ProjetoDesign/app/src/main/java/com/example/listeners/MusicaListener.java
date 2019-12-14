package com.example.listeners;

import com.example.models.Musica;

import java.util.ArrayList;

public interface MusicaListener {
    void onRefreshListaLivros(ArrayList<Musica> livros);
    void onUpdateListaLivros(Musica musica, int operacao);
}
