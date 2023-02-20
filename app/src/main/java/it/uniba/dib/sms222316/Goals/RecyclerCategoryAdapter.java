package it.uniba.dib.sms222316.Goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        List<Achievements> fullHrg, data;
        fullHrg = new ArrayList<>();
        if(cData.get(position).getCategory().equals("Start")){
            fullHrg.add(new Achievements("10","Start", "Il Colosseo"));
            fullHrg.add(new Achievements("100","Start", "Il Colosseo"));
            fullHrg.add(new Achievements("250","Start", "Il Colosseo"));
            fullHrg.add(new Achievements("500","Start", "Il Colosseo"));
            fullHrg.add(new Achievements("1000","Start", "Il Colosseo"));
            fullHrg.add(new Achievements("2500","Start", "Il Colosseo"));
        }else{
            fullHrg.add(new Achievements("10","Vittorie", "Il Colosseo"));
        }
        data = new ArrayList<>(fullHrg);
        RecyclerView myrv = holder.Achievement;

        RecyclerAchievementsAdapter myAdapter = new RecyclerAchievementsAdapter(data);
        myrv.setLayoutManager(new LinearLayoutManager(Padre.getContext(),LinearLayoutManager.HORIZONTAL,false));
        myrv.setAdapter(myAdapter);



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
