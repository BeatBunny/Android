package com.example.projectdesign;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listeners.UserListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Profile;
import com.example.models.SharedPreferencesSettersGetters;
import com.example.models.User;
import com.example.utils.ProfileJSONParser;
import com.example.utils.UserJSONParser;

public class LoginActivity extends AppCompatActivity implements UserListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private String ip;
    public static final String INFO_USER = "user";
    private String username;
    private String authkey;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        SharedPreferencesSettersGetters.init(getApplicationContext());


        Integer UserIdSaved = SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0);
        String UserUsernameSaved = SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.USERNAME_USER, null);
        String UserAuthkeySaved = SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null);

        if(UserIdSaved != 0 && UserUsernameSaved != null && UserAuthkeySaved != null){
            Intent main = new Intent(this, MenuMainActivity.class);
            main.putExtra("IDUSER", UserIdSaved);
            main.putExtra("USERNAME", UserUsernameSaved);
            main.putExtra("AUTH_KEY", UserAuthkeySaved);
            System.out.println("->>>>>>>>>>>>>>>>>>> Tinha lá dados guardados:"+ UserIdSaved + " | "+UserUsernameSaved + " | "+UserAuthkeySaved);
            startActivity(main);
            finish();
        }

    }

    public void onClickLogin(View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        BeatBunnySingleton.getInstance(getApplicationContext()).loginUserAPI(username, password, getApplicationContext(), UserJSONParser.isConnectionInternet(getApplicationContext()));

    }

    public void onClickRegister(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }



    @Override
    public void onRefreshListaUser(User user) {
        if(user == null){
            usernameEditText.setError("Username or Password is wrong");
        }
        else{
            SharedPreferencesSettersGetters.writeInt(SharedPreferencesSettersGetters.ID_USER, user.getId());//save int in shared preference.
            SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.USERNAME_USER, user.getUsername());//save string in shared preference.
            SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.AUTH_KEY, user.getAuthKey());//save boolean in shared preference.
            BeatBunnySingleton.getInstance(getApplicationContext()).getProfileFromLogin(getApplicationContext(), ProfileJSONParser.isConnectionInternet(getApplicationContext()));
        }
    }

    @Override
    public void onRefreshListaProfiles(Profile profile) {
        if(profile != null) {
            //TODO:guardar no shared: email,username,token
            SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.NOME_PROFILE, profile.getNome());//save int in shared preference.
            SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.SALDO_PROFILE, profile.getSaldo());//save int in shared preference.
            SharedPreferencesSettersGetters.writeString(SharedPreferencesSettersGetters.NIF_PROFILE, profile.getNif());//save string in shared preference.
            Intent main = new Intent(this, MenuMainActivity.class);
            main.putExtra("IDUSER", SharedPreferencesSettersGetters.readInt(SharedPreferencesSettersGetters.ID_USER, 0));
            main.putExtra("USERNAME", SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.USERNAME_USER, null));
            main.putExtra("AUTH_KEY", SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.AUTH_KEY, null));
            startActivity(main);
            finish();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void onResume() {
        BeatBunnySingleton.getInstance(getApplicationContext()).setUserListener(this);
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_three_dots_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.action_settings){
            createDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    public Dialog createDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.ip_picker_dialog);
        TextView titulo = dialog.findViewById(R.id.ip_picker_dialog_title);
        titulo.setText("IP ADDRESS");
        dialog.setTitle("IP ADDRESS");
        final EditText textIP = dialog.findViewById(R.id.ipDialog);
        textIP.setText(SharedPreferencesSettersGetters.readString(SharedPreferencesSettersGetters.SETTINGS_IP, null));
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSave);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);

        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeatBunnySingleton.getInstance(getApplicationContext()).setIP(textIP.getText().toString());
                Toast.makeText(getApplicationContext(), BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput(), Toast.LENGTH_SHORT).show();
                textIP.setText(BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput());
                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        return dialog;
    }


}
