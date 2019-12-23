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

                int idMusica = musica.getInt("id");
                String titleMusica = musica.getString("title");
                String coverMusica = musica.getString("musiccover");
                String genreMusica = musica.getString("genres_id");
                String launchdateMusica = musica.getString("launchdate");
                String lyricsMusica = musica.getString("lyrics");
                String pathMusica = musica.getString("musicpath");
                String producerMusica = musica.getString("profile_id");
                String pvpMusica = musica.getString("pvp");
                Musica auxMusica = new Musica(idMusica, titleMusica, launchdateMusica, pathMusica, genreMusica, lyricsMusica, coverMusica, producerMusica, Float.parseFloat(pvpMusica));
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
            String coverMusica = musica.getString("musiccover");
            String genreMusica = musica.getString("genres_id");
            String launchdateMusica = musica.getString("launchdate");
            String lyricsMusica = musica.getString("lyrics");
            String pathMusica = musica.getString("musicpath");
            String producerMusica = musica.getString("producer_id");
            double pvpMusica = musica.getDouble("pvp");

            auxMusica = new Musica(idMusica, titleMusica, launchdateMusica, pathMusica, genreMusica, lyricsMusica, coverMusica, producerMusica, (float) pvpMusica);
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
