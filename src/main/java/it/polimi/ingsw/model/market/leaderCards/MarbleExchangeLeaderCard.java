package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

/**
 * Subclass of the generic Leader Card.
 */
public class MarbleExchangeLeaderCard extends LeaderCard {


    private static final long serialVersionUID = 3469354013689989829L;

    /**
     * Uses the same constructor as the super class.
     * @param exchangeResource: resource to exchange for the grey marbles {@link ResourceType UNDEFINED}
     */
    public MarbleExchangeLeaderCard(int victoryPoints, EffectType effectType,
                                    DevelopmentTag[] requirements, ResourceType exchangeResource) {
        super(victoryPoints, effectType, exchangeResource, requirements, null);
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
                .addExchangeLeader(getResource());
    }

}
