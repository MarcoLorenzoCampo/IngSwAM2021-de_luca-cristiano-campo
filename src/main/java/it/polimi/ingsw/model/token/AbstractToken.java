package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.player.LorenzoPlayer;

public abstract class AbstractToken {

    abstract void tokenAction(LorenzoPlayer lorenzo);
    public abstract void graphicalDraw();
    public abstract Color getColor();
    public abstract  int getQuantity();
}
