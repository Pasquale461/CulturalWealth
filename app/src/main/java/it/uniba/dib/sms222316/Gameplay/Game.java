package it.uniba.dib.sms222316.Gameplay;

import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {
        private final List<Player> players;
        private final List<Property> properties;
        private int currentPlayerIndex;
        private boolean gameStarted;

        public Game(List<Player> players, List<Property> properties) {
            this.players = players;
            this.properties = properties;
            currentPlayerIndex = 0;
            gameStarted = false;
        }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void iniziaPartita() {
        gameStarted = true;
        // Inizializza il gioco, distribuisci le proprietà, assegna i soldi iniziali ai giocatori, ecc.
    }

    public void terminaPartita() {
        gameStarted = false;
        // Esegui le operazioni di pulizia o salvataggio dei dati alla fine della partita
    }

    public void gestisciLancioDadi(Player player, int valoreDado1, int valoreDado2) {
        if (!gameStarted) {
            throw new IllegalStateException("La partita non è ancora iniziata.");
        }

        int sommaDadi = valoreDado1 + valoreDado2;
        int nuovaPosizione = (player.getPosition() + sommaDadi) % properties.size();

        //Il giocatore riceve 200
        if (nuovaPosizione < player.getPosition()) {
            player.addMoney(200);
        }

        player.setPosition(nuovaPosizione);
        Property currentProperty = properties.get(nuovaPosizione);

        if (currentProperty.isAvaible()) {
            gestisciAcquisto(player, currentProperty);
        } else if (currentProperty.getGiocatore() != player) {
            gestisciPagamentoAffitto(player, currentProperty);
        }

    }

    public void gestisciAcquisto(Player player, Property property) {
        int prezzoProprieta = property.getCosto();

        if (player.getMoney() >= prezzoProprieta) {
            player.addProperty(property);
            player.removeMoney(prezzoProprieta);
            property.setGiocatore(player);
        }
    }

    public void gestisciPagamentoAffitto(Player player, Property property) {
        int affitto = property.getAffitto(property.getPaints());

        if (player.getMoney() >= affitto) {
            Player owner = property.getGiocatore();
            player.removeMoney(affitto);
            owner.addMoney(affitto);
        } else {
            // Il giocatore non ha abbastanza denaro per pagare l'affitto
            // ... gestisci la situazione di insolvenza del giocatore
        }
    }

    public void prossimoTurno() {
        if (!gameStarted) {
            throw new IllegalStateException("La partita non è ancora iniziata.");
        }


        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public int[] dadi() {

        if (!gameStarted) {
            throw new IllegalStateException("La partita non è ancora iniziata.");
        }
        int[] randomNumber= new int[2];
        randomNumber[0] = (int) (Math.random() * 6) + 1;
        randomNumber[1] = (int) (Math.random() * 6) + 1;
        return randomNumber;
    }

    public boolean isPartitaFinita() {
        return !gameStarted;
    }

    // Altri metodi getter/setter e metodi ausiliari

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getNumberOfPlayers() {
        return this.players.size();
    }

}


