package it.uniba.dib.sms222316.Goals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222316.Gallery.Heritage;
import it.uniba.dib.sms222316.Gallery.RecyclerViewAdapter;
import it.uniba.dib.sms222316.R;

public class FragmentAchievements extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Category> Category, data;
        Category = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Heritage");
        Query query = reference.orderBy("Title", Query.Direction.ASCENDING);

        /*query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Category.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {

                    Category.add(new Category(document.getReference("Name") , document.getString("Description") , document.getString("Type") , document.getString("Image") ));

                }
                List<Heritage> data = new ArrayList<>(Category);

                DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                float RecyclerWidth = (displayMetrics.widthPixels / displayMetrics.density) - 300; //larghezza sezione bottoni
                int spanCount = (int) (RecyclerWidth / 100) - 1;

                data = new ArrayList<>(Category);

                RecyclerView myrv = view.findViewById(R.id.CategoryRecycler);
                RecyclerCategoryAdapter myAdapter = new RecyclerCategoryAdapter(data);
                myrv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                myrv.setAdapter(myAdapter);


            } else {
                Log.e("Query-Gallery", "Not found query");

            }
        });*/


        Category.add(new Category("Vittorie"));
        Category.add(new Category("Start"));
        Category.add(new Category("buh"));


    }
}