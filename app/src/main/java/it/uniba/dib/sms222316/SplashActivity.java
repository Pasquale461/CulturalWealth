package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    Thread loading;
    boolean conn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_splash);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        ProgressBar simpleProgressBar=(ProgressBar)findViewById(R.id.simpleProgressBar); // initiate the progress bar


        // Start long running operation in a background thread
       loading = new Thread(new Runnable() {
            public void run() {








                // ottieni la directory di download predefinita dell'applicazione



                while (progressStatus < 100) {
                    progressStatus += 3;

                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            simpleProgressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Backgroundloadscreen();

        if (conn) redirect();
    }

    private void redirect(){
        new Handler().postDelayed(() -> {
            if(progressStatus >= 100 && getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(SplashActivity.this, Login.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, Home.class));
                    finish();
                }
            } else {
                redirect();
            }
        },1000);
    }

    void GetimageFromStorage(StorageReference Subject , File foldertodownload)
    {

        Subject.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference fileRef : listResult.getItems()) {
                    // crea un file locale per il download
                    File localFile = new File(foldertodownload, fileRef.getName());
                    Log.d("for" , fileRef.getName());

                    // scarica il file nella directory di download
                    fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // file scaricato con successo
                            Log.d("Successo" , "Successo");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // gestisci eventuali errori di download
                            Log.d("Errore download" , "Errore download");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // gestisci eventuali errori nell'elencare i file nella cartella
                Log.d("errori nell'elencare i file nella cartella" , "errori nell'elencare i file nella cartella");
            }
        });
    }
    void Backgroundloadscreen()
    {
        if (connection()) {
            loading.start();
            AssetsLoad();

        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attenzione");
            builder.setMessage("La connessione a Internet non ?? disponibile.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Azioni da eseguire quando l'utente fa clic sul pulsante OK
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    void AssetsLoad()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference Profile = storageRef.child("ProfilesPictures/");
        StorageReference  Monuments = storageRef.child("Monuments/");
        StorageReference  Museums = storageRef.child("Museums/");
        File ProfilePic = new File(getFilesDir(), "CulturalWealth/ProfilesPictures");
        File MonumentsPic = new File(getFilesDir(), "CulturalWealth/Monuments");
        File MuseumPic = new File(getFilesDir(), "CulturalWealth/Museums");

        File directory = new File(getFilesDir(), "CulturalWealth/");
        if(ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!directory.exists()) {
                directory.mkdirs();


            } else {
                if (!ProfilePic.exists()) {

                    ProfilePic.mkdirs();
                    GetimageFromStorage(Profile, ProfilePic);
                }
                if (!MonumentsPic.exists()) {

                    MonumentsPic.mkdirs();
                    GetimageFromStorage(Monuments, MonumentsPic);
                }
                if (!MuseumPic.exists()) {

                    MuseumPic.mkdirs();
                    GetimageFromStorage(Museums, MuseumPic);
                }
                redirect();
            }
        }
    }

    boolean connection()
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // il dispositivo ha una connessione a Internet attiva
                return true;
            } else {
                // il dispositivo non ha una connessione a Internet attiva
                conn = false;
                return false;
            }
        }

}



