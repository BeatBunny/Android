package com.example.projectdesign;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Email;
    private EditText Password;
    private EditText Nome;
    private EditText Nif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Username= findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password= findViewById(R.id.editTextPassword);
        Nome = findViewById(R.id.editTextNome);
        Nif = findViewById(R.id.editTextNif);

    }

    public void onClickRegister(View view) {
        String username = Username.getText().toString();
        if (username==null) {
            Username.setError("Insert Username");
            return;
        }
        String email = Email.getText().toString();
        if (email==null){
            Email.setError("Insert Email");
            return;}
        String password = Password.getText().toString();
        if (password==null){
            Password.setError("Insert Password");
            return;}
        String nome = Nome.getText().toString();
        if (nome==null){
            Nome.setError("Insert Name");
            return;}
        String nif = Nif.getText().toString();
        if (nif==null){
            Nif.setError("Insert Nif");
            return;}


        /**Depois de efetuar o registo**/
        Intent log = new Intent(this, LoginActivity.class);
//      log.putExtra("EMAIL", email);
        startActivity(log);
        finish();
    }
}
