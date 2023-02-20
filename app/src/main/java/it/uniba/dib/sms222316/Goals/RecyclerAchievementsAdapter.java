package it.uniba.dib.sms222316.Goals;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import it.uniba.dib.sms222316.R;

public class RecyclerAchievementsAdapter extends RecyclerView.Adapter<RecyclerAchievementsAdapter.MyViewHolder>{


    private final List<Achievements> mData;

    public RecyclerAchievementsAdapter(List<Achievements> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.achievement_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAchievementsAdapter.MyViewHolder holder, int position) {
        Uri uri = Uri.parse("android.resource://it.uniba.dib.sms222316/drawable/"+mData.get(position).getRelic().replaceAll("\\s+", "_").toLowerCase());

        holder.TargetPoint.setText(mData.get(position).getTargetPoint());
        holder.Relic.setText(mData.get(position).getRelic());
        holder.img.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TargetPoint, Relic;
        CardView cardView ;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.CardAchievements);
            TargetPoint = itemView.findViewById(R.id.target) ;
            Relic = itemView.findViewById(R.id.relic);
            img = itemView.findViewById(R.id.bg);
        }
    }
}
