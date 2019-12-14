package com.example.modelo;

import java.util.ArrayList;

public class User {

    private static int Id ;
    private String Username;
    private String AuthKey;
    private String Email;



    public User(int id, String username, String authKey, String email) {
    Id=id;
    Username=username;
    AuthKey=authKey;
    Email=email;
    }

    public void setId(int id){Id = id;}
    public void setUsername(String username) {
        Username = username;
    }
    public void setAuthKey(String authKey ) { AuthKey=authKey;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public int getId(){return Id;}
    public String getUsername() {
        return Username;
    }
    public String getAuthKey() {
        return AuthKey;
    }
    public String getEmail() {
        return Email;
    }

}
