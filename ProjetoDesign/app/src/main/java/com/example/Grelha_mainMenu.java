package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.adapters.GrelhaMusicaAdapter;
import com.example.listeners.MusicaListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.Playlist;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;
import com.example.utils.ProfileJSONParser;

import java.util.ArrayList;
public class Grelha_mainMenu extends Fragment implements MusicaListener {
    private GridView grelhaMusicas;
    private ArrayList<Musica> allMusics;
    private ArrayList<Musica> listaMusicasCompradasProduzidas;
    private ImageView imageViewGrelhaJava;
    private SearchView searchView;
    private ArrayList<Playlist> listaComTodasAsPlaylists;

    public Grelha_mainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);

        grelhaMusicas = view.findViewById(R.id.GrelhaDeMusicas);
        imageViewGrelhaJava = view.findViewById(R.id.imageViewGrelha);

        BeatBunnySingleton.getInstance(getContext()).getAllMusicasAPI(getContext(), MusicaJSONParser.isConnectionInternet(getContext()));

        grelhaMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);
                boolean isYours = false;

                isYours = BeatBunnySingleton.getInstance(getContext()).checkIfYouOwnMusic(musica.getId());

                Intent intent = new Intent(getContext(), Detalhes_Musica_Activity.class);
                intent.putExtra("DETALHES",musica.getId());
                intent.putExtra("IS_BOUGHT", isYours);
                startActivity(intent);

                System.out.println("--> Musica clicada = "+musica.getTitle());
            }
        });

        BeatBunnySingleton.getInstance(getContext()).setMusicaListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(!MusicaJSONParser.isConnectionInternet(getContext()))
            return;

        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);

        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Musica> filtroListaMusica = new ArrayList<Musica>();
                for (Musica m:BeatBunnySingleton.getInstance(getContext()).getAllMusics())
                    if(m.getTitle().toLowerCase().contains(newText.toLowerCase()))
                        filtroListaMusica.add(m);

                grelhaMusicas.setAdapter(new GrelhaMusicaAdapter(getContext(), filtroListaMusica));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onResume(){
        if(searchView!=null){
            searchView.onActionViewCollapsed();
        }
        BeatBunnySingleton.getInstance(getContext()).getAllMusicasAPI(getContext(), MusicaJSONParser.isConnectionInternet(getContext()));
        listaMusicasCompradasProduzidas = BeatBunnySingleton.getInstance(getContext()).getYourStuffAndDontUpdateList(getContext(), MusicaJSONParser.isConnectionInternet(getContext()));
        BeatBunnySingleton.getInstance(getContext()).getSaldoAfterPurchase(getContext(), ProfileJSONParser.isConnectionInternet(getContext()));
        //BeatBunnySingleton.getInstance(getContext()).getAllUsersAPI(getContext(), UserJSONParser.isConnectionInternet(getContext()));

        super.onResume();
    }


    @Override
    public void onRefreshListaMusica(ArrayList<Musica> musicas) {
        GrelhaMusicaAdapter grelhaMusicaAdapter = new GrelhaMusicaAdapter(getContext(), musicas);
        grelhaMusicas.setAdapter(grelhaMusicaAdapter);
    }

    @Override
    public void onUpdateListaMusica(Musica musica, int operacao) {

    }
}
