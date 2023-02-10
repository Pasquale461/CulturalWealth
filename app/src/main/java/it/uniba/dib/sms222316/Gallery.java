package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {
    ImageButton btnBack;
    Button btnMonuments, btnPaintings, btnCharacters;
    enum heritage {
            Monuments, Paintings, Characters, All};

    heritage pressed = heritage.All;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_gallery);

        List<Heritage> data, fullHrg;

        btnBack = findViewById(R.id.btnBack);
        btnMonuments = findViewById(R.id.btnMonuments);
        btnPaintings = findViewById(R.id.btnPaintings);
        btnCharacters = findViewById(R.id.btnCharacters);

        //Button back
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Gallery.this, Home.class);
            startActivity(i);
            finish();
        });

        fullHrg = new ArrayList<>();
        data = new ArrayList<>();
        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));

        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));

        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));

        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));

        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));

        fullHrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
        fullHrg.add(new Heritage("La Gioconda","sdxgfc","Paintings"));
        fullHrg.add(new Heritage("Galileo Galilei","fdgxc","Characters"));
        data.addAll(fullHrg);

        RecyclerView myrv = findViewById(R.id.RecyclerView);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
        int spanCount = (int) (RecyclerWidth / 100) - 1;

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
        myrv.setAdapter(myAdapter);



        btnMonuments.setOnClickListener(v -> {
            if(pressed != heritage.Monuments){
                data.clear();
                List<Heritage> hrg;
                hrg = new ArrayList<>();
                hrg.add(new Heritage("Il Colosseo","gsdx","Monuments"));
                data.addAll(hrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.Monuments;

                btnMonuments.setBackgroundResource(R.drawable.pressed);
                btnPaintings.setBackgroundResource(R.drawable.defaultbtn);
                btnCharacters.setBackgroundResource(R.drawable.defaultbtn);
            }
            else{
                data.clear();
                data.addAll(fullHrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.All;

                btnMonuments.setBackgroundResource(R.drawable.defaultbtn);
            }
        });

        btnPaintings.setOnClickListener(v -> {
            if(pressed != heritage.Paintings){
                data.clear();
                List<Heritage> hrg;
                hrg = new ArrayList<>();
                hrg.add(new Heritage("La Gioconda","fdvf","Paintings"));
                data.addAll(hrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.Paintings;

                btnPaintings.setBackgroundResource(R.drawable.pressed);
                btnMonuments.setBackgroundResource(R.drawable.defaultbtn);
                btnCharacters.setBackgroundResource(R.drawable.defaultbtn);
            }
            else{
                data.clear();
                data.addAll(fullHrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.All;

                btnPaintings.setBackgroundResource(R.drawable.defaultbtn);
            }
        });

        btnCharacters.setOnClickListener(v -> {
            if(pressed != heritage.Characters){
                data.clear();
                List<Heritage> hrg;
                hrg = new ArrayList<>();
                hrg.add(new Heritage("Galileo Galilei","vffre","Characters"));
                data.addAll(hrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.Characters;

                btnCharacters.setBackgroundResource(R.drawable.pressed);
                btnMonuments.setBackgroundResource(R.drawable.defaultbtn);
                btnPaintings.setBackgroundResource(R.drawable.defaultbtn);
            }
            else{
                data.clear();
                data.addAll(fullHrg);
                myAdapter.notifyDataSetChanged();
                pressed = heritage.All;

                btnCharacters.setBackgroundResource(R.drawable.defaultbtn);
            }
        });
    }


}