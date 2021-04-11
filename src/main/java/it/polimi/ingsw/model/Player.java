package it.polimi.ingsw.model;

import it.polimi.ingsw.RealPlayerBoard;

public class Player {

    private final String name;
    private int victoryPoints;
    private final RealPlayerBoard playerBoard;
    private boolean firstToPlay = false;


    public Player(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.name = name;
        this.victoryPoints = 0;
    }

    public void setFirstToPlay() {
        this.firstToPlay = true;
    }

    public boolean getIsFirstToPlay() {
        return firstToPlay;
    }

    public String getName() {
        return name;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
    public RealPlayerBoard getPlayerBoard() { return playerBoard; }
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
