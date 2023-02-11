package it.uniba.dib.sms222316;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        Context mContext;
        List<Heritage> mData;

        private enum TypeHeritage {
            Monuments, Paintings, Characters
        }

        public RecyclerViewAdapter(Context mContext, List<Heritage> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.cardview,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            Uri uri = Uri.parse("android.resource://it.uniba.dib.sms222316/drawable/"+mData.get(position).getTitle().replaceAll("\\s+", "_").toLowerCase());

            holder.haritageTitle.setText(mData.get(position).getTitle());
            holder.img.setImageURI(uri);
            holder.type.setContentDescription(mData.get(position).getType());

            TypeHeritage control = TypeHeritage.valueOf(mData.get(position).getType());

            switch (control){
                case Monuments:
                    holder.type.setBackgroundResource(R.drawable.monument);
                    break;
                case Paintings:
                    holder.type.setBackgroundResource(R.drawable.painting);
                    break;
                case Characters:
                    holder.type.setBackgroundResource(R.drawable.character);
                    break;
            }


            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(mContext,GalleryHeritage.class);

                // passing data to the Gallery Heritage
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                // start the activity
                mContext.startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView haritageTitle;
            CardView cardView ;
            ImageView img, type;

            public MyViewHolder(View itemView) {
                super(itemView);

                haritageTitle = itemView.findViewById(R.id.title) ;
                cardView = itemView.findViewById(R.id.cardview_id);
                img = itemView.findViewById(R.id.idImg);
                type = itemView.findViewById(R.id.Type);
            }
        }

    }
