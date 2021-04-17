package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.utilities.builders.LorenzoTokensBuilder;

import java.io.FileNotFoundException;
import java.util.List;

public class LorenzoTokenPile {

    List<IToken> lorenzoTokens;

    public LorenzoTokenPile() throws FileNotFoundException {
        lorenzoTokens = LorenzoTokensBuilder.build();
    }

    public List<IToken> getLorenzoTokens() {
        return lorenzoTokens;
    }
}
