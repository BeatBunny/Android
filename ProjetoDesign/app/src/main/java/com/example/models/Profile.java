package com.example.models;

public class Profile {
    private int Profile_ID ;
    private String Saldo;
    private String Nome;
    private int Nif ;
    private String Profileimage;


    public Profile(int profile_ID, String saldo, String nome, int nif) {
        Profile_ID=profile_ID;
        Saldo=saldo;
        Nome=nome;
        Nif=nif;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }
    public void setNome(String nome ) {
        Nome=nome;
    }
    public void setNif(int nif){
        Nif=nif;
    }

    public String getSaldo(){
        return Saldo;
    }
    public String getNome(){
        return Nome;
    }
    public String getNif(){
        return Nif+"" ;
    }
}
