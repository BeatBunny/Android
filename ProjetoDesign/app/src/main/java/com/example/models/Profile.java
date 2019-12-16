package com.example.models;

public class Profile {
    private static int Profile_ID ;
    private int Saldo;
    private String Nome;
    private int Nif ;
    private String Profileimage;


    public Profile(int profile_ID, int saldo, String nome, int nif, String profileimage) {
        Profile_ID=profile_ID;
        Saldo=saldo;
        Nome=nome;
        Nif=nif;
        Profileimage=profileimage;
    }

    public void setProfileId(int profile_ID){Profile_ID = profile_ID;}
    public void setSaldo(int saldo) {
        Saldo = saldo;
    }
    public void setNome(String nome ) { Nome=nome;
    }
    public void setNif(int nif){Nif=nif;}
    public void setProfileimage(String profileimage){Profileimage=profileimage;}

    public int getProfileId(){return Profile_ID;}
    public int getSaldo(){return Saldo;}
    public String getNome(){return Nome;}
    public int getNif(){return Nif;}
    public String getProfileimage(){return Profileimage;}
}
