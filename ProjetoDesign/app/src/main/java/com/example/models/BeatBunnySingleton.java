
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
import com.example.listeners.PlaylistListener;
import com.example.listeners.UserListener;
import com.example.utils.MusicaJSONParser;
import com.example.utils.PlaylistJSONParser;
import com.example.utils.ProfileJSONParser;
import com.example.utils.UserJSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

;

//import com.example.listeners.LivrosListener;
//import com.example.utils.LivroJsonParser;

public class BeatBunnySingleton {

    private ArrayList<Musica> musicas;
    private ArrayList<Musica> yourMusics;
    private ArrayList<Musica> listaMusicasProduzidas;
    private ArrayList<Playlist> playlists;
    private ArrayList<User> users;
    private static BeatBunnySingleton INSTANCE = null;
    private BeatBunnyBDHelper beatBunnyBD = null;
    private MusicaListener musicaListener;
    private PlaylistListener playlistListener;

    private UserListener userListener;

    private String CURRENT_IP;

    private String mUrlAPIusersLogin = "http://localhost/BeatBunny/advanced/backend/web/v1/userregisterandlogin/login";
    private String mUrlAPIusersRegister = "http://localhost/BeatBunny/advanced/backend/web/v1/userregisterandlogin/register";
    private String mUrlAPIMusicas = "http://localhost/BeatBunny/advanced/backend/web/v1/music";
    private String mUrlAPIPutMusicInPlaylist = "http://localhost/BeatBunny/advanced/backend/web/v1/playlists/playlistcreate";
    private String mUrlGetStuffFromPlaylists = "http://localhost/BeatBunny/advanced/backend/web/v1/playlists/";
    private String mUrlGetStuffFromUser;

    private static RequestQueue volleiQueue;

    private boolean tof;


    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }
    public void setPlaylistListener(PlaylistListener playlistListener){
        this.playlistListener = playlistListener;
    }

    public static synchronized BeatBunnySingleton getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new BeatBunnySingleton(context);
            volleiQueue = Volley.newRequestQueue(context);
        }
        return INSTANCE;
    }




    public void loginUserAPI(final String username, final String password, final Context context, final boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIusersLogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    User user = UserJSONParser.parserJsonUser(response, context);
                    userListener.onRefreshListaUser(user);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }

    public void getProfileFromLogin(final Context context, final boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.GET, mUrlGetStuffFromUser+SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0)+"/profile?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Profile profile = ProfileJSONParser.parserJsonProfile(response, context);
                    userListener.onRefreshListaProfiles(profile);
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

    public void saveSettings(final String nome, final String nif, final Context context, final boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.PUT, mUrlGetStuffFromUser+"savesettings?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Profile profile = ProfileJSONParser.parserJsonProfile(response, context);
                    userListener.onRefreshListaProfiles(profile);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idUser", SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0).toString());
                    params.put("nomeNovo", nome);
                    params.put("nifNovo", nif);
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }


    public void registoUserAPI(final String username, final String password, final String email, final String nome, final String nif, final Context context, boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIusersRegister, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("------------------------->"+response);
                    try {
                        JSONObject responseContent = new JSONObject(response);
                        String resposta = responseContent.getString("SaveError");
                        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
                        if(resposta.contentEquals("Registered User")){
                            User user = UserJSONParser.parserJsonUser(response, context);
                            userListener.onRefreshListaUser(user);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*User user = UserJSONParser.parserJsonUser(response, context);
                    userListener.onRefreshListaUser(user);*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
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

    public void setIP(String ip) {
        CURRENT_IP = ip;
        SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.SETTINGS_IP, CURRENT_IP);//save boolean in shared preference.
        mUrlAPIusersLogin = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/login";
        mUrlAPIusersRegister = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/register";
        mUrlAPIMusicas = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/music";
        mUrlGetStuffFromUser = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/user/";
        mUrlAPIPutMusicInPlaylist = "http://"+ CURRENT_IP +":80/BeatBunny/advanced/backend/web/v1/playlists/playlistcreate";
        mUrlGetStuffFromPlaylists = "http://"+ CURRENT_IP +":80/BeatBunny/advanced/backend/web/v1/playlists/";
    }

    public String getIPInput() {
        return CURRENT_IP;
    }

    public User getUser(int idUser) {
        for (User u : users)
            if (u.getId() == idUser)
                return u;
        return null;
    }


    //COM FOREACH
    public Musica getMusica(int idMusica) {
        for (Musica M : musicas) {
            if (M.getId() == idMusica) {
                return M;
            }
        }
        return null;
    }


    private BeatBunnySingleton(Context context) {
        musicas = new ArrayList<Musica>();
        users = new ArrayList<User>();
        playlists = new ArrayList<Playlist>();
        beatBunnyBD = new BeatBunnyBDHelper(context);
        CURRENT_IP = SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.SETTINGS_IP, null);//read string in shared preference.
        mUrlAPIusersLogin = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/login";
        mUrlAPIusersRegister = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/userregisterandlogin/register";
        mUrlAPIMusicas = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/music";
        mUrlGetStuffFromUser = "http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/user/";
        mUrlAPIPutMusicInPlaylist = "http://"+ CURRENT_IP +":80/BeatBunny/advanced/backend/web/v1/playlists/playlistcreate";
        mUrlGetStuffFromPlaylists = "http://"+ CURRENT_IP +":80/BeatBunny/advanced/backend/web/v1/playlists/";
    }


    public ArrayList<Musica> getListaMusicas() {
        musicas = beatBunnyBD.getallMusicasBD();
        return musicas;
    }

    public ArrayList<Musica> getAllMusics(){
        return this.musicas;
    }


    /*****************************Métodos que acedem à API******************************/
    public void getAllMusicasAPI(final Context context, final boolean isConnected) {
        //Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            /*musicas = beatBunnyBD.getallMusicasBD();
            if (musicaListener != null)
                musicaListener.onRefreshListaMusica(musicas);*/
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIMusicas, null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    musicas = MusicaJSONParser.parserJsonMusicas(response, context);
                    adicionarMusicasBD(musicas);
                    if (musicaListener != null)
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


    public void adicionarMusicasBD(ArrayList<Musica> listaMusica) {
        beatBunnyBD.removerAllMusicasBD();
        for (Musica musica : listaMusica)
            adicionarMusicaBD(musica);

    }

    public void adicionarMusicaBD(Musica musica) {
        beatBunnyBD.adicionarMusicaBD(musica);
    }

    public void editarMusicaBD(Musica musica) {
        Musica auxMusica = getMusica(musica.getId());
        if (auxMusica != null)
            beatBunnyBD.guardarMusicaBD(musica);
    }

    public void removerMusicaBD(int idMusica) {
        Musica auxMusica = getMusica(idMusica);
        if (auxMusica != null)
            beatBunnyBD.removerMusicaBD(idMusica);
    }


    public void getMusicFromProfileHasClientAPI(final Context context, final boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlGetStuffFromUser + SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0) + "/getmusicfromprofilehasclient?access-token=" + SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    yourMusics = MusicaJSONParser.parserJsonMusicas(response, context);
                    /*if (musicaListener != null)
                        musicaListener.onRefreshListaMusica(yourMusics);*/

                    getMusicFromProfileHasProducerAPI(context, isConnected);
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

    public void getMusicFromProfileHasProducerAPI(final Context context, final boolean isConnected){
        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlGetStuffFromUser + SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0) + "/getmusicfromprofilehasproducer?access-token=" + SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    listaMusicasProduzidas = MusicaJSONParser.parserJsonMusicas(response, context);
                    yourMusics.addAll(listaMusicasProduzidas);
                    /*if (musicaListener != null)
                        musicaListener.onRefreshListaMusica(yourMusics);*/
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

    public ArrayList<Musica> getYourStuffAndUpdateList(final Context context, final boolean isConnected){
        getMusicFromProfileHasClientAPI(context, isConnected);
        if (musicaListener != null)
            musicaListener.onRefreshListaMusica(yourMusics);
        return yourMusics;
    }

    public ArrayList<Musica> getYourStuffAndDontUpdateList(final Context context, final boolean isConnected){
        getMusicFromProfileHasClientAPI(context, isConnected);
        ArrayList<Musica> yourStuff = this.yourMusics;
        return yourStuff;
    }

    public Boolean checkIfYouOwnMusic(int idMusic){
        for (Musica m: yourMusics) {
            if(m.getId() == idMusic)
                return true;
        }
        return false;
    }


    public ArrayList<Playlist> getPlaylists(final Context context, final boolean isConnected) {
        getAllPlaylistsFromUserAPI(context, isConnected);
        return this.playlists;
    }

    public void getAllPlaylistsFromUserAPI(final Context context, final boolean isConnected) {
        System.out.println("--------------------> IS CONNECTED\n"+isConnected);
        //Toast.makeText(context, "isConnected:" + isConnected, Toast.LENGTH_SHORT).show();
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            playlists = beatBunnyBD.getAllPlaylists();
            if (playlistListener != null)
                playlistListener.onRefreshListaPlaylist(playlists);
            for (Playlist playlist: playlists) {
                System.out.println("--------------------> PLAYLISTS FROM USER\n");
                System.out.println(playlist.getId());
                System.out.println(playlist.getNome());
                System.out.println(playlist.getCreationdate());
            }
        } else {                                                                 //"http://" + CURRENT_IP + ":80/BeatBunny/advanced/backend/web/v1/user/"
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlGetStuffFromUser +SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER,0)+"/playlists?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    playlists = PlaylistJSONParser.parserJsonPlaylists(response, context);
                    adicionarPlaylistsBD(playlists);

                    System.out.println("--------------------> PLAYLISTS FROM USER\n\n\n\n"+playlists);
                    getAllMusicsFromEachPlaylist(context, isConnected, playlists);
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

    private void adicionarPlaylistsBD(ArrayList<Playlist> playlists) {
        beatBunnyBD.removerAllPlaylistsBD();
        for (Playlist playlist : playlists)
            beatBunnyBD.adicionarPlaylistBD(playlist);
    }

    public void getAllMusicsFromEachPlaylist(final Context context, boolean isConnected, ArrayList<Playlist> playlists){

        if(!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            playlists = beatBunnyBD.getAllPlaylists();
            if (playlistListener != null)
                playlistListener.onRefreshListaPlaylist(playlists);
        }
        else {

                for (final Playlist pl : playlists) {

                    final ArrayList<Playlist> finalPlaylists = playlists;
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlGetStuffFromUser +SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER,0)+"/playlists/"+pl.getId()+"/musics?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), null
                            , new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            pl.setListaMusicasDestaPlaylist(MusicaJSONParser.parserJsonMusicas(response, context));
                            if (playlistListener != null)
                                playlistListener.onRefreshListaPlaylist(finalPlaylists);
                            System.out.println("THIS IS PL ------------------- >>>> "+pl.getListaMusicasDestaPlaylist());
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
                if(playlists.size() == 0)
                    if (playlistListener != null)
                        playlistListener.onRefreshListaPlaylist(playlists);
            System.out.println("--------------------> PLAYLISTS FROM USER\n"+playlists);
            /*if (playlistListener != null)
                playlistListener.onRefreshListaPlaylist(playlists);*/
        }
    }

    public void createNewPlaylist(final Context context, final boolean isConnected, final String nomePlaylist){
        if (!isConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIPutMusicInPlaylist+"?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getAllPlaylistsFromUserAPI(context, isConnected);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nome", nomePlaylist);
                    params.put("idUser", SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0).toString());
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }

    public void deletePlayistAPI(final Context context, final boolean isConnected, final int idPlaylist){
        if (!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.DELETE, mUrlGetStuffFromPlaylists+"playlistdelete/"+idPlaylist+"?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject responseContent = new JSONObject(response);
                        boolean resposta = responseContent.getBoolean("SaveError");
                        if(resposta)
                            Toast.makeText(context, "Playlist Deleted Successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Playlist Deleted Unsuccessfully", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getAllPlaylistsFromUserAPI(context, isConnected);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleiQueue.add(request);
        }
    }

    public void putSongInPlaylist(final Context context, final boolean isConnected, final int idMusica , final int idPlaylist){
        if (!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlGetStuffFromPlaylists+"putsong?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject responseContent = new JSONObject(response);
                        boolean resposta = responseContent.getBoolean("SaveError");
                        if(resposta)
                            Toast.makeText(context, "Music added to chosen Playlist", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Error adding Music to Playlist", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idMusic", String.valueOf(idMusica));
                    params.put("idPlaylist", String.valueOf(idPlaylist));
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }

    public void removeSongFromPlaylist(final Context context, final boolean isConnected,final int idPlaylist, final int idMusica ){
        if (!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlGetStuffFromPlaylists+"playlistremovesong?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject responseContent = new JSONObject(response);
                        boolean resposta = responseContent.getBoolean("SaveError");
                        if(resposta)
                            Toast.makeText(context, "Music removed from Playlist", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Error removing Music from Playlist", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getAllPlaylistsFromUserAPI(context, isConnected);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idMusic", String.valueOf(idMusica));
                    params.put("idPlaylist", String.valueOf(idPlaylist));
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }



    public void buySongAPI(final Context context, final boolean isConnected, final int idMusica) {
        tof = false;
        if (!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, mUrlGetStuffFromUser+"buysong?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("------------------------->"+response);
                    try {
                        JSONObject responseContent = new JSONObject(response);
                        String resposta = responseContent.getString("SaveError");
                        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
                        if(resposta == "Music Bought"){
                            tof = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getMusicFromProfileHasClientAPI(context, isConnected);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idMusicaParaComprar", String.valueOf(idMusica));
                    params.put("idUser", SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0).toString());
                    return params;
                }
            };
            volleiQueue.add(request);
        }
    }

    public void getSaldoAfterPurchase(final Context context, final boolean isConnected){
        if (!isConnected){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.GET, mUrlGetStuffFromUser +SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER,0)+"/saldoprofile?access-token="+SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null)
            , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String saldoDepoisDaCompra = ProfileJSONParser.parserJsonSaldo(response, context);
                    SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.SALDO_PROFILE, saldoDepoisDaCompra);
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


}