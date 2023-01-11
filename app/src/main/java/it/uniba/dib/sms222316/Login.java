package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private long backPressed;
    private static final int TIME_INTERVALL = 2000;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_login);



    //TODO: Rimando schermata Guest

    //TODO: Rimando schermata principale


        //Rimando alla schermata di registrazione
        Register = findViewById(R.id.SignIn);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, registration.class));
            }
        });
    }

    /*@Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Do you really want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }*/

    //Avviso uscita dall'app
    @Override
    public void onBackPressed() {
        if(backPressed + TIME_INTERVALL > System.currentTimeMillis()){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit app", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}