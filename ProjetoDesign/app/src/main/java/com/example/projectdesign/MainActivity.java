package com.example.projectdesign;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DADOS_USERNAME = "username";

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }


    private void carregarCabecalho(){
        email = getIntent().getStringExtra("EMAIL");
        System.out.println("-->"+email);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.textViewEmail);
        nav_user.setText(email);
    }


    /*private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_estatico);
        Fragment fragment = new ListaLivrosFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle(navigationView.getMenu().getItem(0).getTitle());
    }*/





    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                //fragment = new ListaLivrosFragment();
                setTitle(menuItem.getTitle());
                System.out.println("-->Nav Menu");
                break;
            case R.id.nav_about:
                setTitle(menuItem.getTitle());
                //fragment = new GrelhaLivrosFragment();
                System.out.println("-->Nav About");
                break;
            case R.id.nav_musics:
                setTitle(menuItem.getTitle());
                fragment = new MusicFragment();
                System.out.println("-->Nav Musics");
                break;
            case R.id.nav_email:
                System.out.println("-->Nav Email");
                break;
        }
        if(fragment != null){
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

