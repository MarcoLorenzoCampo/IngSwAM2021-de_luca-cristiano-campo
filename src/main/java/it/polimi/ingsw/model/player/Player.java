package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.inventoryManager.InventoryManager;

public class Player {

    private final String playerName;
    private int victoryPoints;
    private RealPlayerBoard playerBoard;
    private boolean firstToPlay = false;
    private PlayerState playerState;

    private InventoryManager inventoryManager;

    /**
     * Default player constructor.
     * @param name: chosen by the player.
     */
    public Player(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.playerName = name;
        this.victoryPoints = 0;
        this.playerState = new PlayerState();
        this.inventoryManager = new InventoryManager();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void setFirstToPlay() {
        this.firstToPlay = true;
    }

    public boolean getIsFirstToPlay() {
        return firstToPlay;
    }

    public String getName() {
        return playerName;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public RealPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
