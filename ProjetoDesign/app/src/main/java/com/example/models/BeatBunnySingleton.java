
package com.example.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.listeners.MusicaListener;
import com.example.listeners.UserListener;
import com.example.utils.MusicaJSONParser;
import com.example.utils.UserJSONParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import com.example.listeners.LivrosListener;
//import com.example.utils.LivroJsonParser;

public class BeatBunnySingleton {
    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD = 2;
    private static final int REMOVER_BD = 3;
    private ArrayList<Musica> musicas;
    private ArrayList<User> users;
    private static BeatBunnySingleton INSTANCE = null;
    private BeatBunnyBDHelper beatBunnyBD = null;
    private MusicaListener musicaListener;

    private UserListener userListener;

    private String tokenAPI = "AMSI-TOKEN";

    private String mUrlAPIusers = "http://10.200.12.249/web/v1/user";
    //http://127.0.0.1:8888/web/v1/
    //http://localhost/BeatBunny/advanced/backend/web/v1
    private String mUrlAPIMusicas = "http://10.200.14.190:80/BeatBunny/advanced/backend/web/v1/music";
    private static RequestQueue volleiQueue;



    public static synchronized BeatBunnySingleton getInstance(Context context) {

        if(INSTANCE == null){
            INSTANCE = new BeatBunnySingleton(context);
            volleiQueue = Volley.newRequestQueue(context);
        }
        return INSTANCE;
    }

    public void loginUserAPI(final String username, final String password, final Context context){
        StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIusers, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onUpdateListaUsersBD(UserJSONParser.parserJsonUser(response, context), ADICIONAR_BD);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        volleiQueue.add(request);
    }


    public void getAllUsersAPI(final Context context, boolean isConnected){
        //Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            users = beatBunnyBD.getAllUsersBD();
            if(userListener != null)
                userListener.onRefreshListaUser(users);
        }
        else{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIusers, null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    users = UserJSONParser.parserJsonUsers(response, context);
                    adicionarUsersBD(users);
                    if(userListener!= null)
                        userListener.onRefreshListaUser(users);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            );
            volleiQueue.add(request);
        }
    }



    private void onUpdateListaUsersBD(User user, int operacao) {
        switch (operacao){
            case ADICIONAR_BD:
                adicionarUserBD(user);
                break;
            case EDITAR_BD:
                editarUserBD(user);
                break;
        }
    }


    public User getUser(int idUser){
        for (User u : users)
            if(u.getId() == idUser)
                return u;
        return null;
    }


    private void adicionarUsersBD(ArrayList<User> users) {
        beatBunnyBD.removerAllUsersBD();
        for (User user : users)
            adicionarUserBD(user);
    }

    private void editarUserBD(User user) {
        User auxUser = getUser(user.getId());
        if (auxUser != null)
            beatBunnyBD.guardarUserBD(auxUser);
    }

    private void adicionarUserBD(User user) {
        beatBunnyBD.adicionarUserBD(user);
    }


    //COM FOR
    public Musica getMusicaById(int idMusica){
        for(int i = 0; i < musicas.size(); i++){
            if(musicas.get(i).getId() == idMusica){
                return musicas.get(i);
            }
        }
        return null;
    }


    //COM FOREACH
    public Musica getMusica(int idMusica){
        for(Musica M : musicas){
            if(M.getId() == idMusica){
                return M;
            }
        }
        return null;
    }





    private BeatBunnySingleton(Context context) {
        musicas= new ArrayList<Musica>();
        users= new ArrayList<User>();
        beatBunnyBD = new BeatBunnyBDHelper(context);



    }


    public ArrayList<Musica> getListaMusicas() {
        musicas = beatBunnyBD.getallMusicasBD();
        return musicas;
    }


    public ArrayList<User> getListaUsers() {
        users = beatBunnyBD.getAllUsersBD();
        return users;
    }



    /*****************************Métodos que acedem à API******************************/
        public void getAllMusicasAPI(final Context context, boolean isConnected){
            //Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
            if(!isConnected){
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                musicas = beatBunnyBD.getallMusicasBD();
                if(musicaListener != null)
                    musicaListener.onRefreshListaMusica(musicas);
            }
            else{
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIMusicas, null
                , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        musicas = MusicaJSONParser.parserJsonMusicas(response, context);
                        adicionarMusicasBD(musicas);
                        if(musicaListener!= null)
                            musicaListener.onRefreshListaMusica(musicas);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                );
                volleiQueue.add(request);
            }
        }

    public void adicionarMusicaAPI(final Musica musica, final Context context){
        StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIMusicas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onUpdateListaMusicasBD(MusicaJSONParser.parserJsonMusica(response, context), ADICIONAR_BD);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("token", tokenAPI);
                params.put("title", musica.getTitle());
                params.put("cover", musica.getMusiccover());
                params.put("genre", musica.getMusicgenre());
                params.put("launchdate", musica.getLaunchdate()+"");
                params.put("lyrics", musica.getLyrics());
                params.put("path", musica.getMusicpath());
                params.put("producer", musica.getProducer());
                return params;
            }
        };
        volleiQueue.add(request);
    }


    private void onUpdateListaMusicasBD(Musica musica, int operacao) {
        switch (operacao){
            case ADICIONAR_BD:
                adicionarMusicaBD(musica);
                break;
            case EDITAR_BD:
                editarMusicaBD(musica);
                break;
            case REMOVER_BD:
                removerMusicaBD(musica.getId());
                break;
        }
    }

    public void setMusicaListener(MusicaListener musicaListener) {
        this.musicaListener = musicaListener;
    }



    public void adicionarMusicasBD(ArrayList<Musica> listaMusica){
        beatBunnyBD.removerAllMusicasBD();
        for (Musica musica : listaMusica)
            adicionarMusicaBD(musica);

    }

    public void adicionarMusicaBD(Musica musica){
        beatBunnyBD.adicionarMusicaBD(musica);
    }
    public void editarMusicaBD(Musica musica){
        Musica auxMusica = getMusica(musica.getId());
        if (auxMusica != null)
            beatBunnyBD.guardarMusicaBD(musica);
    }

    public void removerMusicaBD(int idMusica){
        Musica auxMusica = getMusica(idMusica);
        if (auxMusica!=null)
            beatBunnyBD.removerMusicaBD(idMusica);
    }


}
