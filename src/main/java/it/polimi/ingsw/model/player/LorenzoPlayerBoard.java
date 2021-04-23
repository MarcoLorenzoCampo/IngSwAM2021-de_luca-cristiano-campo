package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.token.LorenzoTokenPile;

import java.util.Collections;

public class LorenzoPlayerBoard {
    private final FaithTrack lorenzoFaithTrack;
    private final LorenzoTokenPile lorenzoActions;

    public LorenzoPlayerBoard() {
        lorenzoActions = new LorenzoTokenPile();
        lorenzoFaithTrack = new FaithTrack();
    }

    public void getAction(LorenzoAction toPerform) {
        toPerform.isValid();
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

    public FaithTrack getFaithTrack() {
        return lorenzoFaithTrack;
    }

    public void shuffleTokens() {
        Collections.shuffle(lorenzoActions.getLorenzoTokens());
    }
}
