package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class ExtraInventoryLeaderCard extends LeaderCard {

    ResourceType extraInventoryType;

    public ExtraInventoryLeaderCard(int victoryPoints,
                                    EffectType effectType, ResourceTag[] requirements, DevelopmentTag[] requirementsDev, ResourceType extraInventoryType) {
        super(victoryPoints, effectType, requirementsDev, requirements);
        this.extraInventoryType = extraInventoryType;
    }

    @Override
    public void setActive() {
        super.setActive();
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .getWarehouse()
                .addExtraInventory(extraInventoryType);
    }

    public ResourceType getExtraInventoryType() {
        return extraInventoryType;
    }
}
