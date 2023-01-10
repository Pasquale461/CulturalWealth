package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {

    EditText Username , Password , Mail;
    Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_registration);


        //Loginclass
        Username = findViewById(R.id.editTextTextPersonName);
        Mail = findViewById(R.id.editTextTextEmailAddress2);
        Password = findViewById(R.id.editTextTextPassword);
        Confirm = findViewById(R.id.confirm);

        Confirm.setOnClickListener(v-> createAccount());
    }
    void createAccount(){

        String mail = Mail.getText().toString();
        String pass = Password.getText().toString();
        boolean isValid = validatedata(mail, pass);
        if(!isValid)return;
        createAccountInFirebase(mail, pass);
    }



    void createAccountInFirebase(String Email , String Password_local)
    {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password_local).addOnCompleteListener(registration.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //acc is done
                            Toast.makeText(registration.this,"Account creato correttamente",Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else{
                            //fail
                            Toast.makeText(registration.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    void changeInProgress(boolean inProgress)
    {
        if(inProgress){
            Confirm.setVisibility(View.GONE);
        }else{
            Confirm.setVisibility(View.VISIBLE);
        }
    }

    boolean validatedata(String Email , String Password_local){
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            Mail.setError("Email is invalid");
            return false;
        }
        if(Password_local.length()<6)
        {
            Password.setError("Password length is invalid");
            return false;
        }
        return true;
    }
}