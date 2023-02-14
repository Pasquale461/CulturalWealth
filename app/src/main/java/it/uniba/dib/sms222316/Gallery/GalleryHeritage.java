package it.uniba.dib.sms222316.Gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.dib.sms222316.R;

public class GalleryHeritage extends AppCompatActivity {

    ImageButton btnBack;
    ImageView img;
    TextView title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_gallery_heritage);

        btnBack = findViewById(R.id.btnBack);


        //Button back
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(GalleryHeritage.this, Gallery.class);
            startActivity(i);
            finish();
        });

        Uri uri = Uri.parse("android.resource://it.uniba.dib.sms222316/drawable/"+getIntent().getExtras().getString("Title").replaceAll("\\s+", "_").toLowerCase());

        img = findViewById(R.id.imgHeritage);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        String tl = getIntent().getExtras().getString("Title");
        String desc = getIntent().getExtras().getString("Description");

        img.setImageURI(uri);
        title.setText(tl);
        description.setMovementMethod(new ScrollingMovementMethod());
        description.setText(desc);


    }
}