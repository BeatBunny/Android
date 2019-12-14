package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserJSONParser {

    public static ArrayList<User> parserJsonUsers(JSONArray response, Context context){
        ArrayList<User> listaUser = new ArrayList<User>();

        try {
            for (int i = 0; i < response.length(); i++){
                JSONObject user = (JSONObject) response.get(i);
                int idUser = user.getInt("id");
                String usernameUser = user.getString("username");
                String authKeyUser = user.getString("authKey");
                String emailUser = user.getString("email");

                User auxUser = new User(idUser, usernameUser, authKeyUser, emailUser);
                listaUser.add(auxUser);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return listaUser;
    }

    public static User parserJsonUser(String response, Context context){
        User auxUser = null;
        try {
            JSONObject user = new JSONObject(response);

            int idUser = user.getInt("id");
            String usernameUser = user.getString("username");
            String authKeyUser = user.getString("authKey");
            String emailUser = user.getString("email");

            auxUser = new User(idUser, usernameUser, authKeyUser, emailUser);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxUser;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }




    public static String parserJsonLogin(String response, Context context){
        String token = "";
        try {
            JSONObject login = new JSONObject(response);
            token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }
}
