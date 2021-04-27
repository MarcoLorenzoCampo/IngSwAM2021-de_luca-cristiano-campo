package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

/**
 * Subclass of Leader Card.
 */
public class ExtraProductionLeaderCard extends LeaderCard {

    DevelopmentTag[] requirements;
    ResourceTag[] inputResources;
    ResourceTag[] outputResources;

    /**
     * Uses the same constructor as the super class.
     * @param inputResources: resources to pay in order to activate the extra production.
     * @param outputResources: resources got after the activation.
     */
    public ExtraProductionLeaderCard(int victoryPoints, EffectType effectType, DevelopmentTag[] requirements,
             ResourceTag[] inputResources, ResourceTag[] outputResources) {

        super(victoryPoints, effectType, requirements, null);
        this.inputResources = inputResources;
        this.outputResources = outputResources;
    }

    /**
     * Overrides the super class method by adding an extra production card with the attributes
     * given by the leader card. Sets the new production card in the player's Production Board.
     * {@link it.polimi.ingsw.model.productionBoard.ProductionBoard}
     */
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
