package com.example.projectdesign;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.listeners.PlaylistListener;
import com.example.models.BeatBunnySingleton;
import com.example.models.Musica;
import com.example.models.Playlist;
import com.example.utils.PlaylistJSONParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PlaylistFragment extends Fragment implements PlaylistListener {

    private int musicNumber = 0;
    private ArrayList<Playlist> playlists;


    public PlaylistFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!PlaylistJSONParser.isConnectionInternet(getContext())){
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else{
                    createDialog();
                }
            }
        });

        ExpandingList expandingList = (ExpandingList) view.findViewById(R.id.expanding_list_playlists);

        playlists = BeatBunnySingleton.getInstance(getContext()).getPlaylists();


        //TODO: FOR A PARTIR DAQUI A ENGLOBAR AS PLAYLISTS
        for( Playlist pl : playlists ) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout_playlists);

            ((TextView) item.findViewById(R.id.title_playlist)).setText(pl.getNome());

            //TODO: CADA SUBITEM É UMA MUSICA, FOREACHE DENTRO DO FOREACH DAS PLAYLISTS PARA CADA UMA DAS PLAYLISTS ||||||||||| TIRAR COMENTARIOS PARA TESTAR (CURRENTLY NOT WORKING)
            System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (Musica ms : pl.getListaMusicasDestaPlaylist() ) {
                System.out.println(ms.getTitle() + "\n ----------------------------------");
            }
            item.createSubItems(pl.getListaMusicasDestaPlaylist().size());
            for (Musica ms : pl.getListaMusicasDestaPlaylist()){
                View subItem = item.getSubItemView(musicNumber);
                subItem.findViewById(R.id.sub_playlist);


                musicNumber++;
            }

           /* item.createSubItems(pl.getListaMusicasDestaPlaylist().size());

            for( Musica ms : pl.getListaMusicasDestaPlaylist() ) {
                View subItem = item.getSubItemView(musicNumber);
                ((TextView) subItem.findViewById(R.id.sub_playlist)).setText(ms.getTitle());

                musicNumber++;
            }
*/
            item.setIndicatorColorRes(R.color.common_google_signin_btn_text_light_default);
            item.setIndicatorIconRes(R.drawable.ic_musicbeat);

        }
        //TODO: CRIAR PLAYLIST
            //feito api
                //http://localhost/BeatBunny/advanced/backend/web/v1/playlists/playlistcreate?access-token=XXX
                /*
                * ENVIAR POR POST:
                *   "nome":"BeatBunnyPlaylist #N",
                *   "idUser":"X" (ID DO USER NA SHAREDPREFERENCES)
                *
                * */

        //TODO: EDITAR O NOME DA PLAYLIST
            //feito api
                //http://localhost/BeatBunny/advanced/backend/web/v1/playlists/playlistupdate/11?access-token=XXX
                /*
                * ENVIAR POR PUT
                *   "nome":"BeatBunnyPlaylist #N"
                *
                * */

        //TODO: ADICIONAR A MÚSICA À PLAYLIST (A PARTIR DA ACTIVITY DA MÚSICA)
            //feito api
                //http://localhost/BeatBunny/advanced/backend/web/v1/playlists/putsong?access-token=            XXX
                /*
                * ENVIAR POR POST
                *   "idPlaylist":"ID DA PLAYLIST QUE É PARA TER A MÚSICA",
                *   "idMusic":"ID DA MÚSICA QUE É PARA ADICIONAR À PLAYLIST"
                *
                * */

        //TODO: REMOVER A MUSICA DA PLAYLIST
            //feito api
                //http://localhost/BeatBunny/advanced/backend/web/v1/playlists/removesong?access-token=         XXX
                /*
                * ENVIAR POR DELETE
                *   "idPlaylist":"ID DA PLAYLIST QUE É PARA TER A MÚSICA",
                *   "idMusic":"ID DA MÚSICA QUE É PARA ADICIONAR À PLAYLIST"
                *
                * */

        //TODO: ELIMINAR A PLAYLIST TODA
            //feito api
                //http://localhost/BeatBunny/advanced/backend/web/v1/playlists/delete/      XX    ?access-token=    XXX
                /*
                * ENVIAR POR DELETE
                *   é preciso meter o ID da PLAYLIST que é para ELIMINAR
                *
                * */



        /*TREZE != TREUZE*/

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



        BeatBunnySingleton.getInstance(getContext()).setPlaylistListener(this);

        return view;
    }

    public Dialog createDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.new_playlist_dialog);
        dialog.setTitle("New Playlist");
        final EditText textNomePlaylist = dialog.findViewById(R.id.newPlaylist);
        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSavePlaylist);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancelPlaylist);

        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BeatBunnySingleton.getInstance(getContext()).setIP(textNomePlaylist.getText().toString());
                //Toast.makeText(getContext(), BeatBunnySingleton.getInstance(getContext()).getIPInput(), Toast.LENGTH_SHORT).show();

                BeatBunnySingleton.getInstance(getContext()).createNewPlaylist(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()), textNomePlaylist.getText().toString());

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
        return dialog;
    }

    @Override
    public void onRefreshListaPlaylist(ArrayList<Playlist> playlist) {

        /*GrelhaMusicaAdapter grelhaMusicaAdapter = new GrelhaMusicaAdapter(getContext(), musicas);
        grelhaMusicas.setAdapter(grelhaMusicaAdapter);*/
    }

    @Override
    public void onUpdateListaMusica(Playlist playlist, int operacao) {

    }

    @Override
    public void onResume() {
        BeatBunnySingleton.getInstance(getContext()).getAllPlaylistsFromUserAPI(getContext(), PlaylistJSONParser.isConnectionInternet(getContext()));
        super.onResume();

    }



}
