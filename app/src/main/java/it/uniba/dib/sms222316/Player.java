package it.uniba.dib.sms222316;

import java.util.List;

import it.uniba.dib.sms222316.Gameplay.Property;

public class Player {
    private String name;
    private int score, money;
    private int position;

    private List<Property> properties;

    public Player(String name) {
        this.name = name;
        score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void removeMoney(int money) {
        this.money -= money;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void addProperty(Property property) {
        property.setGiocatore(this);
        properties.add(property);
    }

    public void removeProperty(Property property) {
        property.setGiocatore(null);
        properties.remove(property);
    }
}
