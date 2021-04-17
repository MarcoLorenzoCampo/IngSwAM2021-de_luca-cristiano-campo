package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.token.IToken;
import it.polimi.ingsw.model.token.TokenDiscard;
import it.polimi.ingsw.model.token.TokenMove;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public final class LorenzoTokensBuilder {

    public static ArrayList<IToken> build() throws FileNotFoundException {
        ArrayList<IToken> builtList = new ArrayList<>();

        builtList.add(new TokenDiscard(Color.PURPLE));
        builtList.add(new TokenDiscard(Color.BLUE));
        builtList.add(new TokenDiscard(Color.GREEN));
        builtList.add(new TokenDiscard(Color.YELLOW));
        builtList.add(new TokenMove(2));
        builtList.add(new TokenMove());

        Collections.shuffle(builtList);
        return builtList;
    }
}
