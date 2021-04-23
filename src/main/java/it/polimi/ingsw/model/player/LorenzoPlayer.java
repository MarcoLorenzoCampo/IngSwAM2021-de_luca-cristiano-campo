package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

public class LorenzoPlayer {

    private final String name;
    private final FaithTrack faithTrack;
    private final LorenzoPlayerBoard lorenzoPlayerBoard;

    public LorenzoPlayer() {
        this.name = "__LorenzoIsABot__";
        faithTrack = new FaithTrack();
        lorenzoPlayerBoard = new LorenzoPlayerBoard();
    }

    public String getName() {
        return name;
    }
}
