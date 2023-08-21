package it.uniba.dib.sms222316;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class endgame_popup extends AppCompatActivity {

    int selected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_endgame);

        Intent intent = getIntent();
        int punteggioprimo = intent.getIntExtra("punteggioprimo" ,0);
        int punteggiosecondo =intent.getIntExtra("punteggiosecondo", 0);
        int punteggioterzo =intent.getIntExtra("punteggioterzo" , 0);
        String nomeprimo = intent.getStringExtra("nomeprimo");
        String nomesecondo =intent.getStringExtra("nomesecondo");
        String nometerzo =intent.getStringExtra("nometerzo");




        TextView pprimo = findViewById(R.id.firstpoints);
        TextView psecondo = findViewById(R.id.secondpoints);
        TextView pterzo = findViewById(R.id.thirdpoints);
        TextView nprimo = findViewById(R.id.firstname);
        TextView nsecondo = findViewById(R.id.secondname);
        TextView nterzo = findViewById(R.id.thirdname);
        ImageView continu = findViewById(R.id.continu);
        continu.setOnClickListener(v -> {
            Intent i = new Intent(endgame_popup.this, Home.class);
            startActivity(i);
        });


        pprimo.setText(""+punteggioprimo);
        psecondo.setText(""+punteggiosecondo);
        pterzo.setText(""+punteggioterzo);

        if (nomeprimo.equals("Giocatore 1")) {
            findusername(FirebaseAuth.getInstance().getCurrentUser().getEmail(), nprimo);
            nsecondo.setText(nomesecondo);
            nterzo.setText(nometerzo);
        } else if (nomesecondo.equals("Giocatore 1")) {
            findusername(FirebaseAuth.getInstance().getCurrentUser().getEmail(), nsecondo);
            nprimo.setText(nomeprimo);
            nterzo.setText(nometerzo);
        } else if (nometerzo.equals("Giocatore 1")) {
            findusername(FirebaseAuth.getInstance().getCurrentUser().getEmail(), nterzo);
            nsecondo.setText(nomesecondo);
            nprimo.setText(nomeprimo);
        }





    }

    private void findusername(String Accountstring , TextView v)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", Accountstring)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        v.setText(documentSnapshot.getString("nome"));

                    }
                });


    }
    }




