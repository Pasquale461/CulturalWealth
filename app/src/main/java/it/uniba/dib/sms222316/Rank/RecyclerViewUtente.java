package it.uniba.dib.sms222316.Rank;

import android.content.Context;
import android.util.Log;
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
import it.uniba.dib.sms222316.Utente;

public class RecyclerViewUtente extends RecyclerView.Adapter<RecyclerViewUtente.MyViewHolderUser>  {
    Context uContext;
    List<Utente> uData;

    private enum TypeHeritage {
        Monuments, Paintings, Characters
    }

    public RecyclerViewUtente(Context uContext, List<Utente> uData) {
        this.uContext = uContext;
        this.uData = uData;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(uContext);
        view = mInflater.inflate(R.layout.card_utente,parent,false);
        return new MyViewHolderUser(view);
    }



    public void onBindViewHolder(MyViewHolderUser holder, final int position) {

        //Uri uri = Uri.parse("android.resource://it.uniba.dib.sms222316/drawable/"+uData.get(position).getName().replaceAll("\\s+", "_").toLowerCase());

        holder.User_name.setText(uData.get(position).getName());
        holder.img.setImageURI(uData.get(position).getProfileUri());
        Log.d("image", uData.get(position).getProfileUri().toString());
        holder.points.setText(uData.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        return uData.size();
    }

    public static class MyViewHolderUser extends RecyclerView.ViewHolder {

        TextView User_name, points;
        CardView cardView ;
        ImageView img;

        public MyViewHolderUser(View itemView) {
            super(itemView);

            User_name = itemView.findViewById(R.id.user_name) ;
            cardView = itemView.findViewById(R.id.card_utente);
            img = itemView.findViewById(R.id.user_icon);
            points = itemView.findViewById(R.id.user_score);
        }
    }

}
