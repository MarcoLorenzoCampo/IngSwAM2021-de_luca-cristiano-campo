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

    private boolean hasPlaceableLeaders;
    private boolean hasActivatedProductions;
    private boolean hasPickedResources;
    private boolean hasBoughCard;

    public PlayerState() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasPlaceableLeaders = true;
        hasBoughCard = false;
    }

    public void noMoreLeaderCards() {
        hasPlaceableLeaders = false;
    }

    public void performedExclusiveAction() {
        hasActivatedProductions = true;
        hasPickedResources = true;
        hasBoughCard = true;
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


    public void endTurnReset() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasBoughCard = false;
    }
}
