package it.uniba.dib.sms222316.Rank;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import it.uniba.dib.sms222316.Gallery.Heritage;
import it.uniba.dib.sms222316.R;
import it.uniba.dib.sms222316.Utente;

public class ranking_popup extends Dialog {

    private EditText mEditText;
    private Button mButton;
    private static final int TIME_INTERVALL = 2000;
    private long backPressed;
    ArrayList<Utente> data, fullHrg = new ArrayList<>();


    public ranking_popup(Context context) {
        super(context);
       // context.setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.ranking_popup);



        String empty = "AdaLovelace.png";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Users");
        Query query = reference.orderBy("points", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (!document.getId().equals("Guest")) {
                        if (document.get("Profilepic") == null) {
                            fullHrg.add(new Utente(document.getString("nome"), "", document.getLong("points"), empty));

                        } else {
                            DocumentReference userRef = db.document(document.getDocumentReference("Profilepic").getPath());


                            // Utilizziamo la reference per prendere il nome dell'utente
                            userRef.get().addOnSuccessListener(documentSnapshot -> {
                                fullHrg.add(new Utente(document.getString("nome"), "", document.getLong("points"), documentSnapshot.getString("Image")));
                                Log.d("documento", documentSnapshot.getString("Image"));


                            }).addOnFailureListener(e -> Log.d("documento", "AAAAA"));
                        }
                    }
                }
                    data = new ArrayList<>(fullHrg);

                    RecyclerView myrv = findViewById(R.id.recicler_ranking);


                    /*DisplayMetrics displayMetrics = new DisplayMetrics();
                    getContext().getResources().getDisplayMetrics();
                    float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                    int spanCount = (int) (RecyclerWidth / 100) - 1;*/

                    RecyclerViewUtente myAdapter = new RecyclerViewUtente(context, data);

                    myrv.setLayoutManager(new LinearLayoutManager(context));

                    myrv.setAdapter(myAdapter);
                    for (Utente element : fullHrg) {
                        Log.d("documentoss", element.getName() + "  " + element.getProfilePic());
                    }

            } else {
                Log.e("Query-ranking", "Not found query");

            }


        });

    }


}