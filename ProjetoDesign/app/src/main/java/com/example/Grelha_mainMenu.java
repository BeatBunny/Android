package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.adapters.GrelhaMusicaAdapter;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.projectdesign.R;
import com.example.utils.MusicaJSONParser;

import java.util.ArrayList;
public class Grelha_mainMenu extends Fragment {
    private GridView grelhaMusicas;
    private ArrayList<Musica> listamusicas;

    private SearchView searchView;
    public Grelha_mainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listamusicas = BeatBunnySingleton.getInstance(getContext()).getListaMusica();
        grelhaMusicas = view.findViewById(R.id.GrelhaDeMusicas);
        grelhaMusicas.setAdapter(new GrelhaMusicaAdapter(getContext(), listamusicas));
        grelhaMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), Detalhes_Musica_Activity.class);
                intent.putExtra("DETALHES",musica.getId());
                startActivity(intent);
                System.out.println("--> Musica clicada = "+musica.getTitle());
            }
        });
        return view;
    }

    public void onResume(){
        if(searchView!=null){
            searchView.onActionViewCollapsed();
        }
        BeatBunnySingleton.getInstance(getContext()).getAllMusicasAPI(getContext(), MusicaJSONParser.isConnectionInternet(getContext()));
        super.onResume();
    }

}
