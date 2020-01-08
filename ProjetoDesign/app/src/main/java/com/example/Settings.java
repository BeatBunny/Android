package com.example;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.listeners.UserListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Profile;
import com.example.models.SharedPreferencesSettersGetters;
import com.example.models.User;
import com.example.projectdesign.R;
import com.example.utils.ProfileJSONParser;

import java.util.Objects;

public class Settings extends Fragment implements UserListener {

    private String username,saldo;
    private int id;
    private String currentIP;
    private EditText Nome, Nif;
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
                Nif.setFocusableInTouchMode(true);
                Nome.setEnabled(true);
                Nif.setEnabled(true);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            String novo_nome;
            String novo_nif;
            @Override
            public void onClick(View v) {

                String nome = Nome.getText().toString();
                if (nome.length()==0){
                    Nome.setError("Insert Name");
                    return;
                }
                String nif = Nif.getText().toString();
                if (nif.length()==0){
                    Nif.setError("Insert Nif");
                    return;
                }

                if(!nif.matches("[0-9]+")){
                    Nif.setError("NIF has to be numbers only");
                    return;
                }

                if(nif.length() != 9){
                    Nif.setError("NIF has to be 9 numbers long");
                    return;
                }

                novo_nome= Nome.getText().toString();
                novo_nif= Nif.getText().toString();

                BeatBunnySingleton.getInstance(getContext()).saveSettings(novo_nome, novo_nif, getContext(), ProfileJSONParser.isConnectionInternet(getContext()));


            }
        });
        //grelhaMusicas.setAdapter(new GrelhaMusicaAdaptor(getContext(), listamusicas));
        return view;
    }

    private void GetUserData(){
        Nome.setFocusable(false);
        Nome.setEnabled(false);
        Nif.setFocusable(false);
        Nif.setEnabled(false);
        Submit.setVisibility(View.GONE);
        Change_Settings.setVisibility(View.VISIBLE);
        id = Objects.requireNonNull(getActivity()).getIntent().getIntExtra("IDUSER", 0);
        User_nome.setText(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("USERNAME"));
        User_nome.setTextSize(25);
        User_nome.setTextColor(Color.parseColor("#80CBC4"));
        User_balance.setText(Objects.requireNonNull(SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.SALDO_PROFILE, null))+" €");
        Nome.setText(Objects.requireNonNull(SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.NOME_PROFILE, null)));
        Nif.setText(Objects.requireNonNull(SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.NIF_PROFILE, null)));
        Glide.with(getActivity().getApplicationContext())
                .load("http://"+currentIP+":80/BeatBunny/advanced/frontend/web/uploads/"+id+"/profileimage_"+id+".png")
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(ProfilePicture);
    }


    @Override
    public void onRefreshListaUser(User user) {

    }

    @Override
    public void onRefreshListaProfiles(Profile profile) {
        SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.NOME_PROFILE, profile.getNome());//save int in shared preference.
        SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.NIF_PROFILE, profile.getNif());//save string in shared preference.
        GetUserData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return null;
    }


    @Override
    public void onResume() {
        BeatBunnySingleton.getInstance(getContext()).setUserListener(this);
        super.onResume();
    }

}
