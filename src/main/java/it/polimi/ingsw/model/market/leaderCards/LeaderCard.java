package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

public abstract class LeaderCard {

    private final int victoryPoints;
    private final EffectType effectType;
    private boolean isActive;
    private final DevelopmentTag[] requisites;

    public LeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requisites) {
        this.effectType = effectType;
        this.victoryPoints = victoryPoints;
        isActive = false;
        this.requisites = requisites;
    }

    public void setActive() {
        isActive = true;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
    public EffectType getEffectType() {
        return effectType;
    }
    public boolean isActive() {
        return isActive;
    }
    public DevelopmentTag[] getRequisites() {
        return requisites;
    }

}
