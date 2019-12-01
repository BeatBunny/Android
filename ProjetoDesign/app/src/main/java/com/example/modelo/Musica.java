package com.example.modelo;
public class Musica {
        private int Id;
        private String Title;
        private String Launchdate;
        private int Rating;
        private int Pvp;
        private int Musiccover;
        private String Musicpath;
        private static int autoincrementent=1;

        public Musica( String title, String launchdate, String musicpath, int pvp, int musiccover) {
            Id = autoincrementent++;
            Title=title;
            Launchdate=launchdate;
            Pvp=pvp;
            Musiccover=musiccover;
            Musicpath=musicpath;
        }
    public void setId(int id) {
        Id = id;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public void setLaunchedate(String launchedate ) {
        Launchdate = launchedate;
    }
    public void setRating(int rating) {
        Rating = rating;
    }
    public void setPvp(int pvp) {
        Pvp = pvp;
    }
    public void setMusiccover(int musiccover) {
        Musiccover = musiccover;
    }
    public void setMusicpth(String musicpth){
        Musicpath=musicpth;
    }
    public int getId() {
        return autoincrementent;
    }
    public String getTitle() {
        return Title;
    }
    public String getLaunchedate() {
        return Launchdate;
    }
    public int getRating() {
        return Rating;
    }
    public int getMusiccover() {
        return Musiccover;
    }
    public int getPvp(){
        return Pvp;
    }
    public String getMusicpth() {
        return Musicpath;
    }
}

