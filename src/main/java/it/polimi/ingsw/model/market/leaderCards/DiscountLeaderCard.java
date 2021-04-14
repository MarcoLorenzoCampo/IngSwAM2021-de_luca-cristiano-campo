package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.List;

public class DiscountLeaderCard extends LeaderCard {

    ResourceType discountedResource;

    public DiscountLeaderCard(int victoryPoints, EffectType effectType,
                              DevelopmentTag[] requirements, ResourceType discountedResource) {
        super(victoryPoints, effectType, requirements);
        this.discountedResource = discountedResource;
    }

    public ResourceType getDiscountedResource() {
        return discountedResource;
    }
}
