package it.uniba.dib.sms222316.Gameplay;



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

    public void endTurn(List<Player> players) {

        if (players.stream().filter(l-> !l.isBankrupt()).count()<=1) {
            terminaPartita();
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();


        if(players.get(currentPlayerIndex).isBankrupt()) endTurn(players);

//
//        if(players.get(currentPlayerIndex).isBankrupt() && players.stream().filter(l-> !l.isBankrupt()).count()>1){
//            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
//            endTurn(players);
//        }
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


    public void gestisciAcquisto(Player player, Property property) {
            /**To do alert when there is no money to buy*/
        int prezzoProprieta = property.getCosto();
        if (player.getMoney() >= prezzoProprieta) {
            //player.addProperty(property);     ridondante salvarsi la lista delle proprietà se il giocatore è salvato nelle proprietà
            player.removeMoney(prezzoProprieta);
            property.setGiocatore(player);

        }
    }

    public void gestisciPagamentoAffitto( Property property) {
        int affitto = property.getAffitto(property.getPaints());
        Player player = players.get(currentPlayerIndex);
        property.getGiocatore().addMoney(affitto);

        if (player.getMoney() >= affitto) {
            player.removeMoney(affitto);
        } else {
            player.setBankrupt();
            /**To do manage mortage to sell*/
            // Il giocatore non ha abbastanza denaro per pagare l'affitto
            // ... gestisci la situazione di insolvenza del giocatore
        }
    }

    public void prossimoTurno() {
        if (!gameStarted) {
            //
        }


        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public int[] dadi() {

        if (!gameStarted) {

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

    public List<Property> getProperties() {
        return properties;
    }

    public List<Player> getPlayers() {
        return players;
    }
}


