package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.MultiplayerGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

public class MarbleExchangeLeaderCard extends LeaderCard {

    ResourceType exchangeResource;

    public MarbleExchangeLeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requirements, ResourceType exchangeResource) {
        super(victoryPoints, effectType, requirements);
        this.exchangeResource = exchangeResource;
    }

    @Override
    public void setActive() {
        super.setActive();
        MultiplayerGame.getGameInstance()
                .getCurrentPlayer()
                .getInventoryManager()
                .addExchangeLeader(exchangeResource);
    }

    public ResourceType getExchangeResource() {
        return exchangeResource;
    }
}
