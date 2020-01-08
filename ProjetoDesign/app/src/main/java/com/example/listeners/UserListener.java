package com.example.listeners;

import android.app.Dialog;
import android.os.Bundle;

import com.example.models.Profile;
import com.example.models.User;

public interface UserListener {
    void onRefreshListaUser(User user);
    void onRefreshListaProfiles(Profile profile);

    Dialog onCreateDialog(Bundle savedInstanceState);
}
