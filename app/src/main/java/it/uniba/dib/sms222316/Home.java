package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Home extends AppCompatActivity {
    Button Play, usrbtn;
    ImageButton Settings;
    MediaPlayer main_ost,effect;
    SwitchCompat mVolume,eVolume;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    EditText usrtext;
    TextView profilename;
    private long backPressed;
    private static final int TIME_INTERVALL = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_home);
        //popup username
        PopupDialog popupDialog = new PopupDialog(Home.this, Home.this);
        popupDialog.setCanceledOnTouchOutside(false);
        //Todo : bug quando
        //
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        String Accountstring;
        if(account != null) Accountstring = account.getEmail();
        else Accountstring = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        CardView Gallery = findViewById(R.id.Gallery);
        Gallery.setOnClickListener(v -> {
            Intent i = new Intent(Home.this, Gallery.class);
            startActivity(i);
            finish();
        });

        profilename = findViewById(R.id.profile_name);


        usrtext = popupDialog.findViewById(R.id.username_text);
        usrbtn = popupDialog.findViewById(R.id.submitUserButton);
        usrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map Us = new HashMap<>();
                Us.put("email", Accountstring);
                Us.put("nome",usrtext.getText().toString());
                Us.put("friend_code",getRandomHexString(5));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(getRandomHexString(10)).set(Us)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //aggiunto
                                showToast(Home.this, "aggiunto");
                                findusername(Accountstring);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast(Home.this, "non aggiunto");

                            }
                        });
                popupDialog.dismiss();
            }
        });


        Settings = findViewById(R.id.settings);
        PopupSettings popupSettings = new PopupSettings(Home.this,Home.this);

        //main ost
        main_ost = MediaPlayer.create(this, R.raw.electricpistol);
        effect = MediaPlayer.create(this, R.raw.sound);
        mVolume=popupSettings.findViewById(R.id.vol_musica);
        eVolume=popupSettings.findViewById(R.id.vol_effetti);

        if(mVolume.isChecked()){
            popupSettings.SoundSwitchM();
        }


        //ricerca dell'utente



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", Accountstring)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            showToast(Home.this , "trovato");
                        } else {
                            showToast(Home.this , "Non trovato");
                            //APRIRE popup

                            popupDialog.show();



                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast(Home.this , "errore db");
                    }
                });



        Play = findViewById(R.id.button);
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eVolume.isChecked()) {
                    effect.start();
                }
            }
        });


        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //APRIRE popup impostazioni
                popupSettings.show();
            }
        });
        findusername(Accountstring);
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
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            profilename.setText(documentSnapshot.getString("nome")+"#"+documentSnapshot.getString("friend_code"));

                        }

                    }
                });


    }

}
