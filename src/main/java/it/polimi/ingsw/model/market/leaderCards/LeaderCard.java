package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

/**
 * Every specific leader card type extends this base leader card.
 */
public abstract class LeaderCard {

    private final int victoryPoints;
    private final EffectType effectType;
    private boolean isActive;
    private final DevelopmentTag[] requirementsDevCards;
    private final ResourceTag[] requirementsResource;

    /**
     * Building the cards using the attributes given by the builder class.
     * {@link it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder}
     */
    public LeaderCard(int victoryPoints, EffectType effectType,
                      DevelopmentTag[] requirementsDevCards, ResourceTag[] requirementsResource) {
        this.effectType = effectType;
        this.victoryPoints = victoryPoints;
        isActive = false;
        this.requirementsDevCards = requirementsDevCards;
        this.requirementsResource = requirementsResource;
    }

    /**
     * Sets the boolean value to true and gets overridden by the subclass activating its specific
     * effect.
     */
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
    public DevelopmentTag[] getRequirementsDevCards() {
        return requirementsDevCards;
    }
    public ResourceTag[] getRequirementsResource() {
        return requirementsResource;
    }
}
