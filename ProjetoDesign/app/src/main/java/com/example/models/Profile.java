package com.example.models;

import android.net.Network;

public class Profile {
    private static int Id ;
    private int Saldo;
    private String Nome;
    private int Nif ;
    private String Profileimage;
    private int User_id;


    public Profile(int id, int saldo, String nome, int nif, String profileimage, int user_id) {
        Id=id;
        Saldo=saldo;
        Nome=nome;
        Nif=nif;
        Profileimage=profileimage;
        User_id=user_id;
    }

    public void setId(int id){Id = id;}
    public void setSaldo(int saldo) {
        Saldo = saldo;
    }
    public void setNome(String nome ) { Nome=nome;
    }
    public void setNif(int nif){Nif=nif;}
    public void setProfileimage(String profileimage){Profileimage=profileimage;}
    public void setUser_id(int user_id){User_id=user_id;}

    public int getId(){return Id;}
    public int getSaldo(){return Saldo;}
    public String getNome(){return Nome;}
    public int getNif(){return Nif;}
    public String getProfileimage(){return Profileimage;}
    public int getUser_id(){return User_id;}
}
