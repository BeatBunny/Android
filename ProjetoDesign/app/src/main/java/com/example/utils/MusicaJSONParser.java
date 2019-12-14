package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.models.Musica;
import com.example.models.User;

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
                int idUser = musica.getInt("id");
                String usernameUser = musica.getString("username");
                String authKeyUser = musica.getString("authKey");
                String emailUser = musica.getString("email");

                Musica auxMusica = new Musica(/*idUser, usernameUser, authKeyUser, emailUser*/);
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
            JSONObject user = new JSONObject(response);

            int idUser = user.getInt("id");
            String usernameUser = user.getString("username");
            String authKeyUser = user.getString("authKey");
            String emailUser = user.getString("email");

            auxMusica = new Musica(idUser, usernameUser, authKeyUser, emailUser);

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
