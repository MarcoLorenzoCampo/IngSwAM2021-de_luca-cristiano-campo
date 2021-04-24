package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class ExtraProductionLeaderCard extends LeaderCard {

    DevelopmentTag[] requirements;
    ResourceTag[] inputResources;
    ResourceTag[] outputResources;

    public ExtraProductionLeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requirements,
             ResourceTag[] inputResources, ResourceTag[] outputResources) {

        super(victoryPoints, effectType, requirements, null);
        this.inputResources = inputResources;
        this.outputResources = outputResources;
    }

    @Override
    public void setActive() {
        super.setActive();
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .addLeaderProduction(inputResources, outputResources);
    }

    public ResourceTag[] getInputResources() {
        return inputResources;
    }
    public ResourceTag[] getOutputResources() {
        return outputResources;
    }
    public DevelopmentTag[] getRequirements() {
        return requirements;
    }
}
