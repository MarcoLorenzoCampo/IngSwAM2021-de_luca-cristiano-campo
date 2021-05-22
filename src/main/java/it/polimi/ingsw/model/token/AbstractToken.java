package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;

import java.io.Serializable;

public abstract class AbstractToken implements Serializable {

    private static final long serialVersionUID = 3983412867417513884L;

    abstract void tokenAction(LorenzoPlayer lorenzo);
    public abstract String graphicalDraw();
}
