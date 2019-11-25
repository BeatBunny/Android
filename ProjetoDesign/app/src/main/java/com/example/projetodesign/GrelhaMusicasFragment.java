package com.example.projetodesign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;


public class GrelhaMusicasFragment extends Fragment {


    private GridView gvGrelhaMusicas;

    private SearchView searchView;


    public GrelhaMusicasFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grelha_musicas, container, false);

        setHasOptionsMenu(true);

        //listaMusicas = SingletonGestorMusicas.getInstance().getListaMusicas();

        gvGrelhaMusicas = view.findViewById(R.id.gvGrelhaMusicas);

        /*gvGrelhaMusicas.setAdapter(new GrelhaMusicasAdaptador(getContext(), listaMusicas));

        gvGrelhaMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalhesMusicaActivity.class);
                intent.putExtra("DETALHES",musica.getId());
                startActivity(intent);

                System.out.println("--> Musica clicada = "+musica.getTitulo());
            }
        });*/

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
                /*ArrayList<Musica> filtroListaLivros = new ArrayList<Musica>();
                for (Musica m:SingletonGestorMusicas.getInstance().getListaLivros())
                    if(m.getTitulo().toLowerCase().contains(newText.toLowerCase()))
                        filtroListaLivros.add(m);

                gvGrelhaMusicas.setAdapter(new GrelhaMusicasAdaptador(getContext(), filtroListaLivros));*/
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
