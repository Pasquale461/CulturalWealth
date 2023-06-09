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
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222316.Gameplay.Game;
import it.uniba.dib.sms222316.Gameplay.Player;
import it.uniba.dib.sms222316.Gameplay.Property;

public class GameActivity extends AppCompatActivity {

    int start=0;

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



        int coordinate[] =new int[2];
        casella = caselle.setCasella(this);
        List<Player> players = new ArrayList<>();
        players.add(new Player("Giocatore 1"));
        players.add(new Player("Giocatore 2"));
        players.add(new Player("Giocatore 3"));
        List<Property> properties = new ArrayList<>();
        int v[] = {1,2,3};
        properties.add(new Property("", "" , "" , "" , 1 , v, 1 ,1 ,1));
        properties.add(new Property("", "" , "" , "" , 1 , v, 1 ,1 ,1));

        //Instanza oggetto partita
        game = new Game(players , properties);
        //Calcolo giocatori
        ImageView[] pedina = new ImageView[game.getNumberOfPlayers()];
        int[] position = new int[game.getNumberOfPlayers()];
        pedina[0] = findViewById(R.id.pedina1);
        pedina[1] = findViewById(R.id.pedina2);
        pedina[2] = findViewById(R.id.pedina3);





        PopupField field = new PopupField(GameActivity.this, GameActivity.this);
        Button rollButton = findViewById(R.id.dado);




        View view = findViewById(R.id.relativeLayout);
        GifImageView Dice = findViewById(R.id.dice);

        // Registra un OnGlobalLayoutListener sulla vista
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Verifica se la vista è stata già creata
                if (!isViewCreated) {

                    casella[start].getLocationInWindow(coordinate);

                    for (int i=0;i < game.getNumberOfPlayers();i++)
                    {
                        pedina[i].setX(coordinate[0]);
                        pedina[i].setY(coordinate[1]);
                    }

                    isViewCreated = true;
                }

                // Rimuovi il listener per evitare di essere chiamato nuovamente
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
            game.iniziaPartita();
            game.dadi();
            int[] numeri = new int[2];
            numeri = game.dadi();
            int randomNumber = numeri[0];

            int gifResourceId = 0;
            switch (randomNumber){
                case 1: gifResourceId = R.drawable.dado1;break;
                case 2: gifResourceId = R.drawable.dado2;break;
                case 3: gifResourceId = R.drawable.dado3;break;
                case 4: gifResourceId = R.drawable.dado4;break;
                case 5: gifResourceId = R.drawable.dado5;break;
                case 6: gifResourceId = R.drawable.dado6;break;
            }
            GifDrawable Gif = null;
            try {
                Gif = new GifDrawable(getResources(), gifResourceId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Gif.setLoopCount(1);
            GifDrawable finalGif = Gif;
            finalGif.reset(); // Resetta la GIF all'inizio
            finalGif.start();
            Dice.setImageDrawable(finalGif);

            rollButton.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rollButton.setEnabled(true);
                    Toast.makeText(GameActivity.this, "Hai ottenuto " + randomNumber, Toast.LENGTH_SHORT).show();
                }
            }, 3500);

            int currentPlayer = game.getCurrentPlayerIndex();

            Path path = new Path();

            path.moveTo(casella[position[currentPlayer]].getX(),casella[position[currentPlayer]].getY());

            for(int i = 1;i<=randomNumber;i++) {

                position[currentPlayer] ++;
                if(position[currentPlayer] == 40) position[currentPlayer] =0;
                path.lineTo(casella[position[currentPlayer]].getX(), casella[position[currentPlayer]].getY());
            }

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if(position[currentPlayer] < 40){
                        field.InfoField(position[currentPlayer]);
                        field.show();
                    }
                }
            }, 2000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(pedina[currentPlayer], pedina[currentPlayer].X,pedina[currentPlayer].Y, path);
                    animator.setDuration(2000);
                    animator.start();
                }
            },3500);

            players.get(currentPlayer).setPosition(position[currentPlayer]);
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