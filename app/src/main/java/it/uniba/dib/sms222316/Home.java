package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    Button Option, usrbtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    EditText usrtext;
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
        //
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        CardView Gallery = findViewById(R.id.Gallery);
        Gallery.setOnClickListener(v -> {
            Intent i = new Intent(Home.this, Gallery.class);
            startActivity(i);
            finish();
        });


        usrtext = popupDialog.findViewById(R.id.username_text);
        usrbtn = popupDialog.findViewById(R.id.submitUserButton);
        usrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map Us = new HashMap<>();
                Us.put("email", account.getEmail());
                Us.put("nome",usrtext.getText().toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document("LA").set(Us)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //aggiunto
                                showToast(Home.this, "aggiunto");
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



        //ricerca dell'utente



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", account.getEmail())
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
}