package it.uniba.dib.sms222316;

import static it.uniba.dib.sms222316.Utility.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private long backPressed;
    private static final int TIME_INTERVALL = 2000;
    Button Register, Login , loginWithGoogle;
    EditText emailEditText, passwordEditText;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_login);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); //va rivisto



        //TODO: Rimando schermata Guest

        //TODO: Rimando schermata principale

        //Instanza oggetti visivi
        emailEditText = findViewById(R.id.emaillogintext);
        passwordEditText = findViewById(R.id.passwordlogintext);
        Login = findViewById(R.id.loginButton);
        loginWithGoogle = findViewById(R.id.Google);
        //Instanza variabili di login
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){

        }

        //Creazione trigger  onclick
        Login.setOnClickListener((v)-> loginUser());
        loginWithGoogle.setOnClickListener(view -> SignIn());





        //Rimando alla schermata di registrazione
        Register = findViewById(R.id.SignIn);
        Register.setOnClickListener(v -> startActivity(new Intent(Login.this, Registration.class)));
    }


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

    //Funzione che gestisce il login classico
    void loginUser()
    {
        String mail = emailEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        boolean isValid = validateData(mail, pass);
        if(!isValid)return;
        loginAccountInFirebase(mail, pass);
    }

    //
    boolean validateData(String Email , String Password_local){
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            emailEditText.setError("Email non valida");
            return false;
        }
        if(Password_local.length()<6)
        {
            passwordEditText.setError("Lunghezza della password troppo corta");
            return false;
        }
        return true;
    }
    //Login con firebase
    void loginAccountInFirebase(String mail,String pass)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
            changeInProgress(false);
            if (task.isSuccessful())
            {
                if (firebaseAuth.getCurrentUser().isEmailVerified()) //registrazione avvenuta correrttamente (controllo errore)
                {
                    startActivity(new Intent(Login.this, Home.class));
                }else
                {
                    showToast(Login.this, "Email non valida");//registrazione non avvenuta
                }
            }
            else
            {
                showToast(Login.this, task.getException().getLocalizedMessage());//controllo dell'errore
            }

        });

    }
    void changeInProgress(boolean inProgress)
    {
        if(inProgress){
            Login.setVisibility(View.GONE);
        }else{
            Login.setVisibility(View.VISIBLE);
        }
    }
    //login con Google API
    private void SignIn()
    {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try { //Login Work
                task.getResult(ApiException.class);
                HomeActivity();

            } catch (ApiException e) {// Login error
                showToast(this, "error");
            }
        }
    }



    private void HomeActivity()
    {
        finish();
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
}
