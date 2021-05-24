package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.Reducible;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.ArrayList;
import java.util.Objects;

public class ProductionCard extends BaseProduction implements Reducible {

    private final Level level;
    private final Color color;
    private final int id;
    private final int victoryPoints;
    private final ArrayList<ResourceTag> requirements;

    /* for testing purposes */
    public ProductionCard(Level level, Color color,int id, int victoryPoints, ArrayList<ResourceTag> requirements) {
        this.level = level;
        this.color = color;
        this.id = id;
        this.victoryPoints = victoryPoints;
        this.requirements = requirements;
    }

    public Level getLevel() {
        return level;
    }
    public Color getColor() {
        return color;
    }
    public int getVictoryPoints() {
        return victoryPoints;
    }
    public int getId(){return id;}


    public ArrayList<ResourceTag> getRequirements() {
        return requirements;
    }

    public void placeCard(int index, ProductionCard boughtCard, ProductionBoard productionBoard) {
        productionBoard.placeProductionCard(index, boughtCard);
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

    /**
     * Method to get a reduced read only instance of a production card to be send via network.
     * @return: reduced card.
     */
    @Override
    public String reduce() {
        String signature = "-----------------------------ProductionCard-----------------------------" +
            "\nLEVEL: " + level + "; COLOR: " + color + "; VICTORY POINTS: " + victoryPoints + "\n";

        String req = "";
        for (ResourceTag requirement : requirements) {
            req = req.concat(requirement.toString()) + " - ";
        }

        String input = "";
        for (int i=0; i<super.getInputResources().size(); i++) {
            input = input.concat(super.getInputResources().get(i).toString()) + " - ";
        }

        String output = "";
        for (int j=0; j<super.getOutputResources().size(); j++) {
            output = output.concat(super.getOutputResources().get(j).toString()) + " - ";
        }

        return signature + "\n" + "Requirements:\n" + req + "\n"
                + "Input resources:\n" +  input + "\n"
                + "Output provided:\n" + output + "\n"
                + "------------------------------------------------------------------------\n";
    }
}
