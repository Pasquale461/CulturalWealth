package it.uniba.dib.sms222316.Gallery;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.util.Assert;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;

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

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                    isUnlocked(mData.get(position).getTitle()).thenAccept(result -> {
                        Log.d("result" , result.toString());
                                if (!result) {
                                    // Creare la matrice di colori per la modifica della saturazione
                                    ColorMatrix matrix = new ColorMatrix();
                                    matrix.setSaturation(0.1f); // Impostare la saturazione a 50%

                                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                                    Paint paint = new Paint();
                                    paint.setColorFilter(filter);

                                    Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(newImage);
                                    canvas.drawBitmap(bitmap, 0, 0, paint);
                                    holder.img.setImageBitmap(newImage);
                                } else {
                                    holder.img.setImageBitmap(bitmap);
                                }
                            });

                    break;
                case Paintings:
                    holder.type.setBackgroundResource(R.drawable.painting);
                    file = new File(mContext.getFilesDir() ,"CulturalWealth/Paintings/" + mData.get(position).getPic());
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    isUnlocked(mData.get(position).getTitle()).thenAccept(result -> {
                        Log.d("result" , result.toString());
                        if (!result) {
                            // Creare la matrice di colori per la modifica della saturazione
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0.1f); // Impostare la saturazione a 50%

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                            Paint paint = new Paint();
                            paint.setColorFilter(filter);

                            Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(newImage);
                            canvas.drawBitmap(bitmap, 0, 0, paint);
                            holder.img.setImageBitmap(newImage);
                        } else {
                            holder.img.setImageBitmap(bitmap);
                        }
                    });
                    break;
                case Characters:
                    holder.type.setBackgroundResource(R.drawable.character);
                    file = new File(mContext.getFilesDir() ,"CulturalWealth/ProfilesPictures/" + mData.get(position).getPic());
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    isUnlocked(mData.get(position).getTitle()).thenAccept(result -> {
                        Log.d("result" , result.toString());
                        if (!result) {
                            // Creare la matrice di colori per la modifica della saturazione
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0.1f); // Impostare la saturazione a 50%

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                            Paint paint = new Paint();
                            paint.setColorFilter(filter);

                            Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(newImage);
                            canvas.drawBitmap(bitmap, 0, 0, paint);
                            holder.img.setImageBitmap(newImage);
                        } else {
                            holder.img.setImageBitmap(bitmap);
                        }
                    });
                    break;
            }


            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(mContext, GalleryHeritage.class);
                isUnlocked(mData.get(position).getTitle()).thenAccept(result ->
                {
                    if(result)
                    {
                        intent.putExtra("Title",mData.get(position).getTitle());
                        intent.putExtra("Description",mData.get(position).getDescription());
                        intent.putExtra("Type",mData.get(position).getType());
                        intent.putExtra("Image",mData.get(position).getPic());


                        // start the activity
                        mContext.startActivity(intent);
                    }
                    else{
                        Context context = holder.itemView.getContext();
                        Toast.makeText(context, "Not Unclocked", Toast.LENGTH_SHORT).show();
                    }
                });
                // passing data to the Gallery Heritage





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


    public CompletableFuture<Boolean> isUnlocked(String title) {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reference = db.collection("Users").document(users.getUid());

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                ArrayList<DocumentReference> posseduti = (ArrayList<DocumentReference>)document.get("Posseduti");
                List<CompletableFuture<Boolean>> futures = new ArrayList<>();

                for (DocumentReference posseduto : posseduti) {
                    CompletableFuture<Boolean> possedutoFuture = new CompletableFuture<>();

                    posseduto.get().addOnCompleteListener(documentTask -> {
                        if (documentTask.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = documentTask.getResult();

                            if (documentSnapshot.exists()) {

                                if (title.equals(documentSnapshot.get("Title").toString())) {

                                    possedutoFuture.complete(true);
                                    return;
                                }
                            }
                        }

                        possedutoFuture.complete(false);
                    });

                    futures.add(possedutoFuture);
                }

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenAccept(v -> {
                    boolean result = futures.stream().anyMatch(CompletableFuture::join);
                    future.complete(result);
                });
            } else {
                future.complete(false);
            }
        });

        return future;
    }

}

