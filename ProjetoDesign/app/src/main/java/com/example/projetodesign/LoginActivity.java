package com.example.projetodesign;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.projetodesign.MenuMainActivity;
import com.example.projetodesign.R;

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

        if(!IsValidPassword(password)) {
            passwordEditText.setError("A password está errada");
            return;
        }

        Intent main = new Intent(this, MenuMainActivity.class);
        main.putExtra("EMAIL", email);
        startActivity(main);
        finish();

    }

    private boolean IsValidUsername(String email){
        boolean tof = false;
        String goodUsername = "richardus1@gmail.com";
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && goodUsername.equals(email))
            tof = true;

        return tof;
    }

    private boolean IsValidPassword(String pass){
        boolean tof = false;

        String goodPassword = "ggez";
        if(goodPassword.equals(pass))
            tof = true;

        return tof;
    }

}
