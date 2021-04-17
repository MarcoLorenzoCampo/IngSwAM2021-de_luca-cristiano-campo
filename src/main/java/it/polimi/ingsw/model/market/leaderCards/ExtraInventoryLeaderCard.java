package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class ExtraInventoryLeaderCard extends LeaderCard {

    ResourceTag[] requirements;
    ResourceType extraInventoryType;

    public ExtraInventoryLeaderCard(int victoryPoints, EffectType effectType,
                                    ResourceTag[] requirements, ResourceType extraInventoryType) {
        super(victoryPoints, effectType, null);
        this.extraInventoryType = extraInventoryType;
        this.requirements = requirements;
    }

    @Override
    public void setActive() {
        super.setActive();
        Game.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .getWarehouse()
                .addExtraInventory(extraInventoryType);
    }

    public ResourceTag[] getRequirements() {
        return requirements;
    }

    public ResourceType getExtraInventoryType() {
        return extraInventoryType;
    }
}
