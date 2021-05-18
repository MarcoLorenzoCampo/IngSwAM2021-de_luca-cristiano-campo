package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Player Class, ha references to a few useful classes.
 */
public class RealPlayer implements Serializable {

    private static final long serialVersionUID = -8446287370449348970L;

    /**
     * Name of the player.
     */
    private final String playerName;

    /**
     * Victory points acquired.
     */
    private int victoryPoints;

    /**
     * Reference to the player board.
     */
    private final RealPlayerBoard playerBoard;

    /**
     * Boolean value to know if the player is the first one to play.
     */
    private boolean firstToPlay = false;

    /**
     * Reference to the player state.
     */
    private final PlayerState playerState;

    /**
     * Reference to the owned leader cards.
     */
    private List<LeaderCard> ownedLeaderCards;

    /**
     * Reference to his inventory manager.
     */
    //private final InventoryManager inventoryManager;

    /**
     * Default player constructor.
     * @param name: chosen by the player.
     */
    public RealPlayer(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.playerName = name;
        this.victoryPoints = 0;
        this.playerState = new PlayerState();
        //this.inventoryManager = new InventoryManager();
        this.ownedLeaderCards = new LinkedList<>();
    }

    public void setOwnedLeaderCards(List<LeaderCard> ownedLeaderCards) {
        this.ownedLeaderCards = ownedLeaderCards;
    }

   // public InventoryManager getInventoryManager() {
   //     return inventoryManager;
   // }

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

    public List<LeaderCard> getOwnedLeaderCards() {
        return ownedLeaderCards;
    }
}
