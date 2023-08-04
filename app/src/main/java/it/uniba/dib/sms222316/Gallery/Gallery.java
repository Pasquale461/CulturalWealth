package it.uniba.dib.sms222316.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222316.Home;
import it.uniba.dib.sms222316.PopupSettings;
import it.uniba.dib.sms222316.R;

public class Gallery extends AppCompatActivity {
    ImageButton btnBack;
    Button btnMonuments, btnPaintings, btnCharacters;
    PopupSettings popupSettings; //Gestione audio
    SwitchCompat mVolume;
    enum heritage {
            Monuments, Paintings, Characters, All}
    Query query;

    heritage pressed = heritage.All;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_gallery);

        List<Heritage>  fullHrg;
        popupSettings = new PopupSettings(Gallery.this,Gallery.this);
        mVolume=popupSettings.findViewById(R.id.vol_musica);
        btnBack = findViewById(R.id.btnBack);
        btnMonuments = findViewById(R.id.btnMonuments);
        btnPaintings = findViewById(R.id.btnPaintings);
        btnCharacters = findViewById(R.id.btnCharacters);
        ArrayList Owned = (ArrayList) getIntent().getSerializableExtra("Owned");//TO-Do OGGETTO PLAYER NON SOLO ARRAY POSSEDUTI

        //Button back
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Gallery.this, Home.class);
            startActivity(i);
            finish();
        });


        fullHrg = new ArrayList<>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        CollectionReference reference = db.collection("Heritage");
        query = reference.orderBy("Title", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                fullHrg.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title = document.getString("Title");
                    boolean own = Owned.contains(title);
                    fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));

                }
                List<Heritage> data = new ArrayList<>(fullHrg);

                RecyclerView myrv = findViewById(R.id.RecyclerView);

                DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                int spanCount = (int) (RecyclerWidth / 100) - 1;

                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                myrv.setAdapter(myAdapter);


            } else {
                Log.e("Query-Gallery", "Not found query");

            }
        });


        btnMonuments.setOnClickListener(v -> {
            if(pressed != heritage.Monuments){

                fullHrg.clear();

                Query query = reference.orderBy("Title", Query.Direction.ASCENDING).whereEqualTo("Type" , "Monuments");
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));



                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);
                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.Monuments;
                        btnMonuments.setBackgroundResource(R.drawable.pressed);
                        btnPaintings.setBackgroundResource(R.drawable.defaultbtn);
                        btnCharacters.setBackgroundResource(R.drawable.defaultbtn);



                    } else {
                        Log.e("Query-Gallery", "Not found query");
                    }


                });

            }
            else{
                fullHrg.clear();
                query = reference.orderBy("Title", Query.Direction.ASCENDING);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));


                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);

                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.All;
                        btnMonuments.setBackgroundResource(R.drawable.defaultbtn);


                    } else {
                        Log.e("Query-Gallery", "Not found query");

                    }


                });



            }
        });

        btnPaintings.setOnClickListener(v -> {
            if(pressed != heritage.Paintings){
                fullHrg.clear();

                Query query = reference.orderBy("Title", Query.Direction.ASCENDING).whereEqualTo("Type" , "Paintings");
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));

                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);
                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.Paintings;
                        btnPaintings.setBackgroundResource(R.drawable.pressed);
                        btnMonuments.setBackgroundResource(R.drawable.defaultbtn);
                        btnCharacters.setBackgroundResource(R.drawable.defaultbtn);



                    } else {
                        Log.e("Query-Gallery", "Not found query");
                    }


                });

            }
            else{
                fullHrg.clear();
                query = reference.orderBy("Title", Query.Direction.ASCENDING);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));

                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);

                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.All;
                        btnPaintings.setBackgroundResource(R.drawable.defaultbtn);


                    } else {
                        Log.e("Query-Gallery", "Not found query");

                    }


                });

            }
        });

        btnCharacters.setOnClickListener(v -> {
            if(pressed != heritage.Characters){


                fullHrg.clear();

                Query query = reference.orderBy("Title", Query.Direction.ASCENDING).whereEqualTo("Type" , "Characters");
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));

                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);
                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.Characters;
                        btnCharacters.setBackgroundResource(R.drawable.pressed);
                        btnMonuments.setBackgroundResource(R.drawable.defaultbtn);
                        btnPaintings.setBackgroundResource(R.drawable.defaultbtn);



                    } else {
                        Log.e("Query-Gallery", "Not found query");
                    }


                });


            }
            else{
                fullHrg.clear();
                query = reference.orderBy("Title", Query.Direction.ASCENDING);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String title = document.getString("Title");
                            boolean own = Owned.contains(title);
                            fullHrg.add(new Heritage(title , document.getString("Description") , document.getString("Type") , document.getString("Image"), own));

                        }
                        List<Heritage> data = new ArrayList<>(fullHrg);

                        RecyclerView myrv = findViewById(R.id.RecyclerView);

                        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                        float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                        int spanCount = (int) (RecyclerWidth / 100) - 1;

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,data);
                        myrv.setLayoutManager(new GridLayoutManager(this,spanCount));
                        myrv.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        pressed = heritage.All;
                        btnCharacters.setBackgroundResource(R.drawable.defaultbtn);


                    } else {
                        Log.e("Query-Gallery", "Not found query");

                    }


                });



            }
        });
    }
    protected void onResume() {
        super.onResume();
        if(mVolume.isChecked()){
            popupSettings.SoundSwitchM();
        }
    }

}