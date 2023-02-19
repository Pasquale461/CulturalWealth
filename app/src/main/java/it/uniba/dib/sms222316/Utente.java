package it.uniba.dib.sms222316;

import android.net.Uri;

public class Utente {
    private final String name;
    private final String email;
    private final String points;
    private final String ProfilePic;



    public Utente(String name,String email, String points, String ProfilePic ){
        this.name = name;
        this.email = email;
        this.points = points;
        this.ProfilePic = ProfilePic;

    }

    public String getName(){
        return name;
    }
    public String getScore(){
        return points;
    }

    public String getMail(){
        return email;
    }
    public String getProfilePic(){
        return ProfilePic;
    }



}
