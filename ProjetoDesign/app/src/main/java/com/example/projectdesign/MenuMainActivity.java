package com.example.projectdesign;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.Grelha_mainMenu;
import com.example.Settings;
import com.example.models.BeatBunnySingleton;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String username;
    private String authkey;
    private int id;
    private FragmentManager fragmentManager;
    private ImageView imageViewProfileNavbar;
    private String currentIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentIP = BeatBunnySingleton.getInstance(getApplicationContext()).getIPInput();
        setContentView(R.layout.activity_menu_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.abrir, R.string.fechar );
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        carregarCabecalho();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();

    }


    private void carregarCabecalho(){
        username = getIntent().getStringExtra("USERNAME");
        id = getIntent().getIntExtra("IDUSER", 0);
        authkey = getIntent().getStringExtra("AUTH_KEY");
        System.out.println("-->"+ username);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.textViewEmail);
        nav_user.setText(username);


        imageViewProfileNavbar = hView.findViewById(R.id.imageViewUserBNavbar);
        Glide.with(getApplicationContext())
                .load("http://"+currentIP+":80/BeatBunny/advanced/frontend/web/uploads/"+id+"/profileimage_"+id+".png")
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(imageViewProfileNavbar);
    }


    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new Grelha_mainMenu();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle(navigationView.getMenu().getItem(0).getTitle());
    }

    public Dialog createDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.logout_dialog);
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonYes);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancelLogout);
        boolean returningShit = false;

        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BeatBunnySingleton.getInstance(getApplicationContext()).setIP(textIP.getText().toString());

                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
                dialog.dismiss();
                finish();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogo) {

                System.out.println("-->>>>>>>>>>>>>>>>> DISMISSED");
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogo) {

                System.out.println("-->>>>>>>>>>>>>>>>> CANCELED");
            }
        });
        dialog.show();
        return dialog;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new Grelha_mainMenu();
                setTitle(menuItem.getTitle());
                System.out.println("-->Nav Menu");
                break;
            case R.id.nav_musics:
                setTitle(menuItem.getTitle());
                fragment = new Grelha_mainMenu();
                System.out.println("-->Nav Your Musics");
                break;
            case R.id.nav_playlists:
                setTitle(menuItem.getTitle());
                fragment = new PlaylistFragment();
                System.out.println("-->Nav Playlists");
                break;
            case R.id.nav_settings:
                System.out.println("-->Nav Settings");
                fragment = new Settings();
                break;
            case R.id.nav_logout:
                createDialog();
                System.out.println("-->Nav Logout");
        }
        if(fragment != null){
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        //TODO: desfazer-se do USER enviado pela api nas SharedPreferences
        super.onStop();
    }
}

