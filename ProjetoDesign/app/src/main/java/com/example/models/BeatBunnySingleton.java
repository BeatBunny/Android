package com.example.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.listeners.MusicaListener;
import com.example.utils.MusicaJSONParser;

import org.json.JSONArray;

import java.util.ArrayList;

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

    private String mUrlAPIusers = "localhost/BeatBunny/advanced/backend/web/v1/user";
    private String mUrlAPIMusicas = "localhost/BeatBunny/advanced/backend/web/v1/music";
    private static RequestQueue volleiQueue;


    public static synchronized BeatBunnySingleton getInstance(Context context) {
        if(instance == null)
            instance = new BeatBunnySingleton();
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



    private BeatBunnySingleton() {

    }


    public ArrayList<Musica> getListaMusica() {
        return new ArrayList<>(musicas);
    }
    public void AdicionarMusica(Musica addmusica){
        musicas.add(addmusica);
    }

    public void Remover_musica(int Musica){
        Musica auxMusica= getMusica(idMusica);
        if (auxMusica!=null){
            musicas.remove(auxMusica);
        }

    }
    public void Adicionar_Musica(Musica musica)
    {
        musicas.add(musica);
    }

//    public void Editar_livro(Musica musica){
//        Musica auxMusica= getMusica(musica.getiD());
//        musica.setLaunchedate(auxMusica.getLaunchedate());
//        musica.setMusiccover(auxMusica.getMusiccover());
//        musica.setMusicpth(auxMusica.getMusicpth());
//        musica.setTitle(auxMusica.getTitle());
//        musica.setRating(auxMusica.getRating());
//    }

    public void getAllLivrosAPI(final Context context, boolean isConnected){
        Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            musicas = beatBunnyBD.getAllMusicasBD();
            if(musicaListener != null)
                musicaListener.onRefreshListaLivros(musicas);
        }
        else{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIMusicas, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    musicas = MusicaJSONParser.parserJsonLivros(response, context);
                    adicionarLivrosBD(livros);
                    if(livrosListener!= null)
                        livrosListener.onRefreshListaLivros(livros);
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
            case REMOVER_BD:
                removerUserBD(user.getId());
                break;
        }
    }




}
