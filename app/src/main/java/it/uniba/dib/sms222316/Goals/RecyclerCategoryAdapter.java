package it.uniba.dib.sms222316.Goals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.uniba.dib.sms222316.R;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.MyViewHolder>{

    private final List<Category> cData;
    private ViewGroup Padre;

    public RecyclerCategoryAdapter(List<Category> cData) {
        this.cData = cData;
    }

    @NonNull
    @Override
    public RecyclerCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.category_card,parent,false);
        this.Padre = parent;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCategoryAdapter.MyViewHolder holder, int position) {
        //Set nome e container Category
        holder.category.setText(cData.get(position).getCategory());

        //Set recycler Achievements
        List<Achievements> goals;
        goals = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Achievements");
        Query query = reference.whereEqualTo(FieldPath.documentId(), cData.get(position).getCategory()).orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                goals.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Map<String, Object> map = document.getData();

                    for(Map.Entry<String, Object> entry : map.entrySet()){
                        Map<String, Object> obb = (Map<String, Object>) entry.getValue();

                        if(!obb.isEmpty()){
                            DocumentReference reward = (DocumentReference) obb.get("Reward");
                            reward.get().addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    DocumentSnapshot rew = task1.getResult();
                                    goals.add(new Achievements(obb.get("Target").toString(), obb.get("Name").toString(),rew.getString("Image")));
                                    Collections.reverse(goals); //TODO BISOGNA ORDINARLO PER TARGET
                                    List<Achievements> data = new ArrayList<>(goals);
                                    RecyclerView myrv = holder.Achievement;

                                    RecyclerAchievementsAdapter myAdapter = new RecyclerAchievementsAdapter(data);
                                    myrv.setLayoutManager(new LinearLayoutManager(Padre.getContext(), LinearLayoutManager.HORIZONTAL, false));
                                    myrv.setAdapter(myAdapter);

                                }
                            });

                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category ;
        RecyclerView Achievement;

        public MyViewHolder(View itemView) {
            super(itemView);

            Achievement = itemView.findViewById(R.id.AchievementsRecycler);
            category = itemView.findViewById(R.id.CategoryName);
        }
    }

}
