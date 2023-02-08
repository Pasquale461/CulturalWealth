package it.uniba.dib.sms222316;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Gallery extends AppCompatActivity {
    ImageButton btnBack;
    Button btnMonuments, btnPaintings, btnCharacters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_gallery);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnMonuments = (Button) findViewById(R.id.btnMonuments);
        btnPaintings = (Button) findViewById(R.id.btnPaintings);
        btnCharacters = (Button) findViewById(R.id.btnCharacters);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Gallery.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        btnMonuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView1 = findViewById(R.id.cardPaintings1);
                CardView cardView2 = findViewById(R.id.cardCharacters1);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
            }
        });

        btnPaintings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView1 = findViewById(R.id.cardMonuments1);
                CardView cardView2 = findViewById(R.id.cardCharacters1);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
            }
        });

        btnCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView1 = findViewById(R.id.cardMonuments1);
                CardView cardView2 = findViewById(R.id.cardPaintings1);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
            }
        });
    }
}