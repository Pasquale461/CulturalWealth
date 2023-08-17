package it.uniba.dib.sms222316;

import android.net.Uri;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private List<DocumentReference> posseduti;
    private String ProfilePic;
    private String email;
    private String friend_code;
    private String name;
    private String points;




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
