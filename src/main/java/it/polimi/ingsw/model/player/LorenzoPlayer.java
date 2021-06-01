package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

public class LorenzoPlayer {

    private final LorenzoPlayerBoard lorenzoPlayerBoard;

    public LorenzoPlayer() {
        lorenzoPlayerBoard = new LorenzoPlayerBoard();
    }

    public LorenzoPlayerBoard getLorenzoPlayerBoard() {
        return lorenzoPlayerBoard;
    }
}
