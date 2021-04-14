package it.polimi.ingsw.model.player;

public class Player {

    private final String playerName;
    private int victoryPoints;
    private final RealPlayerBoard playerBoard;
    private boolean firstToPlay = false;
    private final PlayerState playerState;


    public Player(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.playerName = name;
        this.victoryPoints = 0;
        playerState = new PlayerState();
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

    public RealPlayerBoard getPlayerBoard() { return playerBoard; }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
