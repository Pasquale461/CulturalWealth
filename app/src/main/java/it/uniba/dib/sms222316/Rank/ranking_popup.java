package it.uniba.dib.sms222316.Rank;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
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


    public ranking_popup(Context context ,Context context1) {
        super(context);

        setContentView(R.layout.ranking_popup);

        Log.d("TAG", "Questo è un messaggio di debug");




        String empty = "AdaLovelace.png";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Users");
        Query query = reference.orderBy("email", Query.Direction.ASCENDING);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if(document.get("ProfilePic") == null)
                    {
                        fullHrg.add(new Utente(document.getString("nome"), "" , "1000", empty));
                    }
                    else {
                        Log.d("documento" , document.getDocumentReference("ProfilePic").getPath());
                        DocumentReference userRef = db.document(document.getDocumentReference("ProfilePic").getPath());


                        // Utilizziamo la reference per prendere il nome dell'utente
                        userRef.get().addOnSuccessListener(documentSnapshot -> {
                            fullHrg.add(new Utente(document.getString("nome"), "" , "1000", documentSnapshot.getString("Image")));
                            Log.d("documento" , documentSnapshot.getString("Image"));

                            data = new ArrayList<>(fullHrg);

                            RecyclerView myrv = findViewById(R.id.recicler_ranking);


                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getContext().getResources().getDisplayMetrics();
                            float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                            int spanCount = (int) (RecyclerWidth / 100) - 1;


                            RecyclerViewUtente myAdapter = new RecyclerViewUtente(context, data);

                            myrv.setLayoutManager(new LinearLayoutManager(context));

                            myrv.setAdapter(myAdapter);
                        }).addOnFailureListener(e -> Log.d("documento" , "AAAAA"));
                    }
                    for (Utente element : fullHrg) {
                        Log.d("documentos" , element.getName()+"  "+element.getProfilePic());
                    }




                }
            } else {
                Log.e("Query-ranking", "Not found query");

            }


        });

    }


}