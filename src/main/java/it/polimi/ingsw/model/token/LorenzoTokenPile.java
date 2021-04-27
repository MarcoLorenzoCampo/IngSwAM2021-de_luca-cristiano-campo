package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.utilities.builders.LorenzoTokensBuilder;

import java.util.List;

public class LorenzoTokenPile {

    private int lastTaken;
    private final List<IToken> lorenzoTokens;

    public LorenzoTokenPile() {
        lorenzoTokens = LorenzoTokensBuilder.build();
        lastTaken = 0;
    }

    public void performTokenAction(LorenzoPlayer lorenzo) {
        lorenzoTokens.get(lastTaken%lorenzoTokens.size()).tokenAction(lorenzo);
        lastTaken++;
    }

    public List<IToken> getLorenzoTokens() {
        return lorenzoTokens;
    }

    public int getLastTaken() {
        return lastTaken;
    }
}
