package com.example.listeners;

import com.example.models.Playlist;

import java.util.ArrayList;

public interface PlaylistListener {
    void onRefreshListaPlaylist(ArrayList<Playlist> playlist);
    void onUpdateListaMusica(Playlist playlist, int operacao);
}
