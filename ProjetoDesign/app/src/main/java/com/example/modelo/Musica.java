package com.example.modelo;
public class Musica {
        private String Title;
        private String Launchedate;
        private int Rating;
        private int Pvp;
        private int Musiccover;
        private String Musicpth;
        private static int autoincrementent=1;

        public Musica(int id, String title, String launchedate,String musicpth, int rating, int pvp, int musiccover) {
            autoincrementent = id++;
            Title=title;
            Launchedate=launchedate;
            Rating=rating;
            Pvp=pvp;
            Musiccover=musiccover;
            Musicpth=musicpth;
        }

        public void setTitle(String title) {
            Title = title;
        }
        public void setLaunchedate(String launchedate ) {
            Launchedate = launchedate;
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
            Musicpth=musicpth;
        }
        public int getId() {
            return autoincrementent;
        }
        public String getTitle() {
            return Title;
        }
        public String getLaunchedate() {
            return Launchedate;
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
            return Musicpth;
        }
    }

