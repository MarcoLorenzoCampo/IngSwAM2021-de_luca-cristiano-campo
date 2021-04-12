package it.polimi.ingsw.model.player;

public class PlayerState {

    private boolean hasPlaceableLeaders;
    private boolean hasActivatedProductions;
    private boolean hasPickedResources;

    public PlayerState() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasPickedResources = false;
        hasPlaceableLeaders = true;
    }

    public void noMoreLeaderCards() {
        hasPlaceableLeaders = false;
    }

    public void activatedProductions() {
        hasActivatedProductions = true;
    }

    public void pickedResources() {
        hasPickedResources = true;
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

    public void endTurnReset() {
        hasActivatedProductions = false;
        hasPickedResources = false;
    }
}
