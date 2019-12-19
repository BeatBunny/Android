package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.adapters.GrelhaMusicaAdapter;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.User;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;
import com.example.utils.UserJSONParser;
import com.google.android.material.internal.BaselineLayout;

import java.util.ArrayList;

public class Settings extends Fragment {

    private EditText Nome, Email, Nif;
    private ImageView ProfilePicture;
    private TextView User_nome,User_balance;
    private Button Change_Settings,Submit;
    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);
        Nome =view.findViewById(R.id.ChangeName);
        Email = view.findViewById(R.id.ChangeEmail);
        Nif = view.findViewById(R.id.ChangeNif);
        ProfilePicture = view.findViewById(R.id.ProfilePicture);
        User_nome= view.findViewById(R.id.TextViewNome);
        User_balance =view.findViewById(R.id.TextViewBalances);
        Change_Settings = view.findViewById(R.id.ChangeSettings);
        Submit = view.findViewById(R.id.Submit);
        //grelhaMusicas.setAdapter(new GrelhaMusicaAdaptor(getContext(), listamusicas));

        return view;
    }


    public void onClickChangeSettings(View view){

    }
}
