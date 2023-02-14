package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    EditText Username , Password , Mail;
    Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_registration);


        //Registration class
        Username = findViewById(R.id.editTextTextPersonName);
        Mail = findViewById(R.id.editTextTextEmailAddress);
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
        showToast(Registration.this, "Successo");
        startActivity(new Intent(Registration.this, Login.class));
    }



    void createAccountInFirebase(String Email , String Password_local, String name)
    {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password_local).addOnCompleteListener(Registration.this,
                task -> {
                    if(task.isSuccessful()){
                        //acc is done

                        firebaseAuth.getCurrentUser().sendEmailVerification(); //controllo dell'errore
                        firebaseAuth.signOut(); //Perché uscire?
                        String UID = firebaseAuth.getCurrentUser().getUid();
                        Log.e("a", "entrato");
                        Map<String, Object> Users = new HashMap<>();
                        Users.put("UID", UID);
                        Users.put("email", Email);
                        Users.put("nome", name);

                        showToast(Registration.this, "Successo nome");
                        FirebaseFirestore.getInstance().collection("Users").document(UID)
                                .set(Users).addOnSuccessListener(unused -> showToast(Registration.this, "Successo"))
                                .addOnFailureListener(e -> showToast(Registration.this, "Fallito"));
                        Toast.makeText(Registration.this,"Account creato correttamente",Toast.LENGTH_SHORT).show();
                    }else{
                        //fail
                        Toast.makeText(Registration.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show(); //controllo errore
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

