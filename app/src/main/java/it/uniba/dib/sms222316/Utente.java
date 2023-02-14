package it.uniba.dib.sms222316;

public class Utente {
    private final String name;
    private final String email;
    private final String points;



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
