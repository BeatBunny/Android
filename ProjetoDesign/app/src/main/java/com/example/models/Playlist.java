package com.example.models;

import java.util.ArrayList;

public class Playlist {
    private int Id;
    private String Creationdate;
    private String Nome;
    private ArrayList<Musica> ListaMusicasDestaPlaylist;

    public Playlist(int id, String nome, String creationdate) {
        Id=id;
        Nome=nome;
        Creationdate = creationdate;
    }

    public void setId(int id){Id = id;}
    public void setNome(String nome){Nome = nome;}
    public void setCreationdate(String creationdate){Creationdate = creationdate;}
    public void setListaMusicasDestaPlaylist(ArrayList<Musica> listaMusicasDestaPlaylist){ListaMusicasDestaPlaylist = listaMusicasDestaPlaylist;}

    public int getId(){return Id;}
    public String getNome(){return Nome;}
    public String getCreationdate(){return Creationdate;}
    public ArrayList<Musica> getListaMusicasDestaPlaylist(){return ListaMusicasDestaPlaylist;}
}
