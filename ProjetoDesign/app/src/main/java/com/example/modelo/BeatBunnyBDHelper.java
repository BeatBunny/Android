package com.example.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    private static final String PROFILE_ID= "id";
    private static final String PROFILE_SALDO = "saldo";
    private static final String PROFILE_NOME = "nome";
    private static final String PROFILE_NIF = "nif";
    private static final String PROFILE_IMAGE = "profileimage";
    private static final String PROFILE_USER_ID= "user_id";
                     /**TabelaMusics**/
    private static final String MUSIC_ID= "id";
    private static final String MUSIC_TITLE = "title";
    private static final String MUSIC_LAUNCHDATE = "launchdate";
    private static final String MUSIC_RATING = "rating";
    private static final String MUSIC_LYRICS = "musiclyrics";
    private static final String MUSIC_COVER = "musiccover";
    private static final String MUSIC_PATH = "musicpath";
    private static final String MUSIC_GENRE = "genre";
    private static final String MUSIC_PROFILE_ID= "profile_id";
                    /**TabelaPlaylists**/
    private static final String PLAYLISTS_ID= "id";
    private static final String PLAYLISTS_NAME= "name";
    private static final String PLAYLIST_MUSIC_ID= "music_id";
    private static final String PLAYLIST_PROFILE_ID= "profile_id";


    public BeatBunnyBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTable = "CREATE TABLE "+TABLE_USER+
                "( "+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USER_USERNAME + " TEXT NOT NULL, "+
                USER_AUTH_KEY + " TEXT NOT NULL, "+
               // USER_PASSWORD_HASH + " TEXT NOT NULL, "+
                USER_EMAIL + " TEXT NOT NULL)";
        db.execSQL(createUserTable);

        String createProfileTable = "CREATE TABLE "+TABLE_PROFILE+
                "( "+PROFILE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PROFILE_NOME + " TEXT NOT NULL, "+
                PROFILE_SALDO + " TEXT NOT NULL, "+
                PROFILE_NIF + " TEXT NOT NULL, "+
                PROFILE_IMAGE + " TEXT NOT NULL,"+
                PROFILE_USER_ID+ "INTEGER, "+
                "FOREIGN KEY ("+PROFILE_USER_ID+") REFERENCES "+TABLE_USER+"("+USER_ID+"))";
        db.execSQL(createProfileTable);

        String createMusicTable = "CREATE TABLE "+TABLE_MUSICS+
                "( "+MUSIC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MUSIC_TITLE + " TEXT NOT NULL, "+
                MUSIC_RATING + " INTEGER NOT NULL, "+
                MUSIC_GENRE + " TEXT NOT NULL, "+
                MUSIC_COVER + " TEXT NOT NULL,"+
                MUSIC_LAUNCHDATE + " DATE NOT NULL, "+
                MUSIC_LYRICS + " TEXT NOT NULL, "+
                MUSIC_PATH + " TEXT NOT NULL,"+
                MUSIC_PROFILE_ID+ "INTEGER, "+
                "FOREIGN KEY ("+MUSIC_PROFILE_ID+") REFERENCES "+TABLE_PROFILE+"("+PROFILE_ID+"))";
        db.execSQL(createMusicTable);

        String createPlaylistTable = "CREATE TABLE "+TABLE_PLAYLISTS+
                "( "+PLAYLISTS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PLAYLISTS_NAME + " TEXT NOT NULL, "+
                PLAYLIST_MUSIC_ID+ "INTEGER, "+
                PLAYLIST_PROFILE_ID+ "INTEGER,"+
                "FOREIGN KEY ("+PLAYLIST_MUSIC_ID+") REFERENCES "+TABLE_MUSICS+"("+MUSIC_ID+"),"+
                "FOREIGN KEY ("+PLAYLIST_PROFILE_ID+") REFERENCES "+TABLE_PROFILE+"("+PROFILE_ID+"))";
        db.execSQL(createPlaylistTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYLISTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MUSICS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        this.onCreate(db);
    }
    public Musica adicionarMusicaBD(Musica musica){
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, musica.getiD());
        values.put(MUSIC_TITLE, musica.getTitle());
        values.put(MUSIC_COVER, musica.getMusiccover());
        values.put(MUSIC_GENRE, musica.getMusicgenre());
        values.put(MUSIC_LAUNCHDATE, musica.getLaunchedate());
        values.put(MUSIC_RATING, musica.getRating());
        values.put(MUSIC_LYRICS, musica.getLyrics());
        values.put(MUSIC_PATH, musica.getMusicpth());
        values.put(MUSIC_PROFILE_ID,musica.getProfile_id());

        long id = this.database.insert(TABLE_MUSICS, null, values);
        if(id > -1){
            musica.setiD( (int) id );
            return musica;
        }
        return null;
    }

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

    public Profile adicionarProfileBD(Profile profile){
        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, profile.getId());
        values.put(PROFILE_NOME, profile.getNome());
        values.put(PROFILE_SALDO, profile.getSaldo());
        values.put(PROFILE_NIF, profile.getNif());
        values.put(PROFILE_IMAGE, profile.getProfileimage());
        values.put(PROFILE_USER_ID, profile.getUser_id());

        long id = this.database.insert(TABLE_PROFILE, null, values);
        if(id > -1){
            profile.setId( (int) id );
            return profile;
        }
        return null;
    }

    public Playlist adicionarPlaylistBD(Playlist playlist){
        ContentValues values = new ContentValues();
        values.put(PLAYLISTS_ID, playlist.getId());
        values.put(PLAYLISTS_NAME, playlist.getNome());
        values.put(PLAYLIST_MUSIC_ID, playlist.getMusic_id());
        values.put(PLAYLIST_PROFILE_ID, playlist.getProfile_id());

        long id = this.database.insert(TABLE_PROFILE, null, values);
        if(id > -1){
            playlist.setId( (int) id );
            return playlist;
        }
        return null;
    }


    public ArrayList<Musica> getallMusicsBD(){
        ArrayList<Musica> musicas = new ArrayList<Musica>();
        Cursor cursor = this.database.query(TABLE_MUSICS, new String[]{MUSIC_ID, MUSIC_TITLE, MUSIC_LAUNCHDATE, MUSIC_PATH, MUSIC_GENRE, MUSIC_LYRICS,MUSIC_RATING,MUSIC_COVER,MUSIC_PROFILE_ID}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Musica auxMusica = new Musica(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6),cursor.getInt(7),cursor.getInt(8));
                auxMusica.setiD(cursor.getInt(0));
                musicas.add(auxMusica);
            }while (cursor.moveToNext());
        }
        return musicas;
    }

}

