package com.example.projectdesign;

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

import com.example.Detalhes_Musica_Activity;
import com.example.adapters.GrelhaMusicaAdapter;
import com.example.listeners.MusicaListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.utils.MusicaJSONParser;

import java.util.ArrayList;

public class YourmusicsFragment extends Fragment implements MusicaListener {

    private GridView yourMusicsGrid;
    private ImageView imageViewGrelhaJava;
    private ArrayList<Musica> listaMusicasCompradasProduzidas;
    private SearchView searchView;

    public YourmusicsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yourmusics, container, false);
        setHasOptionsMenu(true);


        yourMusicsGrid = view.findViewById(R.id.YourMusicsGrid);
        imageViewGrelhaJava = view.findViewById(R.id.imageViewGrelha);

        yourMusicsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);

                boolean isYours = BeatBunnySingleton.getInstance(getContext()).checkIfYouOwnMusic(musica.getId());
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
    public void onRefreshListaMusica(ArrayList<Musica> musicas) {
        GrelhaMusicaAdapter grelhaMusicaAdapter = new GrelhaMusicaAdapter(getContext(), musicas);
        yourMusicsGrid.setAdapter(grelhaMusicaAdapter);
    }

    @Override
    public void onUpdateListaMusica(Musica musica, int operacao) {

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

                yourMusicsGrid.setAdapter(new GrelhaMusicaAdapter(getContext(), filtroListaMusica));
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
        BeatBunnySingleton.getInstance(getContext()).getYourStuffAndUpdateList(getContext(), MusicaJSONParser.isConnectionInternet(getContext()));

        super.onResume();
    }

}
