package com.example.projectdesign;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listeners.UserListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.User;
import com.example.utils.UserJSONParser;

public class LoginActivity extends AppCompatActivity implements UserListener {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
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
            //TODO:guardar no shared: email,username,token
            Intent main = new Intent(this, MenuMainActivity.class);
            main.putExtra("IDUSER", user.getId());
            main.putExtra("USERNAME", user.getUsername());
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

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.ip_picker_dialog);
            dialog.setTitle("IP ADDRESS");
            final EditText textIP = dialog.findViewById(R.id.ipDialog);
            Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSave);
            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);

            dialogButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BeatBunnySingleton.getInstance(getApplicationContext()).setIP(textIP.getText().toString());
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
        }
        return super.onOptionsItemSelected(item);
    }


}
