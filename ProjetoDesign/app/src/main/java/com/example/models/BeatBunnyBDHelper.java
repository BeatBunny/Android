package com.example.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BeatBunnyBDHelper extends SQLiteOpenHelper {

    private final SQLiteDatabase database;

    private static final String DB_NAME = "BeatBunnyDB";
    private static final int DB_VERSION = 1;
                    /**Tabelas**/
    private static final String TABLE_USER = "users";
    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_MUSICS = "musics";
                     /**TabelaUser**/
    private static final String USER_ID= "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_AUTH_KEY = "auth_key";
    //private static final String USER_PASSWORD_HASH = "password_hash";
    private static final String USER_EMAIL = "email";
                     /**TabelaProfile**/
    private static final String PROFILE_ID= "profile_id";
    private static final String PROFILE_SALDO = "saldo";
    private static final String PROFILE_NOME = "nome";
    private static final String PROFILE_NIF = "nif";
    private static final String PROFILE_IMAGE = "profileimage";
                     /**TabelaMusics**/
    private static final String MUSIC_ID= "id";
    private static final String MUSIC_TITLE = "title";
    private static final String MUSIC_LAUNCHDATE = "launchdate";
    private static final String MUSIC_LYRICS = "musiclyrics";
    private static final String MUSIC_COVER = "musiccover";
    private static final String MUSIC_PATH = "musicpath";
    private static final String MUSIC_GENRE = "genre";
    private static final String MUSIC_PRODUCER= "producer";
    private static final String MUSIC_PVP = "pvp";
                    /**TabelaPlaylists**/
    private static final String PLAYLISTS_ID= "id";
    private static final String PLAYLISTS_NAME= "name";
    private static final String PLAYLISTS_CREATIONDATE = "creationdate";


    public BeatBunnyBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase database) {

        String createUserTable = "CREATE TABLE "+TABLE_USER+
                "( "+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USER_USERNAME + " TEXT NOT NULL, "+
                USER_AUTH_KEY + " TEXT NOT NULL, "+
                USER_EMAIL + " TEXT NOT NULL,"+
                PROFILE_ID + " INTEGER,"+
                PROFILE_NOME + " TEXT, "+
                PROFILE_SALDO + " TEXT, "+
                PROFILE_NIF + " TEXT, "+
                PROFILE_IMAGE + " TEXT)";
        database.execSQL(createUserTable);

        String createMusicTable = "CREATE TABLE "+TABLE_MUSICS+
                "( "+MUSIC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MUSIC_TITLE + " TEXT NOT NULL, "+
                MUSIC_GENRE + " TEXT NOT NULL, "+
                MUSIC_COVER + " TEXT NOT NULL,"+
                MUSIC_LAUNCHDATE + " DATE NOT NULL, "+
                MUSIC_LYRICS + " TEXT NOT NULL, "+
                MUSIC_PATH + " TEXT NOT NULL,"+
                MUSIC_PRODUCER+ " TEXT NOT NULL,"+
                MUSIC_PVP+ " TEXT NOT NULL)";
        database.execSQL(createMusicTable);

        String createPlaylistTable = "CREATE TABLE "+TABLE_PLAYLISTS+
                "( "+PLAYLISTS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PLAYLISTS_NAME + " TEXT NOT NULL, "+
                PLAYLISTS_CREATIONDATE+ " TEXT NOT NULL) ";
        database.execSQL(createPlaylistTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYLISTS);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_MUSICS);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        this.onCreate(database);
    }




    public Playlist adicionarPlaylistBD(Playlist playlist){
        ContentValues values = new ContentValues();
        values.put(PLAYLISTS_ID, playlist.getId());
        values.put(PLAYLISTS_NAME, playlist.getNome());
        values.put(PLAYLISTS_CREATIONDATE, playlist.getCreationdate());

        long id = this.database.insert(TABLE_PLAYLISTS, null, values);
        if(id > -1){
            playlist.setId( (int) id );
            return playlist;
        }
        return null;
    }


    public ArrayList<Musica> getallMusicasBD(){
        ArrayList<Musica> musicas = new ArrayList<Musica>();
        Cursor cursor = this.database.query(TABLE_MUSICS, new String[]{MUSIC_ID, MUSIC_TITLE, MUSIC_LAUNCHDATE, MUSIC_PATH, MUSIC_GENRE, MUSIC_LYRICS,MUSIC_COVER,MUSIC_PRODUCER, MUSIC_PVP}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Musica auxMusica = new Musica(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7), cursor.getFloat(8));
                //auxMusica.setId(cursor.getInt(0));
                musicas.add(auxMusica);
            }while (cursor.moveToNext());
        }
        return musicas;
    }
    public boolean removerAllMusicasBD() {
        return database.delete(TABLE_MUSICS, null, null) > 0;
    }

    /****************OPERAÇÕES CRUD**************/

      /****************MUSICAS****************/
    //INSERT
    public void adicionarMusicaBD(Musica musica){
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, musica.getId());
        values.put(MUSIC_TITLE, musica.getTitle());
        values.put(MUSIC_COVER, musica.getMusiccover());
        values.put(MUSIC_GENRE, musica.getMusicgenre());
        values.put(MUSIC_LAUNCHDATE, musica.getLaunchdate());
        values.put(MUSIC_LYRICS, musica.getLyrics());
        values.put(MUSIC_PATH, musica.getMusicpath());
        values.put(MUSIC_PRODUCER,musica.getProducer());
        values.put(MUSIC_PVP, musica.getPvp());

        this.database.insert(TABLE_MUSICS, null, values);
    }

    //UPDATE
    public boolean guardarMusicaBD(Musica musica){

        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, musica.getId());
        values.put(MUSIC_TITLE, musica.getTitle());
        values.put(MUSIC_COVER, musica.getMusiccover());
        values.put(MUSIC_GENRE, musica.getMusicgenre());
        values.put(MUSIC_LAUNCHDATE, musica.getLaunchdate());
        values.put(MUSIC_LYRICS, musica.getLyrics());
        values.put(MUSIC_PATH, musica.getMusicpath());
        values.put(MUSIC_PRODUCER,musica.getProducer());
        values.put(MUSIC_PVP, musica.getPvp());

        return this.database.update(TABLE_MUSICS, values, "id = ?", new String[]{musica.getId()+""}) > 0;
    }
    //DELETE
    public boolean removerMusicaBD(int idMusica){
        return database.delete(TABLE_MUSICS, "id =?", new String[]{idMusica+""}) > 0;
    }




    /****************USERS****************/


    public User adicionarUserBD(User user){
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_AUTH_KEY, user.getAuthKey());
        values.put(USER_EMAIL, user.getEmail());

        long id = this.database.insert(TABLE_USER, null, values);
        if(id > -1){
            user.setId( (int) id );
            return user;
        }
        return null;
    }

    public boolean adicionarProfileBD(Profile profile, int user_id){

        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, profile.getProfileId());
        values.put(PROFILE_NOME, profile.getNome());
        values.put(PROFILE_SALDO, profile.getSaldo());
        values.put(PROFILE_NIF, profile.getNif());
        values.put(PROFILE_IMAGE, profile.getProfileimage());

        return this.database.update(TABLE_USER, values, "id = ?", new String[]{user_id+""}) > 0;

    }

    public boolean guardarUserBD(User user) {

        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_AUTH_KEY, user.getAuthKey());

        return this.database.update(TABLE_USER, values, "id = ?", new String[]{user.getId()+""}) > 0;
    }

    public ArrayList<User> getAllUsersBD(){
        ArrayList<User> users = new ArrayList<User>();
        Cursor cursor = this.database.query(TABLE_USER, new String[]{USER_ID, USER_USERNAME, USER_AUTH_KEY, USER_EMAIL}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                User auxUser = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                auxUser.setId(cursor.getInt(0));
                users.add(auxUser);
            }while (cursor.moveToNext());
        }
        return users;
    }

    public boolean removerAllUsersBD() {
        return database.delete(TABLE_USER, null, null) > 0;
    }
}

