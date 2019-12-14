package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.models.Musica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MusicaJSONParser {


    public static ArrayList<Musica> parserJsonMusicas(JSONArray response, Context context){
        ArrayList<Musica> listaMusicas = new ArrayList<Musica>();

        try {
            for (int i = 0; i < response.length(); i++){
                JSONObject musica = (JSONObject) response.get(i);

                /*
                *
                values.put(MUSIC_ID, musica.getId());
                values.put(MUSIC_TITLE, musica.getTitle());
                values.put(MUSIC_COVER, musica.getMusiccover());
                values.put(MUSIC_GENRE, musica.getMusicgenre());
                values.put(MUSIC_LAUNCHDATE, musica.getLaunchedate());
                values.put(MUSIC_LYRICS, musica.getLyrics());
                values.put(MUSIC_PATH, musica.getMusicpth());
                values.put(MUSIC_PRODUCER,musica.getProducer());
                * */

                int idMusica = musica.getInt("id");
                String titleMusica = musica.getString("title");
                String coverMusica = musica.getString("cover");
                String genreMusica = musica.getString("genre");
                String launchdateMusica = musica.getString("launchdate");
                String lyricsMusica = musica.getString("lyrics");
                String pathMusica = musica.getString("path");
                String producerMusica = musica.getString("producer");
                /*
                public Musica(int id, String title, String launchdate,String musicpth,String musicgenre,String lyrics, int musiccover, String  producer)
                */
                Musica auxMusica = new Musica(idMusica, titleMusica, launchdateMusica, pathMusica, genreMusica, lyricsMusica, coverMusica, producerMusica);
                listaMusicas.add(auxMusica);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return listaMusicas;
    }

    public static Musica parserJsonMusica(String response, Context context){
        Musica auxMusica = null;
        try {
            JSONObject musica = new JSONObject(response);

            int idMusica = musica.getInt("id");
            String titleMusica = musica.getString("title");
            String coverMusica = musica.getString("cover");
            String genreMusica = musica.getString("genre");
            String launchdateMusica = musica.getString("launchdate");
            String lyricsMusica = musica.getString("lyrics");
            String pathMusica = musica.getString("path");
            String producerMusica = musica.getString("producer");

            auxMusica = new Musica(idMusica, titleMusica, launchdateMusica, pathMusica, genreMusica, lyricsMusica, coverMusica, producerMusica);
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
