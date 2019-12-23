package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.models.Playlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaylistJSONParser {


    public static ArrayList<Playlist> parserJsonPlaylists(JSONArray response, Context context){
        ArrayList<Playlist> listaPlaylists = new ArrayList<Playlist>();
        try {
            for (int i = 0; i < response.length(); i++){
                JSONObject playlist = (JSONObject) response.get(i);

                int idPlaylist = playlist.getInt("id");
                String nomePlaylist = playlist.getString("nome");
                String creationdatePlaylist = playlist.getString("creationdate");
                Playlist auxPlaylist = new Playlist(idPlaylist, nomePlaylist, creationdatePlaylist);
                listaPlaylists.add(auxPlaylist);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return listaPlaylists;
    }

    public static Playlist parserJsonMusicsFromPlaylist(String response, Context context){
        Playlist auxMusica = null;
        try {
            JSONObject musica = new JSONObject(response);

            int idMusica = musica.getInt("id");
            String titleMusica = musica.getString("title");
            String coverMusica = musica.getString("musiccover");
            String genreMusica = musica.getString("genres_id");
            String launchdateMusica = musica.getString("launchdate");
            String lyricsMusica = musica.getString("lyrics");
            String pathMusica = musica.getString("musicpath");
            String producerMusica = musica.getString("producer_id");
            double pvpMusica = musica.getDouble("pvp");

       //     auxMusica = new Playlist(idMusica, titleMusica, launchdateMusica, pathMusica, genreMusica, lyricsMusica, coverMusica, producerMusica, (float) pvpMusica);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxMusica;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
