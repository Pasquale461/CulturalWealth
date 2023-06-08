package it.uniba.dib.sms222316.Gameplay;

import java.util.List;

import it.uniba.dib.sms222316.Player;

public class Game {
    private List<Player> players;
    private List<Property> properties;
    private int currentPlayerIndex;

    public Game(List<Player> players , List<Property> properties) {
        this.players = players;
        this.properties = properties;
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

    public void gestisciLancioDadi(Player player, int valoreDado1, int valoreDado2) {
        int sommaDadi = valoreDado1 + valoreDado2;
        int nuovaPosizione = (player.getPosition() + sommaDadi);
        int Primacasella;

        if ((player.getPosition() < 40 && player.getPosition() > 0) && nuovaPosizione >= 0 && nuovaPosizione < player.getPosition() ) {
            // Il giocatore ha passato per la casella di partenza, aggiungi un bonus
            player.addMoney(200);
        }

        player.setPosition(nuovaPosizione);

        if (properties.get(player.getPosition()).isAvaible()) {
            // La proprietà non ha un proprietario, il giocatore può acquistarla
           // player.addProperty(properties.get(player.getPosition());

        //} else if (player.getPosition().getProprietario() != player) {
            // Il giocatore paga l'affitto al proprietario della proprietà
          //  gestisciPagamentoAffitto(player, proprietàCorrente);
        }
    }
}


