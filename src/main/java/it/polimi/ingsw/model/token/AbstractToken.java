package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.utilities.Reducible;

import java.io.Serializable;

public abstract class AbstractToken {

    abstract void tokenAction(LorenzoPlayer lorenzo);
    public abstract String graphicalDraw();
    public abstract Color getColor();
    public abstract  int getQuantity();
}
