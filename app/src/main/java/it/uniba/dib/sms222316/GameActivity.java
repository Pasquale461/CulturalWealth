package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        playerNameTextView = findViewById(R.id.playerNameTextView);
        playerScoreTextView = findViewById(R.id.playerScoreTextView);
        Button endturn = findViewById(R.id.endTurn);
        Button info = findViewById(R.id.Info);
        info.setVisibility(View.INVISIBLE);


        int coordinate[] =new int[2];
        casella = caselle.setCasella(this);

        //Creazione lista Giocatori
        List<Player> players = new ArrayList<>();
        players.add(new Player("Giocatore 1"));
        players.add(new Player("Giocatore 2"));
        players.add(new Player("Giocatore 3"));

        //Lettura file JSON delle proprieta
        List<Property> properties = new ArrayList<>();
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("property", "raw", getPackageName());
        InputStream inputStream = resources.openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonString = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject0 = new JSONObject(String.valueOf(jsonString));
            JSONArray jsonArray = jsonObject0.getJSONArray("properties");

            //Creazione oggetti Proprietà
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("monument")){
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String group = jsonObject.getString("group");
                String description = jsonObject.getString("description_it");
                int price = jsonObject.getInt("price");
                JSONArray rentarray = jsonObject.getJSONArray("rent");
                int[] rent = new int[rentarray.length()];
                for (int j = 0; j < rentarray.length(); j++) {
                    rent[j] = rentarray.getInt(j);
                }
                int paintCost = jsonObject.getInt("paintCost");
                int sell_price = jsonObject.getInt("sell_price");
                int posizione = jsonObject.getInt("position");

                Property property = new Property(name,type,group,description,price,rent,paintCost,sell_price,posizione);
                properties.add(property);
                }
                if(jsonObject.getString("type").equals("museum") || jsonObject.getString("type").equals("utility")){
                    String name = jsonObject.getString("name");
                    String type = jsonObject.getString("type");
                    String description = jsonObject.getString("description_it");
                    int price = jsonObject.getInt("price");
                    JSONArray rentarray = jsonObject.getJSONArray("rent");
                    int[] rent = new int[rentarray.length()];
                    for (int j = 0; j < rentarray.length(); j++) {
                        rent[j] = rentarray.getInt(j);
                    }
                    int sell_price = jsonObject.getInt("sell_price");
                    int posizione = jsonObject.getInt("position");

                    Property property = new Property(name,type,description,price,rent,sell_price,posizione);
                    properties.add(property);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Property data : properties) {
            String peni = data.getNome()+"-"+data.getTipo();
            Log.d("GameActivity", peni);
        }

        //Instanza oggetto partita
        game = new Game(players , properties);
        game.iniziaPartita();

        //Calcolo giocatori
        ImageView[] pedina = new ImageView[game.getNumberOfPlayers()];
        int[] position = new int[game.getNumberOfPlayers()];
        pedina[0] = findViewById(R.id.pedina1);
        pedina[1] = findViewById(R.id.pedina2);
        pedina[2] = findViewById(R.id.pedina3);





        PopupField field = new PopupField(GameActivity.this, GameActivity.this);
        Button rollDice = findViewById(R.id.dado);



        //Creazione animazione Dadi
        View view = findViewById(R.id.relativeLayout);
        GifImageView Dice1 = findViewById(R.id.dice1);
        GifImageView Dice2 = findViewById(R.id.dice2);
        int random[] = game.dadi();
        int lastFrameIndex1 = rollDice(random[0]).getNumberOfFrames() - 1;
        Dice1.setImageBitmap(rollDice(random[0]).seekToFrameAndGet(lastFrameIndex1));
        int lastFrameIndex2 = rollDice(random[1]).getNumberOfFrames() - 1;
        Dice2.setImageBitmap(rollDice(random[1]).seekToFrameAndGet(lastFrameIndex2));


        // Registra un OnGlobalLayoutListener sulla vista
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Verifica se la vista è stata già creata
                if (!isViewCreated) {

                    casella[start].getLocationInWindow(coordinate);
                    //Posizionamento delle pedine sul Via
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

        updateUI(players.get(0).getScore());
        endturn.setVisibility(View.INVISIBLE);


        rollDice.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {

            //Lancio dei dadi
            int[] numeri = game.dadi();
            rollDice.setEnabled(false);
            GifDrawable Gif1 = rollDice(numeri[0]);
            GifDrawable Gif2 = rollDice(numeri[1]);
            Gif1.reset(); // Resetta la GIF all'inizio
            Gif1.start(); // Inizia animazione GIF
            Dice1.setImageDrawable(Gif1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Gif2.reset(); // Resetta la GIF all'inizio
                    Gif2.start(); // Inizia animazione GIF
                    Dice2.setImageDrawable(Gif2);
                }
            },200);



            int currentPlayer = game.getCurrentPlayerIndex();

            Path path = new Path();
            path.moveTo(casella[position[currentPlayer]].getX(),casella[position[currentPlayer]].getY());
            for(int i = 1;i<=(numeri[0]+numeri[1]);i++) {
                position[currentPlayer] ++;
                if(position[currentPlayer] == 40) position[currentPlayer] =0;
                path.lineTo(casella[position[currentPlayer]].getX(), casella[position[currentPlayer]].getY());
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(pedina[currentPlayer], pedina[currentPlayer].X,pedina[currentPlayer].Y, path);
                    animator.setDuration(2000);
                    animator.start();
                    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {
                            info.setVisibility(View.VISIBLE);
                            endturn.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {

                        }
                    };
                    animator.addListener(animatorListener);
                }
            },3500);



            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    field.InfoField(position[currentPlayer],properties);
                    field.show();
                }
            });
            players.get(currentPlayer).setPosition(position[currentPlayer]);
            }

        });
        endturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.endTurn();
                endturn.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                rollDice.setEnabled(true);

            }
        });

    }
    private Player updateUI(int random) {
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addScore(random);
    playerNameTextView.setText(currentPlayer.getName());
    playerScoreTextView.setText(String.valueOf(currentPlayer.getScore()));
    return currentPlayer;
    }

    private GifDrawable rollDice(int number){
        int gifResourceId = 0;
        switch (number){
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

        return Gif;
    }


}