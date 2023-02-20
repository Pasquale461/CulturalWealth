package it.uniba.dib.sms222316.Gallery;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.*;

import it.uniba.dib.sms222316.R;

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



            holder.haritageTitle.setText(mData.get(position).getTitle());

            File file;
            Bitmap bitmap;
            holder.type.setContentDescription(mData.get(position).getType());

            TypeHeritage control = TypeHeritage.valueOf(mData.get(position).getType());

            switch (control){
                case Monuments:
                    holder.type.setBackgroundResource(R.drawable.monument);
                     file = new File(mContext.getFilesDir() ,"CulturalWealth/Monuments/" + mData.get(position).getPic());
                     bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                    break;
                case Paintings:
                    holder.type.setBackgroundResource(R.drawable.painting);
                    file = new File(mContext.getFilesDir() ,"CulturalWealth/Paintings/" + mData.get(position).getPic());
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                    break;
                case Characters:
                    holder.type.setBackgroundResource(R.drawable.character);
                    file = new File(mContext.getFilesDir() ,"CulturalWealth/ProfilesPictures/" + mData.get(position).getPic());
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                    break;
            }


            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(mContext, GalleryHeritage.class);

                // passing data to the Gallery Heritage
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Type",mData.get(position).getType());
                intent.putExtra("Image",mData.get(position).getPic());

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
