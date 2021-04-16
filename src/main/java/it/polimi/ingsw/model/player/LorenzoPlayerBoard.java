package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.token.IToken;

import java.util.ArrayList;
import java.util.List;

public class LorenzoPlayerBoard extends PlayerBoard{
    private final FaithTrack lorenzoFaithTrack = new FaithTrack();
    private final List<IToken> lorenzoActionToken = new ArrayList<>();

}
