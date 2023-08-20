package it.uniba.dib.sms222316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.uniba.dib.sms222316.Rank.ranking_popup;
import it.uniba.dib.sms222316.databinding.EndgamePopupBinding;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.uniba.dib.sms222316.Gameplay.Game;
import it.uniba.dib.sms222316.Gameplay.Player;
import it.uniba.dib.sms222316.Gameplay.Property;

public class GameActivity extends AppCompatActivity {

    int start=0;

    Caselle caselle = new Caselle();
    ImageView[] casella;
    boolean isViewCreated = false;
    private TextView playerNameTextView;
    private TextView playerNameTextView2;
    private TextView playerNameTextView3;
    private TextView playerScoreTextView;
    private TextView playerScoreTextView2;
    private TextView playerScoreTextView3;
    Game game;
    List<Property> properties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_game);
        playerNameTextView = findViewById(R.id.name1);
        playerScoreTextView = findViewById(R.id.money1);
        playerScoreTextView2 = findViewById(R.id.money2);
        playerScoreTextView3 = findViewById(R.id.money3);
        Button endturn = findViewById(R.id.endTurn);
        Button info = findViewById(R.id.Info);
        Button buy = findViewById(R.id.buy);
        info.setVisibility(View.INVISIBLE);
        buy.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

// Estrai il parametro utilizzando la chiave identificativa
        int money = intent.getIntExtra("money", 1500);
        int icon = intent.getIntExtra("puddle", 0);
        ImageButton se = findViewById(R.id.savexit);
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  caricare oggetto
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference usr = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                String nuovoDocumentoId = "OAsoyWPGI2Q72hSAW50JOAsoyWPGI2Q72hSAW50J"; // Sostituisci con il tuo ID univoco o lascia vuoto per generare uno automaticamente
                CollectionReference partiteCollection = db.collection("Users").document().collection("Games");
                DocumentReference nuovoDocumentoRef = partiteCollection.document(nuovoDocumentoId);
                nuovoDocumentoRef.set(game);
            }
        });

        int coordinate[] =new int[2];
        casella = caselle.setCasella(this);

        //Creazione lista Giocatori
        List<Player> players = new ArrayList<>();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        playerNameTextView = findViewById(R.id.name1);findusername(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        playerNameTextView2 = findViewById(R.id.name2);playerNameTextView2.setText("Guest 2");
        playerNameTextView3 = findViewById(R.id.name33);playerNameTextView3.setText("Guest 3");
        players.add(new Player("Giocatore 1",0, money));
        players.add(new Player("Giocatore 2", 1, money));
        players.add(new Player("Giocatore 3", 2, money));

        ImageView pedina1 = findViewById(R.id.pedina1);
        switch (icon)
        {
            case 1 :pedina1.setImageResource(R.drawable.culture_japan_japanese_svgrepo_com);break;
            case 2 :pedina1.setImageResource(R.drawable.abstract_painting_ic_national_culture_paris_svgrepo_com);break;
            case 3 :pedina1.setImageResource(R.drawable.moai_svgrepo_com);break;
            case 4 :pedina1.setImageResource(R.drawable.icon_rank_149);break;
            case 5 :pedina1.setImageResource(R.drawable.japan_monument_svgrepo_com);break;
            default:pedina1.setImageResource(R.drawable.culture_japan_japanese_svgrepo_com);break;
        }


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
                String foto = jsonObject.getString("foto_path");
                Property property = new Property(name,type,group,description,price,rent,paintCost,sell_price,posizione,foto);
                properties.add(property);
                }
                if(jsonObject.getString("type").equals("museum") || jsonObject.getString("type").equals("utility")){
                    String name = jsonObject.getString("name");
                    String type = jsonObject.getString("type");
                    String group = "";
                    String description = jsonObject.getString("description_it");
                    int price = jsonObject.getInt("price");
                    JSONArray rentarray = jsonObject.getJSONArray("rent");
                    int[] rent = new int[rentarray.length()];
                    for (int j = 0; j < rentarray.length(); j++) {
                        rent[j] = rentarray.getInt(j);
                    }
                    int paintCost = 0;
                    int sell_price = jsonObject.getInt("sell_price");
                    int posizione = jsonObject.getInt("position");
                    String foto = "";

                    Property property = new Property(name,type,group,description,price,rent,paintCost,sell_price,posizione,foto);
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
        updateUI(players);


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

        endturn.setVisibility(View.INVISIBLE);
        rollDice.setOnClickListener(v -> {

            //Lancio dei dadi


                if (game.getCurrentPlayer().getMoney() <= 0) {
                    players.get(game.getCurrentPlayerIndex()).setBankrupt();
                    for (Property prop:properties) {
                        if (prop.getGiocatore() == game.getCurrentPlayer() )
                        {
                            prop.setGiocatore(null);
                            updateUI(prop.getPosizione() , -1);
                        }

                    }
                    updateUI(players);
                }

                int[] numeri = game.dadi();
            if(!game.isGameStarted())
            {
                endgame_popup end = new endgame_popup(GameActivity.this);
                //popup classifica
                Window window = end.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setGravity(Gravity.CENTER);
                end.show();
            }
            Log.d("Prison" , ""+game.getCurrentPlayer().isPrison());
            if(game.getCurrentPlayer().isPrison())
            {
                int turno = game.getCurrentPlayer().getTurnPrison();

                rollDice.setEnabled(false);

                GifDrawable Gif1 = rollDice(numeri[0]);
                GifDrawable Gif2 = rollDice(numeri[1]);
                Gif1.reset(); // Resetta la GIF all'inizio
                Gif1.start(); // Inizia animazione GIF
                Dice1.setImageDrawable(Gif1);
                new Handler().postDelayed(() -> {
                    Gif2.reset(); // Resetta la GIF all'inizio
                    Gif2.start(); // Inizia animazione GIF
                    Dice2.setImageDrawable(Gif2);
                }, 200);

                if ( numeri[0] == numeri[1] )
                {
                    game.getCurrentPlayer().point.doublejailexit();

                    game.getCurrentPlayer().setPrison(false);
                    game.getCurrentPlayer().setTurnPrison(0);
                    new Handler().postDelayed(() -> {
                        rollDice.callOnClick();
                    }, 4500);


                }
                else {
                    if ( turno > 2)
                    {
                        game.getCurrentPlayer().setPrison(false);
                        game.getCurrentPlayer().setTurnPrison(0);
                        endturn.setVisibility(View.VISIBLE);
                        game.getCurrentPlayer().point.injailpoint();
                    }
                    else
                    {
                        game.getCurrentPlayer().setTurnPrison(game.getCurrentPlayer().getTurnPrison()+1);
                        endturn.setVisibility(View.VISIBLE);
                        game.getCurrentPlayer().point.injailpoint();


                    }

                }
            }
            else {
                rollDice.setEnabled(false);
                GifDrawable Gif1 = rollDice(numeri[0]);
                GifDrawable Gif2 = rollDice(numeri[1]);
                game.getCurrentPlayer().point.Dicepoint(numeri[0], numeri[1]);

                Gif1.reset(); // Resetta la GIF all'inizio
                Gif1.start(); // Inizia animazione GIF
                Dice1.setImageDrawable(Gif1);
                new Handler().postDelayed(() -> {
                    Gif2.reset(); // Resetta la GIF all'inizio
                    Gif2.start(); // Inizia animazione GIF
                    Dice2.setImageDrawable(Gif2);
                }, 200);


                int currentPlayer = game.getCurrentPlayerIndex();

                Path path = new Path();
                path.moveTo(casella[position[currentPlayer]].getX(), casella[position[currentPlayer]].getY());
                for (int i = 1; i <= (numeri[0] + numeri[1]); i++) {
                    position[currentPlayer]++;
                    if (position[currentPlayer] == 40) {
                        position[currentPlayer] = 0;
                        players.get(currentPlayer).addMoney(100);
                        /*To do alert that money has been added*/
                    }
                    path.lineTo(casella[position[currentPlayer]].getX(), casella[position[currentPlayer]].getY());
                }
                if (position[currentPlayer] == 30) {
                    position[currentPlayer] = 10;
                    game.getCurrentPlayer().setPrison(true);
                    path.lineTo(casella[position[currentPlayer]].getX(), casella[position[currentPlayer]].getY());
                }


                new Handler().postDelayed(() -> {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(pedina[currentPlayer], pedina[currentPlayer].X, pedina[currentPlayer].Y, path);
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
                            Optional<Property> currentProperty = properties.stream().filter(l -> l.getPosizione() == position[currentPlayer]).findFirst();
                            if (currentProperty.isPresent()) {
                                if (currentProperty.get().getGiocatore() == null)
                                    buy.setVisibility(View.VISIBLE);
                                else {
                                    game.gestisciPagamentoAffitto(currentProperty.get());
                                    game.getCurrentPlayer().point.BuyPropPoints(Integer.parseInt(currentProperty.get().getGruppo()));
                                    game.getCurrentPlayer().point.paypoints(currentProperty.get().getAffitto(1),game.HaveGroup(currentProperty.get().getGruppo() ,currentProperty.get().getGiocatore() ));
                                }
                            }
//                            for (Property prop : properties) {
//                                if (prop.getPosizione() == position[currentPlayer] && prop.getGiocatore() == null)
//                                    buy.setVisibility(View.VISIBLE);
//                            }
                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {

                        }
                    };
                    animator.addListener(animatorListener);
                }, 3500);

            }
                buy.setOnClickListener(new View.OnClickListener() {
                    /**When you have no money should still be visible*/
                    @Override
                    public void onClick(View v) {
                        int currentPlayer = game.getCurrentPlayerIndex();

                        for (Property prop : properties) {
                            //if(prop.getTipo().equals("monument")) { Puoi comprare anche i musei e le centrali, if inutile
                                String name = prop.getNome();

                                if (name.equals(casella[position[currentPlayer]].getContentDescription())) {
                                    game.gestisciAcquisto(players.get(currentPlayer),prop);
                                    game.getCurrentPlayer().point.BuyPropPoints(Integer.parseInt(prop.getGruppo()));
                                    buy.setVisibility(View.INVISIBLE);
                                }
                            //}
                            if(prop.getGiocatore() != null){
                                updateUI(prop.getPosizione(),prop.getGiocatore().getIcon());
                            }
                        }

                    }
                });
                updateUI(players);
            int currentPlayer = game.getCurrentPlayerIndex();
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPlayer = game.getCurrentPlayerIndex();
                        field.InfoField(position[currentPlayer], properties);
                        field.show();
                    }
                });

                ConstraintLayout Player = findViewById(R.id.players);
                for(int i=0; i<players.size(); i++){
                    if(i!=currentPlayer){
                        ConstraintLayout rl = (ConstraintLayout) Player.getChildAt(i);
                        int j=i;
                        rl.setOnClickListener(v2 -> {
                            TradeProposal(currentPlayer, j);
                            rl.setOnClickListener(null);
                        });

                    }
                }


                players.get(currentPlayer).setPosition(position[currentPlayer]);
            });
        endturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.endTurn(players);
                endturn.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                buy.setVisibility(View.INVISIBLE);
                rollDice.setEnabled(true);
                updateUI(players);

        });

    }

    public void TradeProposal(int offerer, int recipient){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.trade_popup, null);
        builder.setView(dialogView);

        ListView listView1 = dialogView.findViewById(R.id.ListOfferer);
        ListView listView2 = dialogView.findViewById(R.id.ListRecipient);


        List<Player> players = game.getPlayers();
        List<Property> properties = game.getProperties();

        List<Property> items1 = properties.stream().filter(l -> {
            if (l.getGiocatore() != null) return l.getGiocatore().equals(players.get(offerer));
            return false;
        }).collect(Collectors.toList());
        List<Property> items2 = properties.stream().filter(l -> {
            if (l.getGiocatore() != null) return l.getGiocatore().equals(players.get(recipient));
            return false;
        }).collect(Collectors.toList());


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items1.stream().map(l -> l.getNome()).collect(Collectors.toList()));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items2.stream().map(l -> l.getNome()).collect(Collectors.toList()));



        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        builder.setPositiveButton("OK", (dialog, which) -> {
            SparseBooleanArray checked1 = listView1.getCheckedItemPositions();
            SparseBooleanArray checked2 = listView2.getCheckedItemPositions();
            List<Property> selectedItems1 = new ArrayList<>();
            List<Property> selectedItems2 = new ArrayList<>();
            for (int i = 0; i < checked1.size(); i++) {
                if (checked1.valueAt(i)) {
                    selectedItems1.add(items1.get(checked1.keyAt(i)));
                }
            }
            for (int i = 0; i < checked2.size(); i++) {
                if (checked2.valueAt(i)) {
                    selectedItems2.add(items2.get(checked2.keyAt(i)));
                }
            }

            TradeResponse(selectedItems1, selectedItems2);
        });

        builder.setNegativeButton("Annulla", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void TradeResponse(List<Property> OfferedProperty, List<Property> RequestedProperty){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.trade_popup, null);
        builder.setView(dialogView);
        Player Offerer = OfferedProperty.get(0).getGiocatore();
        Player Recipient = RequestedProperty.get(0).getGiocatore();
        ListView listView1 = dialogView.findViewById(R.id.ListOfferer);
        ListView listView2 = dialogView.findViewById(R.id.ListRecipient);



        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, OfferedProperty.stream().map(l -> l.getNome()).collect(Collectors.toList()));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RequestedProperty.stream().map(l -> l.getNome()).collect(Collectors.toList()));


        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            for (int i = 0; i < OfferedProperty.size(); i++) {
                OfferedProperty.get(i).setGiocatore(Recipient);
            }
            for (int i = 0; i < RequestedProperty.size(); i++) {
                RequestedProperty.get(i).setGiocatore(Offerer);
            }
            updateUI();
        });

        builder.setNegativeButton("Refuse", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updateUI(){
        ConstraintLayout BloccoIcone = findViewById(R.id.Icons);
        List<Property> properties = game.getProperties();

        for (Property prop:properties) {
            int ImagePlayer;
            ImageView icona = (ImageView) BloccoIcone.getChildAt(prop.getPosizione()+40);
            if(prop.getGiocatore() != null)     ImagePlayer = getResources().getIdentifier("_"+prop.getGiocatore().getIcon(), "drawable", getPackageName());
            else    ImagePlayer = getResources().getIdentifier("_-1", "drawable", getPackageName());
            icona.setImageResource(ImagePlayer);
        }

    }
    private void updateUI(int Position, int playerIcon){
        ConstraintLayout BloccoIcone = findViewById(R.id.Icons);
        ImageView icona = (ImageView) BloccoIcone.getChildAt(Position+40);
        int ImagePlayer = getResources().getIdentifier("_"+playerIcon, "drawable", getPackageName());
        icona.setImageResource(ImagePlayer);
    }

    private Player updateUI(List<Player> players) {
        Player currentPlayer = game.getCurrentPlayer();

        int money1 = players.get(0).getMoney();
        int money2 = players.get(1).getMoney();
        int money3 = players.get(2).getMoney();



        if (players.get(0).isBankrupt())playerScoreTextView.setText("BANCAROTTA");else playerScoreTextView.setText(money1+ "$");
        if (players.get(1).isBankrupt())playerScoreTextView2.setText("BANCAROTTA");else playerScoreTextView2.setText(money2+ "$");
        if (players.get(2).isBankrupt())playerScoreTextView3.setText("BANCAROTTA");else playerScoreTextView3.setText(money3+ "$");
    return currentPlayer;
    }

    private void findusername(String Accountstring)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email", Accountstring)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        playerNameTextView.setText(documentSnapshot.getString("nome"));

                    }
                });


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