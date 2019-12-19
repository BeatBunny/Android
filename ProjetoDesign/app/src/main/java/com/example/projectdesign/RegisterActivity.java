package com.example.projectdesign;
import android.app.Dialog;
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

public class RegisterActivity extends AppCompatActivity implements UserListener {

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
        BeatBunnySingleton.getInstance(getApplicationContext()).setUserListener(this);
        setTitle("BeatBunny");
    }

    public void onClickRegisterinsideactivity(View view) {
        String username = Username.getText().toString();
        if (username.length()==0) {
            Username.setError("Insert Username");
            return;
        }
        String email = Email.getText().toString();
        if (email.length()==0){
            Email.setError("Insert Email");
            return;
        }
        String password = Password.getText().toString();
        if (password.length()==0){
            Password.setError("Insert Password");
            return;
        }
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
        BeatBunnySingleton.getInstance(getApplicationContext()).registoUserAPI(username, password, email, nome, nif, getApplicationContext(), UserJSONParser.isConnectionInternet(getApplicationContext()));

    }

    @Override
    public void onRefreshListaUser(User user) {

        this.finish();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return null;
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
