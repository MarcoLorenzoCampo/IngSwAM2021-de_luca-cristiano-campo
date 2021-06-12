package it.polimi.ingsw.model.player;

public class LorenzoPlayer {

    private final LorenzoPlayerBoard lorenzoPlayerBoard;

    public LorenzoPlayer() {
        lorenzoPlayerBoard = new LorenzoPlayerBoard();
    }

    public LorenzoPlayerBoard getLorenzoPlayerBoard() {
        return lorenzoPlayerBoard;
    }

    /**
     * @return position on the faith track
     */
    public int getFaithPosition() {
        return lorenzoPlayerBoard.getLorenzoFaithTrack().getFaithMarker();
    }
}
