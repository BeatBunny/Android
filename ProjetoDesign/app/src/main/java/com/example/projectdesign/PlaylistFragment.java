package com.example.projectdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
//import com.diegodobelo.expandingview.ExpandingItem;
//import com.diegodobelo.expandingview.ExpandingList;

public class PlaylistFragment extends Fragment {

    public PlaylistFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlists, container, false);

        ExpandingList expandingList = (ExpandingList) view.findViewById(R.id.expanding_list_playlists);

        ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout_playlists);

        ((TextView) item.findViewById(R.id.title_playlist)).setText("It Works!!");

        item.createSubItems(5);

        View subItemZero = item.getSubItemView(0);
        ((TextView) subItemZero.findViewById(R.id.sub_playlist)).setText("Cool");

        View subItemOne = item.getSubItemView(1);
        ((TextView) subItemOne.findViewById(R.id.sub_playlist)).setText("Awesome");

        item.setIndicatorColorRes(R.color.common_google_signin_btn_text_light_default);
        item.setIndicatorIconRes(R.drawable.ic_musicbeat);


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
