package com.example.models;

public class Playlist {
    private static int Id ;
    private String Nome;
    private int Music_id ;
    private int Profile_id;

    public Playlist(int id, String nome, int music_id, int profile_id) {
        Id=id;
        Nome=nome;
        Music_id=music_id;
        Profile_id=profile_id;
    }

    public void setId(int id){Id = id;}
    public void setNome (String nome) {
        Nome = nome;
    }
    public void setMusic_id(int music_id ) { Music_id=music_id;
    }
    public void setProfile_id(int profile_id){Profile_id=profile_id;}

    public int getId(){return Id;}
    public String getNome(){return Nome;}
    public int getMusic_id(){return Music_id;}
    public int getProfile_id(){return Profile_id;}
}
