package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class registration extends AppCompatActivity {

    EditText Username , Password , Mail;
    Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        //Registration class
        Username = findViewById(R.id.editTextTextPersonName);
        Mail = findViewById(R.id.editTextTextEmailAddress2);
        Password = findViewById(R.id.editTextTextPassword);
        Confirm = findViewById(R.id.confirm);

        Confirm.setOnClickListener(v-> createAccount());
    }
    void createAccount(){

        String mail = Mail.getText().toString();
        String pass = Password.getText().toString();
        String user = Username.getText().toString();
        boolean isValid = validateData(mail, pass);
        if(!isValid)return;
        createAccountInFirebase(mail, pass, user);
    }



    void createAccountInFirebase(String Email , String Password_local, String name)
    {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password_local).addOnCompleteListener(registration.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //acc is done

                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            String UID = firebaseAuth.getCurrentUser().getUid();

                            Map<String, Object> Users = new HashMap<>();
                            Users.put("UID", UID);
                            Users.put("email", Email);
                            Users.put("nome", name);

                            FirebaseFirestore.getInstance().collection("Users").document(Email)
                                    .set(Users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }

                                    });
                            Toast.makeText(registration.this,"Account creato correttamente",Toast.LENGTH_SHORT).show();
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

    boolean validateData(String Email , String Password_local){
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            Mail.setError("Email non valida");
            return false;
        }
        if(Password_local.length()<6)
        {
            Password.setError("Lunghezza della password troppo corta");
            return false;
        }
        return true;
    }
}

