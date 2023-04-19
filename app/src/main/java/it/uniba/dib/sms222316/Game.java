package it.uniba.dib.sms222316;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
public class Game extends AppCompatActivity {
    int position = 0,start=0;
    ImageView pedina;
    Caselle caselle = new Caselle();
    ImageView[] casella;
    boolean isViewCreated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_game);

        int coordinate[] =new int[2];
        casella = caselle.setCasella(this);

        Button rollButton = findViewById(R.id.dado);

        pedina = findViewById(R.id.pedina);

        View view = findViewById(R.id.relativeLayout);


        // Registra un OnGlobalLayoutListener sulla vista
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Verifica se la vista è stata già creata
                if (!isViewCreated) {

                    casella[start].getLocationInWindow(coordinate);

                    pedina.setX(coordinate[0]);
                    pedina.setY(coordinate[1]);

                    isViewCreated = true;
                }

                // Rimuovi il listener per evitare di essere chiamato nuovamente
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        rollButton.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
            int randomNumber = (int) (Math.random() * 6) + 1;
            rollButton.setEnabled(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rollButton.setEnabled(true);
                }
            }, 2000);

            Toast.makeText(Game.this, "Hai ottenuto " + randomNumber, Toast.LENGTH_SHORT).show();
            Path path = new Path();

            path.moveTo(casella[position].getX(),casella[position].getY());

            for(int i = 1;i<=randomNumber;i++) {

                position++;
                if(position==40) position=0;
                path.lineTo(casella[position].getX(), casella[position].getY());
            }
            ObjectAnimator animator = ObjectAnimator.ofFloat(pedina, pedina.X,pedina.Y, path);
            animator.setDuration(2000);
            animator.start();
            
            }
        });
    }
}