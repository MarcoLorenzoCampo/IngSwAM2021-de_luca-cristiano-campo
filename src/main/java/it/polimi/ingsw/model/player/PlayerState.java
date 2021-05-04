package it.polimi.ingsw.model.player;

/**
 * Contains information about what action has be performed by the
 * player.
 * Exclusive actions can be performed once per turn, so each attribute is
 * set to true once one is performed.
 *
 * Using multiple booleans can be redundant but makes the code easier to read.
 */
public class PlayerState {

    private boolean hasActivatedProductions;
    private boolean hasPickedResources;
    private boolean hasBoughCard;
    private boolean isConnected;

    private boolean hasPlacedLeaders;

    private boolean hasPlaceableLeaders;

    public PlayerState() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasBoughCard = false;
        hasPlacedLeaders = false;

        hasPlaceableLeaders = true;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect() {
        this.isConnected = true;
    }

    public void disconnect() {
        this.isConnected = false;
    }

    public void noMoreLeaderCards() {
        hasPlaceableLeaders = false;
    }

    public void performedExclusiveAction() {
        hasActivatedProductions = true;
        hasPickedResources = true;
        hasBoughCard = true;

        hasPlacedLeaders = false;
    }

    public void placedLeader() {
        this.hasPlacedLeaders = true;
    }

    public boolean getHasPlaceableLeaders() {
        return hasPlaceableLeaders;
    }
    public boolean getHasActivatedProductions() {
        return hasActivatedProductions;
    }
    public boolean getHasPickedResources() {
        return hasPickedResources;
    }
    public boolean getHasBoughCard() {
        return hasBoughCard;
    }
    public boolean getHasPlacedLeaders() {
        return hasPlacedLeaders;
    }



    public void endTurnReset() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasBoughCard = false;
        hasPlacedLeaders = false;
    }
}
