package com.example.modelo;

import android.database.Cursor;

import java.util.ArrayList;

public class Musica {
        private String Title;
        private String Launchedate;
        private int Rating;
        private int Musiccover;
        private String Musicpth;
        private String Musicgenre;
        private String Lyrics;
        private static int iD=1;
        private int Profile_id;


        public Musica(int id, String title, String launchedate,String musicpth,String musicgenre,String lyrics, int rating, int musiccover, int profile_id) {
            iD = id;
            Musicgenre=musicgenre;
            Title=title;
            Profile_id=profile_id;
            Lyrics=lyrics;
            Launchedate=launchedate;
            Rating=rating;
            Musiccover=musiccover;
            Musicpth=musicpth;
        }

        public void setiD(int id){iD = id;}
        public void setTitle(String title) {
            Title = title;
        }
        public void setLaunchedate(String launchedate ) {
            Launchedate = launchedate;
        }
        public void setRating(int rating) {
            Rating = rating;
        }
        public void setMusiccover(int musiccover) {
            Musiccover = musiccover;
        }
        public void setMusicpth(String musicpth){
            Musicpth=musicpth;
        }
        public void setMusicgenre(String musicgenre){Musicgenre=musicgenre;}
        public void setLyrics(String lyrics){Lyrics=lyrics;}
        public void setProfile_id(int profile_id){Profile_id=profile_id;}
        public int getiD() {return iD;}
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
        public int getProfile_id(){return Profile_id;}
        public String getMusicgenre(){return Musicgenre;}
        public String getMusicpth() {
            return Musicpth;
        }
        public String getLyrics(){return Lyrics;}

    }

