package it.uniba.dib.sms222316;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

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
        String name = Username.getText().toString();
        String mail = Mail.getText().toString();
        String pass = Password.getText().toString();
        boolean isValid = validatedata(name, mail, pass);
        if(!isValid)return;
        createAccountInFirebase(name, mail, pass);
    }



    void createAccountInFirebase(String Name , String Email , String Password_local)
    {

    }

    void changeInProgress(boolean inProgress)
    {

    }

    boolean validatedata(String Name , String Email , String Password_local){
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

