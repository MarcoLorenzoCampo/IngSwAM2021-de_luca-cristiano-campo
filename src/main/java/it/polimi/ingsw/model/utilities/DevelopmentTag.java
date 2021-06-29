package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;

/**
 * Class used by the leader cards to define the cost in terms of color and level of owned production cards
 */
public class DevelopmentTag extends Tag {
    private Color color;
    private Level level;

    public DevelopmentTag(int quantity, Color color, Level level) {
        super.setQuantity(quantity);
        this.color = color;
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }
}
