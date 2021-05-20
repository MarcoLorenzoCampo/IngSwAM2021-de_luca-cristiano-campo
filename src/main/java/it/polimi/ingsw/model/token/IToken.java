package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;

public interface IToken {

    void tokenAction(LorenzoPlayer lorenzo);
    String graphicalDraw();
}
