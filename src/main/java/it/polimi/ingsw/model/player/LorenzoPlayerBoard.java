package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.token.IToken;
import it.polimi.ingsw.model.token.LorenzoTokenPile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LorenzoPlayerBoard extends PlayerBoard{
    private final FaithTrack lorenzoFaithTrack;
    private final LorenzoTokenPile lorenzoActions;

    public LorenzoPlayerBoard() throws FileNotFoundException {
        lorenzoActions = new LorenzoTokenPile();
        lorenzoFaithTrack = new FaithTrack();
    }

    public FaithTrack getLorenzoFaithTrack() {
        return lorenzoFaithTrack;
    }

    public LorenzoTokenPile getLorenzoActionToken() {
        return lorenzoActions;
    }

    public void lorenzoMoves(int moves) {
        for(int i=0; i<moves; i++) {
            lorenzoFaithTrack.increaseFaithMarker();
        }
    }
}
