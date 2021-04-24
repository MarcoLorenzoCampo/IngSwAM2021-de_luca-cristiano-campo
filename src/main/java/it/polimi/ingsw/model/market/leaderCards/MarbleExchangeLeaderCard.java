package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;

public class MarbleExchangeLeaderCard extends LeaderCard {

    ResourceType exchangeResource;

    public MarbleExchangeLeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requirements, ResourceType exchangeResource) {
        super(victoryPoints, effectType, requirements, null);
        this.exchangeResource = exchangeResource;
    }

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
