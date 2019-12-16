package com.example.models;

import com.example.projectdesign.R;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.listeners.MusicaListener;
import com.example.utils.MusicaJSONParser;

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
    public int idMusica;
    private ArrayList<Musica> musicas;
    private static BeatBunnySingleton instance = null;
    private BeatBunnyBDHelper beatBunnyBD = null;
    private MusicaListener musicaListener;

    private String tokenAPI = "AMSI-TOKEN";

    private String mUrlAPIusers = "localhost/BeatBunny/advanced/backend/web/v1/user";
    private String mUrlAPIMusicas = "localhost/BeatBunny/advanced/backend/web/v1/music";
    private static RequestQueue volleiQueue;
    private MusicaListener MusicaListener;


    public static synchronized BeatBunnySingleton getInstance(Context context) {
        if(instance == null)
            instance = new BeatBunnySingleton(context);
        return instance;
    }

    /*public void loginUserAPI(final String username, final String password, final Context context){
        String request = new StringRequest(Request.Method.POST, mUrlAPIusers, new Response.Listener<String>() {
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
                params.put("username", user.getTitulo());
                params.put("serie", livro.getSerie());
                params.put("autor", livro.getAutor());
                params.put("ano", livro.getAno()+"");
                params.put("capa", livro.getCapa());
                return params;
            }
        };
    }


    private void onUpdateListaUsersBD(User user, int operacao) {
        switch (operacao){
            case ADICIONAR_BD:
                adicionarUserBD(user);
                break;
            case EDITAR_BD:
                editarUserBD(user);
                break;
            case REMOVER_BD:
                removerUserBD(user.getId());
                break;
        }
    }*/



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
        beatBunnyBD = new BeatBunnyBDHelper(context);
    }


    public ArrayList<Musica> getListaMusica() {
        return new ArrayList<>(musicas);
    }



    /*****************************Métodos que acedem à API******************************/
    public void getAllMusicasAPI(final Context context, boolean isConnected){
        Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            musicas = beatBunnyBD.getallMusicasBD();
            if(musicaListener != null)
                musicaListener.onRefreshListaLivros(musicas);
        }
        else{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIMusicas, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    musicas = MusicaJSONParser.parserJsonMusicas(response, context);
                    adicionarMusicasBD(musicas);
                    if(musicaListener!= null)
                        musicaListener.onRefreshListaLivros(musicas);
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

    public void setMusicaListener(MusicaListener livrosListener) {
        this.MusicaListener = livrosListener;
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
