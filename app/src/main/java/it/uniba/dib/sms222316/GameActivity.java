package it.uniba.dib.sms222316;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222316.Gameplay.Game;

public class GameActivity extends AppCompatActivity {
    int[] position = new int[2];
    int start=0;
    ImageView[] pedina = new ImageView[2];
    Caselle caselle = new Caselle();
    ImageView[] casella;
    boolean isViewCreated = false;
    private TextView playerNameTextView;
    private TextView playerScoreTextView;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_game);
        List<Player> players = new ArrayList<>();
        players.add(new Player("Giocatore 1"));
        players.add(new Player("Giocatore 2"));

        game = new Game(players);
        int coordinate[] =new int[2];
        casella = caselle.setCasella(this);
        PopupField field = new PopupField(GameActivity.this, GameActivity.this);
        Button rollButton = findViewById(R.id.dado);

        pedina[0] = findViewById(R.id.pedina1);
        pedina[1] = findViewById(R.id.pedina2);
        View view = findViewById(R.id.relativeLayout);


        // Registra un OnGlobalLayoutListener sulla vista
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Verifica se la vista è stata già creata
                if (!isViewCreated) {

                    casella[start].getLocationInWindow(coordinate);

                    pedina[0].setX(coordinate[0]);
                    pedina[0].setY(coordinate[1]);
                    pedina[1].setX(coordinate[0]);
                    pedina[1].setY(coordinate[1]);
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

            int a = game.getCurrentPlayerIndex();
            Toast.makeText(GameActivity.this, "Hai ottenuto " + randomNumber, Toast.LENGTH_SHORT).show();
            Path path = new Path();

            path.moveTo(casella[position[a]].getX(),casella[position[a]].getY());

            for(int i = 1;i<=randomNumber;i++) {

                position[a] ++;
                if(position[a] == 40) position[a] =0;
                path.lineTo(casella[position[a]].getX(), casella[position[a]].getY());
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(position[a] < 40){
                        field.InfoField(position[a]);
                        field.show();
                    }
                }
            }, 2000);

            ObjectAnimator animator = ObjectAnimator.ofFloat(pedina[a], pedina[a].X,pedina[a].Y, path);
            animator.setDuration(2000);
            animator.start();
            playerNameTextView = view.findViewById(R.id.playerNameTextView);
            playerScoreTextView = view.findViewById(R.id.playerScoreTextView);

            updateUI(randomNumber);

            game.endTurn();
            }
        });

    }
    private Player updateUI(int randomnumber) {
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addScore(randomnumber);
    playerNameTextView.setText(currentPlayer.getName());
    playerScoreTextView.setText(String.valueOf(currentPlayer.getScore()));
    return currentPlayer;
}
}