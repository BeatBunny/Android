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
        ListaMusicasDestaPlaylist = null;
    }

    public void setId(int id){this.Id = id;}
    public void setNome(String nome){this.Nome = nome;}
    public void setCreationdate(String creationdate){this.Creationdate = creationdate;}
    public void setListaMusicasDestaPlaylist(ArrayList<Musica> listaMusicasDestaPlaylist){this.ListaMusicasDestaPlaylist = listaMusicasDestaPlaylist;}

    public int getId(){return this.Id;}
    public String getNome(){return this.Nome;}
    public String getCreationdate(){return this.Creationdate;}
    public ArrayList<Musica> getListaMusicasDestaPlaylist(){return this.ListaMusicasDestaPlaylist;}

    @Override
    public String toString() {
        return Nome;
    }
}
