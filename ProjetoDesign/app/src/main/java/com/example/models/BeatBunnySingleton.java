package com.example.models;

import java.util.ArrayList;

public class BeatBunnySingleton {
    public int idMusica;
    private ArrayList<Musica> musicas;
    private static BeatBunnySingleton instance = null;
    public static synchronized BeatBunnySingleton getInstance() {
        if(instance == null)
            instance = new BeatBunnySingleton();
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



    private BeatBunnySingleton() {
        gerarFakeData();
    }

    private void gerarFakeData(){
        musicas = new ArrayList<>();
//        musicas.add(new Musica(1,"Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
//        musicas.add(new Musica(2,"Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
//        musicas.add(new Musica(3,"Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
//        musicas.add(new Musica(4,"Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
//        musicas.add(new Musica(5,"Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
//        musicas.add(new Musica(6,"Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
//        musicas.add(new Musica(7,"Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
//        musicas.add(new Musica(8,"Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
//        musicas.add(new Musica(9,"Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
//        musicas.add(new Musica(10,"Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
//        musicas.add(new Musica(11,"Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
//        musicas.add(new Musica(12,"Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
//        musicas.add(new Musica(13,"Rock","23/04/2019","musicpath",10,2,R.drawable.al1));
//        musicas.add(new Musica(14,"Rock","23/04/2019","musicpath",10,2,R.drawable.al2));
//        musicas.add(new Musica(15,"Rock","23/04/2019","musicpath",10,2,R.drawable.al3));
//        musicas.add(new Musica(16,"Rock","23/04/2019","musicpath",10,2,R.drawable.al4));
//        musicas.add(new Musica(17,"Rock","23/04/2019","musicpath",10,2,R.drawable.al5));
//        musicas.add(new Musica(18,"Rock","23/04/2019","musicpath",10,2,R.drawable.al6));
    }

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
