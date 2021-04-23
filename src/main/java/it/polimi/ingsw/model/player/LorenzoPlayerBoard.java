package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.token.LorenzoTokenPile;

import java.util.Collections;

public class LorenzoPlayerBoard extends PlayerBoard {
    private final FaithTrack lorenzoFaithTrack;
    private final LorenzoTokenPile lorenzoActions;

    public LorenzoPlayerBoard() {
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

    @Override
    public FaithTrack getFaithTrack() {
        return lorenzoFaithTrack;
    }

    @Override
    public ProductionBoard getProductionBoard() {
        return null;
    }

    public void shuffleTokens() {
        Collections.shuffle(lorenzoActions.getLorenzoTokens());
    }
}
