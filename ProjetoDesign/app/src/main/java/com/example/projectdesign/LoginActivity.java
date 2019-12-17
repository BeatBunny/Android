package com.example.projectdesign;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        BeatBunnySingleton.getInstance(getApplicationContext()).setUserListener(this);
    }

    public void onClickLogin(View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        BeatBunnySingleton.getInstance(getApplicationContext()).loginUserAPI(username, password, getApplicationContext(), UserJSONParser.isConnectionInternet(getApplicationContext()));

    }



    @Override
    public void onRefreshListaUser(User user) {
        if(user == null){
            usernameEditText.setError("Username or Password is wrong");
        }
        else{
            //TODO:guardar no shared: email,username,token
            Intent main = new Intent(this, MenuMainActivity.class);
            main.putExtra("EMAIL", user.getEmail());
            startActivity(main);
            finish();
        }
    }
}
