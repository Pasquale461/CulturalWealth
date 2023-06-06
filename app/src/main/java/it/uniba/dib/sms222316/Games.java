package it.uniba.dib.sms222316;

import java.util.List;

public class Games {
    private List<Player> players;
    private int currentPlayerIndex;

    public Games(List<Player> players) {
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
