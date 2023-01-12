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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PlayGamesAuthProvider;

import java.util.Objects;

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
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();



        //TODO: Rimando schermata Guest

        //TODO: Rimando schermata principale

        //Instanza oggetti visivi
        emailEditText = findViewById(R.id.emaillogintext);
        passwordEditText = findViewById(R.id.passwordlogintext);
        Login = findViewById(R.id.loginButton);
        loginWithGoogle = findViewById(R.id.Google);
        //Instanza variabili di login
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            firebaseAuthWithPlayGames(Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)));
        }

        //Creazione triggher  onclick
        Login.setOnClickListener((v)-> loginUser());
        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();

            }
        });





        //Rimando alla schermata di registrazione
        Register = findViewById(R.id.SignIn);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, registration.class));
            }
        });
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
        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful())
                {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) //registrazione avvenuta correrttamente
                    {
                        startActivity(new Intent(Login.this, Home.class));
                    }else
                    {
                        showToast(Login.this, "Email non valida");//registrazione non avvenuta
                    }
                }
                else
                {
                    showToast(Login.this, task.getException().getLocalizedMessage());
                }

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
        startActivityForResult(intent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try { //Login Work
                task.getResult(ApiException.class);
                firebaseAuthWithPlayGames(Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)));

            } catch (ApiException e) {// Login error
                showToast(this, "error");
            }
        }
    }

    private void firebaseAuthWithPlayGames(GoogleSignInAccount acct) {


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthCredential credential = PlayGamesAuthProvider.getCredential(Objects.requireNonNull(acct.getServerAuthCode()));
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's informatio
                            FirebaseUser user = auth.getCurrentUser();
                            HomeActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void HomeActivity()
    {
        finish();
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
}
