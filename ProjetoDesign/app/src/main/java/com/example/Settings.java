package com.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adapters.GrelhaMusicaAdapter;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.User;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;
import com.example.utils.UserJSONParser;
import com.google.android.material.internal.BaselineLayout;

import java.util.ArrayList;
import java.util.Objects;

public class Settings extends Fragment {

    private String username,saldo;
    private int id;
    private String currentIP;
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
        currentIP = BeatBunnySingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext()).getIPInput();
        Nome =view.findViewById(R.id.ChangeName);
        Email = view.findViewById(R.id.ChangeEmail);
        Nif = view.findViewById(R.id.ChangeNif);
        ProfilePicture = view.findViewById(R.id.ProfilePicture);
        User_nome= view.findViewById(R.id.TextViewNome);
        User_balance =view.findViewById(R.id.TextViewBalances);
        Change_Settings = view.findViewById(R.id.ChangeSettings);
        Submit = view.findViewById(R.id.Submit);
        GetUserData();

        Change_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Settings.setVisibility(View.GONE);
                Submit.setVisibility(View.VISIBLE);
                Nome.setFocusableInTouchMode(true);
                Email.setFocusableInTouchMode(true);
                Nif.setFocusableInTouchMode(true);
                Nome.setEnabled(true);
                Email.setEnabled(true);
                Nif.setEnabled(true);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            private String novo_nome, novo_email;
            private int novo_nif;
            @Override
            public void onClick(View v) {
                novo_nome=Nome.getText().toString();
                novo_email=Nome.getText().toString();
                novo_nif= Integer.parseInt(Nif.getText().toString());
            }
        });
        //grelhaMusicas.setAdapter(new GrelhaMusicaAdaptor(getContext(), listamusicas));

        return view;
    }

    private void GetUserData(){
        Nome.setFocusable(false);
        Nome.setEnabled(false);
        Email.setFocusable(false);
        Email.setEnabled(false);
        Nif.setFocusable(false);
        Nif.setEnabled(false);
        Submit.setVisibility(View.GONE);
        id = Objects.requireNonNull(getActivity()).getIntent().getIntExtra("IDUSER", 0);
        User_nome.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("USERNAME"));
        User_nome.setTextSize(25);
        User_nome.setTextColor(Color.parseColor("#80CBC4"));
        User_balance.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("SALDO"));
        Nome.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("USERNAME"));
        Email.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("EMAIL"));
        Nif.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("NIF"));
        Glide.with(getActivity().getApplicationContext())
                .load("http://"+currentIP+":80/BeatBunny/advanced/frontend/web/uploads/"+id+"/profileimage_"+id+".png")
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(ProfilePicture);
    }

    public void onClickSubmin(View view) {

    }

}
