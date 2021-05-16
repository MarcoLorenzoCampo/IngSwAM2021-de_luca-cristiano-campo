package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

/**
 * Subclass of the Leader Card.
 */
public class ExtraInventoryLeaderCard extends LeaderCard {


    /**
     * Uses the same constructor as the super class.
     * @param extraInventoryType: used as an extra storage unit of a specific type.
     */
    public ExtraInventoryLeaderCard(int victoryPoints, EffectType effectType, ResourceTag[] requirements,
                                    DevelopmentTag[] requirementsDev, ResourceType extraInventoryType) {
        super(victoryPoints, effectType, extraInventoryType, requirementsDev, requirements);
    }

    /**
     * Overrides the super class method by adding an extra inventory slot in the Warehouse.
     * {@link it.polimi.ingsw.model.warehouse.Warehouse}
     */
    @Override
    public void setActive() {
        super.setActive();
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .getWarehouse()
                .addExtraInventory(getResource());
    }

}
