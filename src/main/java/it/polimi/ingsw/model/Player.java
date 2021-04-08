package it.polimi.ingsw.model;

public class Player {

    private final String name;
    private int victoryPoints;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
