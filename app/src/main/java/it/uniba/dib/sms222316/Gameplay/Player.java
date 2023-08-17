package it.uniba.dib.sms222316.Gameplay;


import java.util.List;


public class Player {
    private String name;
    private int score, money;
    private int position;
    private int icon;
    private List<Property> properties;
    private boolean prison;
    private boolean bankrupt;
    private int turnPrison;


    public Player(String name, int icon) {
        bankrupt = false;
        this.name = name;
        this.icon = icon;
        score = 0;
        money = 1500;
        prison = false;
        turnPrison = 0;
    }

    public String getName() {return name;}

    public int getScore() {return score;}

    public void addScore(int points) {score += points;}

    public void setPosition(int position) {this.position = position;}

    public int getPosition() {return position;}

    public int getMoney() {
        return money;
    }

    public List<Property> getProperties() {
        return properties;
    }


    public void removeProperty(Property property) {
        property.setGiocatore(null);
        properties.remove(property);
    }

    public boolean isPrison() {return prison;}

    public void setPrison(boolean prison) {this.prison = prison;
    turnPrison=2;}

    public int getIcon() {
        return icon;
    }

    public int getTurnPrison() {return turnPrison;}

    public void setTurnPrison(int turnPrison) {this.turnPrison = turnPrison;}

    public void addMoney(int money) {this.money += money;}

    public void removeMoney(int money) {this.money -= money;}

    public void removeTurn() {turnPrison--;}
    public void setBankrupt() {this.bankrupt = true;}
    public boolean isBankrupt() {return bankrupt;}
}
