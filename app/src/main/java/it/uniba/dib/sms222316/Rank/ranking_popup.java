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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.Executor;

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




        Uri empty = Uri.parse("https://firebasestorage.googleapis.com/v0/b/cultural-wealth.appspot.com/o/ProfilesPictures%2FCristoforo%20colombo.png?alt=media&token=3ebfb3ce-f70e-4a74-b2ce-1784e71feae1");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Users");
        Query query = reference.orderBy("email", Query.Direction.ASCENDING);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {


                    fullHrg.add(new Utente(document.getString("nome"), "" , "1000", empty));

                }
            } else {
                Log.e("Query-ranking", "Not found query");

            }
            data = new ArrayList<>(fullHrg);

            RecyclerView myrv = findViewById(R.id.recicler_ranking);


            DisplayMetrics displayMetrics = new DisplayMetrics();
            getContext().getResources().getDisplayMetrics();
            float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
            int spanCount = (int) (RecyclerWidth / 100) - 1;


            RecyclerViewUtente myAdapter = new RecyclerViewUtente(context, data);

            myrv.setLayoutManager(new LinearLayoutManager(context));

            myrv.setAdapter(myAdapter);

        });

    }


}