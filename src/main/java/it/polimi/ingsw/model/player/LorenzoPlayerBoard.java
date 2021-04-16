package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.token.IToken;

import java.util.ArrayList;
import java.util.List;

public class LorenzoPlayerBoard extends PlayerBoard{
    private final FaithTrack lorenzoFaithTrack;
    private final List<IToken> lorenzoActionToken;

    public LorenzoPlayerBoard() {
        lorenzoActionToken = new ArrayList<>();
        lorenzoFaithTrack = new FaithTrack();
    }

    public FaithTrack getLorenzoFaithTrack() {
        return lorenzoFaithTrack;
    }

    public List<IToken> getLorenzoActionToken() {
        return lorenzoActionToken;
    }
}
