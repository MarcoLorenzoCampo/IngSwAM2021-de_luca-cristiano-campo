package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

/**
 * Subclass of the generic Leader Card.
 */
public class MarbleExchangeLeaderCard extends LeaderCard {

    ResourceType exchangeResource;

    /**
     * Uses the same constructor as the super class.
     * @param exchangeResource: resource to exchange for the grey marbles {@link ResourceType UNDEFINED}
     */
    public MarbleExchangeLeaderCard(int victoryPoints, EffectType effectType,
                                    DevelopmentTag[] requirements, ResourceType exchangeResource) {
        super(victoryPoints, effectType, requirements, null);
        this.exchangeResource = exchangeResource;
    }

    /**
     * Overrides the super class method, adding the resource to be exchanged to the
     * {@link it.polimi.ingsw.model.inventoryManager.InventoryManager}
     */
    @Override
    public void setActive() {
        super.setActive();
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .addExchangeLeader(exchangeResource);
    }

    public ResourceType getExchangeResource() {
        return exchangeResource;
    }
}
