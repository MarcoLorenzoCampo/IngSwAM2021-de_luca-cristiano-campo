package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.ArrayList;

/**
 * Contains information about what action has be performed by the
 * player.
 * Exclusive actions can be performed once per turn, so each attribute is
 * set to true once one is performed.
 *
 * Using multiple booleans can be redundant but makes the code easier to read.
 */
public class PlayerState {

    private boolean SetUpPhase;

    private boolean hasActivatedProductions;
    private boolean hasPickedResources;
    private boolean hasBoughCard;
    private boolean isConnected;

    private boolean hasPlacedLeaders;

    private boolean hasPlaceableLeaders;

    private boolean hasTwoExchange;

    private boolean canDeposit;

    private ArrayList<ResourceTag> toBeRemoved;

    public PlayerState() {

        SetUpPhase = true;
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasBoughCard = false;
        hasPlacedLeaders = false;

        hasPlaceableLeaders = true;

        hasTwoExchange = false;

        canDeposit = false;
    }

    public void setSetUpPhase(boolean setUpPhase) {
        SetUpPhase = setUpPhase;
    }

    public boolean isSetUpPhase() {
        return SetUpPhase;
    }

    public void setCanDeposit(boolean canDeposit) {
        this.canDeposit = canDeposit;
    }

    public boolean CanDeposit() {
        return canDeposit;
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

    public boolean hasPerformedExclusiveAction(){
        return (hasActivatedProductions || hasPickedResources || hasBoughCard);
    }

    public void placedLeader() {
        this.hasPlacedLeaders = true;
    }

    public void setHasTwoExchange(boolean twoExchange){ this.hasTwoExchange = twoExchange;}


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
    public boolean isHasTwoExchange() { return hasTwoExchange; }

    public void endTurnReset() {
        hasActivatedProductions = false;
        hasPickedResources = false;
        hasBoughCard = false;

        hasPlacedLeaders = false;

        canDeposit = false;
    }

    public ArrayList<ResourceTag> getToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(ArrayList<ResourceTag> toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }
}
