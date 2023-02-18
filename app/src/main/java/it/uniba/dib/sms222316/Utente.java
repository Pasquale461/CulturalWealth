package it.uniba.dib.sms222316;

import android.net.Uri;

public class Utente {
    private final String name;
    private final String email;
    private final String points;
    private final Uri ProfileUri;



    public Utente(String name,String email, String points, Uri ProfileUri ){
        this.name = name;
        this.email = email;
        this.points = points;
        this.ProfileUri = ProfileUri;

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
    public Uri getProfileUri(){
        return ProfileUri;
    }



}
