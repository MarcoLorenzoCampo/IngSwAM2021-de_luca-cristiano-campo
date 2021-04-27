package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

public class LorenzoPlayer {

    private final FaithTrack faithTrack;
    private final LorenzoPlayerBoard lorenzoPlayerBoard;

    public LorenzoPlayer() {
        String name = "__LorenzoIsABot__";
        faithTrack = new FaithTrack();
        lorenzoPlayerBoard = new LorenzoPlayerBoard();
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public LorenzoPlayerBoard getLorenzoPlayerBoard() {
        return lorenzoPlayerBoard;
    }
}
