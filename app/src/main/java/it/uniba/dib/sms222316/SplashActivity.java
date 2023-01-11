package it.uniba.dib.sms222316;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_splash);

        ProgressBar simpleProgressBar=(ProgressBar)findViewById(R.id.simpleProgressBar); // initiate the progress bar


        // Start long running operation in a background thread
        Thread loading = new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 5;
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
        loading.start();

       redirect();
    }

    private void redirect(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(progressStatus >= 100){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null)
                    startActivity(new Intent(SplashActivity.this, Login.class));
                    //TODO : Sostituire MainActivity con Home una volta creata
                    else startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    redirect();
                }
            }
        },1000);
    }
}