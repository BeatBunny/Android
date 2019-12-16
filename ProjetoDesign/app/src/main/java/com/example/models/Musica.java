package com.example.models;

public class Musica {
        private String Title;
        private String Launchdate;
        private String Musiccover;
        private String Musicpth;
        private String Musicgenre;
        private String Lyrics;
        private static int Id=1;
        private String Producer;


        public Musica(int id, String title, String launchdate,String musicpth,String musicgenre,String lyrics, String musiccover, String  producer) {
            Id = id;
            Musicgenre=musicgenre;
            Title=title;
            Producer=producer;
            Lyrics=lyrics;
            Launchdate=launchdate;
            Musiccover=musiccover;
            Musicpth=musicpth;
        }


        public void setiD(int id){Id = id;}
        public void setTitle(String title) {
            Title = title;
        }
        public void setLaunchedate(String launchdate) {
            Launchdate = launchdate;
        }
        public void setMusiccover(String musiccover) {
            Musiccover = musiccover;
        }
        public void setMusicpth(String musicpth){
            Musicpth=musicpth;
        }
        public void setMusicgenre(String musicgenre){Musicgenre=musicgenre;}
        public void setLyrics(String lyrics){Lyrics=lyrics;}
        public void setProducer(String producer){Producer=producer;}


        public int getId() {return Id;}
        public String getTitle() {
            return Title;
        }
        public String getLaunchedate() {
            return Launchdate;
        }
        public String getMusiccover() {
            return Musiccover;
        }
        public String getProducer(){return Producer;}
        public String getMusicgenre(){return Musicgenre;}
        public String getMusicpth() {
            return Musicpth;
        }
        public String getLyrics(){return Lyrics;}

}

