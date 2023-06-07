package it.uniba.dib.sms222316.Gameplay;

import java.util.List;

import it.uniba.dib.sms222316.Player;

public class Game {
    private List<Player> players;
    private int currentPlayerIndex;

    public Game(List<Player> players) {
        this.players = players;
        currentPlayerIndex = 0;
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
}

