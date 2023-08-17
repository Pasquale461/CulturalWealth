package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.uniba.dib.sms222316.Goals.GoalsPopup;
import it.uniba.dib.sms222316.Rank.ranking_popup;

public class Home extends AppCompatActivity {
    Button Play, usrbtn, rankbutton, Disconnect;
    ImageButton Settings,italianButton,englishButton;
    MediaPlayer effect;
    SwitchCompat mVolume,eVolume;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    EditText usrtext;
    Audio audio;
    PopupSettings popupSettings;
    TextView profilename;
    ArrayList<String> Owned = new ArrayList<>();
    private long backPressed;
    private static final int TIME_INTERVALL = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        LanguageManager lang = new LanguageManager(this);
        lang.ChangeLanguage(lang.GetLang());
        audio = Audio.getInstance(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }




        //popup classifica
        ranking_popup ranking_popup = new ranking_popup(Home.this);
        Window window = ranking_popup.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int WidthPixelrank = (displayMetrics.widthPixels);
        int HeightPixelrank = (displayMetrics.heightPixels);
        window.setLayout((int) (WidthPixelrank * 0.90), (int) (HeightPixelrank * 0.85));
        window.setGravity(Gravity.CENTER);


        //popup username
        PopupDialog popupDialog = new PopupDialog(Home.this, Home.this);
        popupDialog.setCanceledOnTouchOutside(false);


        //
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        String Accountstring;
        if(account != null) Accountstring = account.getEmail();
        else Accountstring = FirebaseAuth.getInstance().getCurrentUser().getEmail(); //Controllo dell'errore




        //TO-DO Player data upload
        //TO-DO LOAD POSSEDUTI





        //Open Popup Goals
        Button goalsBtn = findViewById(R.id.goals_btn);
        GoalsPopup goalsPopup = new GoalsPopup(Home.this);

        displayMetrics = this.getResources().getDisplayMetrics();
        int WidthPixel = (displayMetrics.widthPixels);
        int HeightPixel = (displayMetrics.heightPixels);
        goalsBtn.setOnClickListener(v -> {
            Window goals = goalsPopup.getWindow();
            goals.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            goals.setLayout((int) (WidthPixel * 0.85), (int) (HeightPixel * 0.95));
            goals.setGravity(Gravity.CENTER);
            goalsPopup.show();
        });

        //Close Popup Goals
        Button closer = goalsPopup.findViewById(R.id.closerGoals);
        closer.setOnClickListener(view -> goalsPopup.hide());

        profilename = findViewById(R.id.profile_name);

        rankbutton = findViewById(R.id.rankbutton);
        usrtext = popupDialog.findViewById(R.id.username_text);
        usrbtn = popupDialog.findViewById(R.id.submitUserButton);

        //open gallery
        CardView Gallery = findViewById(R.id.Gallery);
        Gallery.setOnClickListener(v -> {
            loadprop();

        });
        usrbtn.setOnClickListener(v -> {

            //FirebaseFirestore db = FirebaseFirestore.getInstance();
            List<DocumentReference> posseduti = new ArrayList<DocumentReference>();
            DocumentReference def = db.collection("Heritage").document();
           // def.set("Heritage/GalileoGalilei");
            posseduti.add(def);
            Map Us = new HashMap<>();
            Us.put("email", Accountstring);
            Us.put("nome",usrtext.getText().toString());
            Us.put("friend_code",getRandomHexString(5));
            //Us.put("Posseduti",posseduti);
            DocumentReference Defaultpg;

            Defaultpg = db.collection("Heritage").document("Cleopatra");
            List<DocumentReference> referenceArray = new ArrayList<>();
            referenceArray.add(Defaultpg);
            Us.put("Posseduti",referenceArray);
            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(Us) //NULL POINTER

                    .addOnSuccessListener(aVoid -> {
                        //aggiunto
                        showToast(Home.this, "aggiunto");
                        findusername(Accountstring);

                    })
                    .addOnFailureListener(e ->
                            showToast(Home.this, "non aggiunto"));
                    popupDialog.dismiss();
        });


        rankbutton.setOnClickListener(view -> {
            ranking_popup.show();
        });
        closer = ranking_popup.findViewById(R.id.closerankpopup);
        closer.setOnClickListener(view -> ranking_popup.hide());

        //Settings
        Settings = findViewById(R.id.settings);
        popupSettings = new PopupSettings(Home.this,Home.this);

        //main ost
        effect = MediaPlayer.create(this, R.raw.sound);
        mVolume=popupSettings.findViewById(R.id.vol_musica);
        eVolume=popupSettings.findViewById(R.id.vol_effetti);

        if(mVolume.isChecked()){
            popupSettings.SoundSwitchM();
        }




        //ricerca dell'utente
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", Accountstring)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {

                    } else {
                        //APRIRE popup
                        popupDialog.show();
                    }
                })
                .addOnFailureListener(e -> showToast(Home.this , "errore db"));


        //creo instanza popupsettings
        Settings = findViewById(R.id.settings);
        popupSettings = new PopupSettings(Home.this,Home.this);


        //Cambio lingua in italiano
        italianButton = popupSettings.findViewById(R.id.lingua_it);
        italianButton.setOnClickListener(view -> {
            lang.ChangeLanguage("it");
            recreate();
            popupSettings.dismiss();

        });

        //Cambio lingua in inglese
        englishButton = popupSettings.findViewById(R.id.lingua_en);
        englishButton.setOnClickListener(view -> {
            lang.ChangeLanguage("en");
            recreate();
            popupSettings.dismiss();

        });

        //Bottone Della disconnessione
        Disconnect = popupSettings.findViewById(R.id.disconnetti);
        Disconnect.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
        });

        //Gestione ost del gioco
        effect = MediaPlayer.create(this, R.raw.sound);
        mVolume=popupSettings.findViewById(R.id.vol_musica);
        eVolume=popupSettings.findViewById(R.id.vol_effetti);



        Play = findViewById(R.id.play);
        Play.setOnClickListener(v -> {
            if(eVolume.isChecked()) {
                effect.start();
            }
        });


        Settings.setOnClickListener(v -> {
            //APRIRE popup impostazioni
            popupSettings.show();
        });

        //Close Popup Setting
        closer = popupSettings.findViewById(R.id.esci);
        closer.setOnClickListener(view -> popupSettings.hide());

        findusername(Accountstring);

        Button play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, GameActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        audio.pauseAudio();
    }
    public void onBackPressed() {
        if(backPressed + TIME_INTERVALL > System.currentTimeMillis()){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit app", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

    private void findusername(String Accountstring)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", Accountstring)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        profilename.setText(documentSnapshot.getString("nome")+"#"+documentSnapshot.getString("friend_code"));

                    }
                });


    }

    public void loadprop(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference OwnRef = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        OwnRef.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                ArrayList<DocumentReference> OwnList = (ArrayList<DocumentReference>) task1.getResult().get("Posseduti");
                OwnList.forEach(ownList -> {
                    ownList.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {

                            DocumentSnapshot ownTitle = task2.getResult();
                            Owned.add(ownTitle.get("Title").toString());
                            Intent i = new Intent(Home.this, it.uniba.dib.sms222316.Gallery.Gallery.class);
                            i.putExtra("Owned", Owned);
                            startActivity(i);
                        }
                    });
                });


            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVolume.isChecked()){
            popupSettings.SoundSwitchM();
        }
    }
}

