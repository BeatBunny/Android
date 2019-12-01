package com.example.modelo;

import android.content.Context;

import com.example.projetodesign.R;

import java.util.ArrayList;

public class BeatBunnySingleton {
    public int idMusica;
    private ArrayList<Musica> musicas;
    private static BeatBunnySingleton instance = null;
    private BeatBunntDBHelper musicasBD = null;

    public static synchronized BeatBunnySingleton getInstance(Context context) {
        if(instance == null)
            instance = new BeatBunnySingleton(context);
        return instance;
    }


    //COM FOR
        public Musica getMusicaById(int idMusica){
        for(int i = 0; i < musicas.size(); i++){
            if(musicas.get(i).getId() == idMusica){
                return musicas.get(i);
            }
        }
        return null;
    }


    //COM FOREACH
    public Musica getMusica(int idMusica){
        for(Musica M : musicas){
            if(M.getId() == idMusica){
                return M;
            }
        }
        return null;
    }



    private BeatBunnySingleton(Context context) {
        //gerarFakeData();

        musicas= new ArrayList<Musica>();
        musicasBD = new BeatBunntDBHelper(context);
    }

    /*private void gerarFakeData(){
        musicas = new ArrayList<>();
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
        musicas.add(new Musica("Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
    }*/

    public ArrayList<Musica> getListaMusica() {
        return new ArrayList<>(musicas);
    }
    public void AdicionarMusica(Musica addmusica){
        musicas.add(addmusica);
    }
    public void Remover_musica(int Musica){
        Musica auxMusica= getMusica(idMusica);
        if (auxMusica!=null){
            musicas.remove(auxMusica);
        }

    }
    public void Adicionar_Musica(Musica musica)
    {
        musicas.add(musica);
    }

//    public void Editar_livro(Musica musica){
//        Musica auxMusica=getMusica(musica.getId());
//        musica.setLaunchedate(auxMusica.getLaunchedate());
//        musica.setMusiccover(auxMusica.getMusiccover());
//        musica.setMusicpth(auxMusica.getMusicpth());
//        musica.setPvp(auxMusica.getPvp());
//        musica.setTitle(auxMusica.getTitle());
//        musica.setRating(auxMusica.getRating());
//    }

}
