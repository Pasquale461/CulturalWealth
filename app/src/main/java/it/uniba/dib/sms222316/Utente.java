package it.uniba.dib.sms222316;

import android.app.MediaRouteActionProvider;

public class Utente {
    private String name;
    private String email;



    public Utente(String name,String email){
        this.name = name;
        this.email = name;

    }

    public String getName(){
        return name;
    }

    public String getMail(){
        return email;
    }



}
