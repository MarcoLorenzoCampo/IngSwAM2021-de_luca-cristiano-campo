package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;

public class DevelopmentTag extends Tag {
    private Color color;
    private Level level;

    public DevelopmentTag(int quantity, Color color, Level level) {
        this.color = color;
        this.level = level;
    }
}
