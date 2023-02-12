package it.uniba.dib.sms222316;

import android.app.MediaRouteActionProvider;

public class Utente {
    private String name;
    private String email;
    private String points;



    public Utente(String name,String email, String points ){
        this.name = name;
        this.email = name;
        this.points = name;

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



}
