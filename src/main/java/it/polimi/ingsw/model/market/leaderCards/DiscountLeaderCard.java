package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

public class DiscountLeaderCard extends LeaderCard {

    ResourceType discountedResource;

    public DiscountLeaderCard(int victoryPoints, EffectType effectType,
                              DevelopmentTag[] requirements, ResourceType discountedResource) {
        super(victoryPoints, effectType, requirements, null);
        this.discountedResource = discountedResource;
    }

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
