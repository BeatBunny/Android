package com.example.projectdesign;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
    }

    public void onClickLogin(View view){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(!IsValidUsername(email) ) {
            emailEditText.setError("O username está errado");
            return;
        }


        //BeatBunnySingleton.getInstance(getApplicationContext()).getAllUsersAPI(getApplicationContext(), UserJSONParser.isConnectionInternet(getApplicationContext()));

        Intent main = new Intent(this, MenuMainActivity.class);
        main.putExtra("EMAIL", email);
        startActivity(main);
        finish();
    }

    private boolean IsValidUsername(String email){
        boolean tof = false;
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            tof = true;

        return tof;
    }

}
