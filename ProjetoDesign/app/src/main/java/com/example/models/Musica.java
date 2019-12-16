package com.example.models;

public class Musica {
        private int Id;
        private String Title;
        private String Launchdate;
        private String Musiccover;
        private String Musicpath;
        private String Musicgenre;
        private String Lyrics;
        private String Producer;
        private float Pvp;


        public Musica(int id, String title, String launchdate,String musicpath,String musicgenre,String lyrics, String musiccover, String producer, float pvp) {
            Id = id;
            Title=title;
            Launchdate=launchdate;
            Musicpath=musicpath;
            Musicgenre=musicgenre;
            Lyrics=lyrics;
            Musiccover=musiccover;
            Producer=producer;
            Pvp = pvp;
        }


        //public void setId(int id){Id = id;}
        public void setTitle(String title) {
            Title = title;
        }
        public void setLaunchdate(String launchdate) {
            Launchdate = launchdate;
        }
        public void setMusiccover(String musiccover) {
            Musiccover = musiccover;
        }
        public void setMusicpath(String musicpath){
            Musicpath=musicpath;
        }
        public void setMusicgenre(String musicgenre){Musicgenre=musicgenre;}
        public void setLyrics(String lyrics){Lyrics=lyrics;}
        public void setProducer(String producer){Producer=producer;}


        public int getId() {return Id;}
        public String getTitle() {
            return Title;
        }
        public String getLaunchdate() {
            return Launchdate;
        }
        public String getMusiccover() {
            return Musiccover;
        }
        public String getProducer(){return Producer;}
        public String getMusicgenre(){return Musicgenre;}
        public String getMusicpath() {
            return Musicpath;
        }
        public String getLyrics(){return Lyrics;}
        public float getPvp(){return Pvp;}

}

