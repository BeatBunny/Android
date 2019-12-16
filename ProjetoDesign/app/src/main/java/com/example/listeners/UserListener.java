package com.example.listeners;

import com.example.modelo.User;

import java.util.ArrayList;

public interface UserListener {
    void onRefreshListaUser(ArrayList<User> users);
    void onUpdateListaUser(User user, int operacao);
}
