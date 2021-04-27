package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

/**
 * Subclass of the Leader Card.
 */
public class DiscountLeaderCard extends LeaderCard {

    ResourceType discountedResource;

    /**
     * Uses the same constructor as the super class.
     * @param discountedResource: applies a specific resource discount.
     */
    public DiscountLeaderCard(int victoryPoints, EffectType effectType,
                              DevelopmentTag[] requirements, ResourceType discountedResource) {
        super(victoryPoints, effectType, requirements, null);
        this.discountedResource = discountedResource;
    }

    /**
     * Overrides the super class method by adding a discount attribute to the InventoryManager.
     * {@link it.polimi.ingsw.model.inventoryManager.InventoryManager}
     */
    @Override
    public void setActive() {
        super.setActive();
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .addDiscountLeader(discountedResource);
    }

    public ResourceType getDiscountedResource() {
        return discountedResource;
    }
}
