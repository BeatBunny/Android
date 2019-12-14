package com.example;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.adapters.GrelhaMusicaAdapter;
import com.example.models.Musica;
import com.example.models.BeatBunnySingleton;
import com.example.projectdesign.R;
import java.util.ArrayList;
public class Grelha_mainMenu extends Fragment {
    private GridView grelhaMusicas;
    private ArrayList<Musica> listamusicas;

    public Grelha_mainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listamusicas = BeatBunnySingleton.getInstance().getListaMusica();
        grelhaMusicas = view.findViewById(R.id.GrelhaDeMusicas);
        grelhaMusicas.setAdapter(new GrelhaMusicaAdapter(getContext(), listamusicas));
        grelhaMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), Detalhes_Musica_Activity.class);
                intent.putExtra("DETALHES",musica.getId());
                startActivity(intent);
                System.out.println("--> Livro clicado = "+musica.getTitle());
            }
        });
        return view;
    }
}
