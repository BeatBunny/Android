package com.example.listeners;

import android.app.Dialog;
import android.os.Bundle;

import com.example.models.User;

public interface UserListener {
    void onRefreshListaUser(User user);

    Dialog onCreateDialog(Bundle savedInstanceState);
}
