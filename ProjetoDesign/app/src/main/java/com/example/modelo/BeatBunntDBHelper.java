package com.example.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BeatBunntDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "beatbunnyproject";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME_MUSICS = "musics";
    private static final String ID_MUSIC = "id";
    private static final String TITLE_MUSIC = "title";
    private static final String LAUNCHDATE_MUSIC = "launchdate";
    private static final String PVP_MUSIC = "pvp";
    private static final String MUSICCOVER_MUSIC = "musiccover";
    private static final String MUSICPATH_MUSIC = "musicpath";

    private final SQLiteDatabase database;

    public BeatBunntDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMusicTable = "CREATE TABLE "+TABLE_NAME_MUSICS+
                "( "+ID_MUSIC+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TITLE_MUSIC + " TEXT NOT NULL, "+
                PVP_MUSIC + " INTEGER NOT NULL, "+
                MUSICCOVER_MUSIC + " TEXT NOT NULL, "+
                MUSICPATH_MUSIC + " TEXT NOT NULL) ";
        db.execSQL(createMusicTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_MUSICS);
        this.onCreate(db);
    }


    /****************OPERAÇÕES CRUD**************/

    //INSERT
    public Musica adicionarLivroBD(Musica musica){
        ContentValues values = new ContentValues();
        values.put(TITLE_MUSIC, musica.getTitle());
        values.put(LAUNCHDATE_MUSIC, musica.getLaunchedate());
        values.put(PVP_MUSIC, musica.getPvp());
        values.put(MUSICCOVER_MUSIC, musica.getMusiccover());
        values.put(MUSICPATH_MUSIC, musica.getMusicpth());

        long id = this.database.insert(TABLE_NAME_MUSICS, null, values);

        if(id > -1){
            musica.setId( (int) id );
            return musica;
        }

        return null;
    }

    //UPDATE
    /*public boolean guardarLivroBD(Livro livro){


        ContentValues values = new ContentValues();
        values.put(TITULO_LIVRO, livro.getTitulo());
        values.put(SERIE_LIVRO, livro.getSerie());
        values.put(AUTOR_LIVRO, livro.getAutor());
        values.put(ANO_LIVRO, livro.getAno());
        values.put(CAPA_LIVRO, livro.getCapa());

        return this.database.update(TABLE_NAME, values, "id = ?", new String[]{livro.getId()+""}) > 0;
    }*/


    //DELETE
    /*public boolean removerLivroBD(int idLivro){
        return database.delete(TABLE_NAME, "id =?", new String[]{idLivro+""}) > 0;
    }*/

    //GET ALL LIVROS
    public ArrayList<Musica> getAllMusicsBD(){
        ArrayList<Musica> musicas = new ArrayList<Musica>();
        Cursor cursor = this.database.query(TABLE_NAME_MUSICS, new String[]{ID_MUSIC, TITLE_MUSIC, LAUNCHDATE_MUSIC, PVP_MUSIC, MUSICCOVER_MUSIC, MUSICPATH_MUSIC}, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                Musica auxMusica = new Musica(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
                auxMusica.setId(cursor.getInt(0));
                musicas.add(auxMusica);
            }while (cursor.moveToNext());
        }
        return musicas;

    }




}
