package com.example.projectdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
//
//import com.diegodobelo.expandingview.ExpandingItem;
//import com.diegodobelo.expandingview.ExpandingList;

public class PlaylistFragment extends Fragment {

    public PlaylistFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        
        View view = inflater.inflate(R.layout.expanding_playlists, container, false);

        /*listaLivros = SingletonGestorLivros.getInstance().getListaLivros();

        lvListaLivros = view.findViewById(R.id.lvListaLivros);

        lvListaLivros.setAdapter(new ListaLivroAdaptador(getContext(), listaLivros));

        lvListaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Livro livro = (Livro) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra("DETALHES",livro.getId());
                startActivity(intent);

                System.out.println("--> Livro clicado = "+livro.getTitulo());
            }
        });*/

        return view;
    }
}
