package it.uniba.dib.sms222316;


import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ranking_popup extends Dialog {

    private EditText mEditText;
    private Button mButton;
    private static final int TIME_INTERVALL = 2000;
    private long backPressed;


    public ranking_popup(Context context ,Context context1) {
        super(context);

        setContentView(R.layout.ranking_popup);

        Log.d("TAG", "Questo è un messaggio di debug");
        ArrayList<Utente> data, fullHrg;
        fullHrg = new ArrayList<>();

        fullHrg.add(new Utente("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Utente("Galileo Galilei","fdgxc","Characters"));
        fullHrg.add(new Utente("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Utente("Galileo Galilei","fdgxc","Characters"));
        fullHrg.add(new Utente("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Utente("Galileo Galilei","fdgxc","Characters"));
        fullHrg.add(new Utente("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Utente("Galileo Galilei","fdgxc","Characters"));
        data = new ArrayList<Utente>(fullHrg);
        Log.d("TAG", "2");

        RecyclerView myrv = findViewById(R.id.recicler_ranking);

        Log.d("TAG", "3");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getContext().getResources().getDisplayMetrics();
        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
        int spanCount = (int) (RecyclerWidth / 100) - 1;
        Log.d("TAG", "4");

        RecyclerViewUtente myAdapter = new RecyclerViewUtente(context, data);

        myrv.setLayoutManager(new LinearLayoutManager(context));

        myrv.setAdapter(myAdapter);
        /*
        Log.d("TAG", "5");

         */
    }
}