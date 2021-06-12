package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.token.LorenzoTokenPile;
import it.polimi.ingsw.network.eventHandlers.Observable;

import java.util.Collections;

/**
 * Lorenzo's playerBoard, used to perform his signature actions.
 */
public class LorenzoPlayerBoard extends Observable {

    private final FaithTrack lorenzoFaithTrack;
    private final LorenzoTokenPile lorenzoTokenPile;

    public LorenzoPlayerBoard() {
        lorenzoTokenPile = new LorenzoTokenPile();
        lorenzoFaithTrack = new FaithTrack();
    }

    public void getAction(LorenzoAction toPerform) {
        toPerform.isValid();
    }

    public FaithTrack getLorenzoFaithTrack() {
        return lorenzoFaithTrack;
    }

    public LorenzoTokenPile getLorenzoTokenPile() {
        return lorenzoTokenPile;
    }

    public void shuffleTokens() {
        Collections.shuffle(lorenzoTokenPile.getLorenzoTokens());
    }
}
