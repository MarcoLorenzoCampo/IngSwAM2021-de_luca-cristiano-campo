package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.List;

public class MarbleExchangeLeaderCard extends LeaderCard {

    ResourceType exchangeResource;

    public MarbleExchangeLeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requirements, ResourceType exchangeResource) {
        super(victoryPoints, effectType, requirements);
        this.exchangeResource = exchangeResource;
    }

    public ResourceType getExchangeResource() {
        return exchangeResource;
    }
}
