package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.ArrayList;
import java.util.Objects;

public class ProductionCard extends BaseProduction {

    private Level level;
    private Color color;
    private int victoryPoints;
    private ArrayList<ResourceTag> requirements;

    /* no need for a public constructor */
    private ProductionCard() { }

    public Level getLevel() {
        return level;
    }
    public Color getColor() {
        return color;
    }
    public int getVictoryPoints() {
        return victoryPoints;
    }


    public ArrayList<ResourceTag> getRequirements() {
        return requirements;
    }

    public void placeCard(int index, ProductionCard boughtCard) {
        PlayingGame.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .placeProductionCard(index, boughtCard);
    }

    @Override
    public String toString() {
        return "[ color = " + color + " level = " + level + " ]";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionCard)) return false;
        ProductionCard that = (ProductionCard) o;
        return getVictoryPoints() == that.getVictoryPoints() && getLevel() == that.getLevel()
                && getColor() == that.getColor()
                && Objects.equals(getRequirements(), that.getRequirements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getColor());
    }

    public boolean equalsColorLevel(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionCard)) return false;
        ProductionCard that = (ProductionCard) o;
        return getLevel() == that.getLevel()
                && getColor() == that.getColor();
    }

    /* method needed in the ProductionCardMarket */
    public String key() {
        return level + "_" + color;
    }
}
