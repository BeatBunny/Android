
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

    public String CURRENT_IP = "192.168.1.65";


    private String tokenAPI = "AMSI-TOKEN";

    private String mUrlAPIusersLogin = "http://"+CURRENT_IP+":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/login";
    private String mUrlAPIusersRegister = "http://"+CURRENT_IP+":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/register";
    private String mUrlAPIMusicas = "http://"+CURRENT_IP+":80/BeatBunny/advanced/backend/web/v1/music";



    private static RequestQueue volleiQueue;

    public String getCURRENT_IP(){ return CURRENT_IP; }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public static synchronized BeatBunnySingleton getInstance(Context context) {

        if(INSTANCE == null){
            INSTANCE = new BeatBunnySingleton(context);
            volleiQueue = Volley.newRequestQueue(context);
        }
        return INSTANCE;
    }

    public void loginUserAPI(final String username, final String password, final Context context, boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            //TODO: fazer aparecer botão para entrar como guest
            //TODO: fazer função de entrar como guest
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIusersLogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    User user = UserJSONParser.parserJsonUser(response, context);
                    userListener.onRefreshListaUser(user);
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
    }


    public void registoUserAPI(final String username, final String password,final String email, final String nome, final String nif, final Context context, boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            //TODO: fazer aparecer botão para entrar como guest
            //TODO: fazer função de entrar como guest
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIusersRegister, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    User user = UserJSONParser.parserJsonUser(response, context);
                    userListener.onRefreshListaUser(user);
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
                    params.put("email", email);
                    params.put("nome", nome);
                    params.put("nif", nif);
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }

    public void setIP(String ip){
        CURRENT_IP = ip;
    }

    public User getUser(int idUser){
        for (User u : users)
            if(u.getId() == idUser)
                return u;
        return null;
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
