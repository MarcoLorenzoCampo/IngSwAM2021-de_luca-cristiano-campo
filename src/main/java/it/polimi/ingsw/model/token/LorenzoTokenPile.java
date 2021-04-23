package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.utilities.builders.LorenzoTokensBuilder;

import java.util.List;

public class LorenzoTokenPile {

    List<IToken> lorenzoTokens;

    public LorenzoTokenPile() {
        lorenzoTokens = LorenzoTokensBuilder.build();
    }

    public List<IToken> getLorenzoTokens() {
        return lorenzoTokens;
    }
}
