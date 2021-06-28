package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.token.AbstractToken;
import it.polimi.ingsw.model.token.TokenDiscard;
import it.polimi.ingsw.model.token.TokenMove;

import java.util.ArrayList;

public final class LorenzoTokensBuilder {

    private final static IGame game = PlayingGame.getGameInstance();

    public static ArrayList<AbstractToken> build() {
        ArrayList<AbstractToken> builtList = new ArrayList<>();

        builtList.add(new TokenDiscard(Color.PURPLE, game));
        builtList.add(new TokenDiscard(Color.BLUE, game));
        builtList.add(new TokenDiscard(Color.GREEN, game));
        builtList.add(new TokenDiscard(Color.YELLOW, game));
        builtList.add(new TokenMove(2, game));
        builtList.add(new TokenMove(2, game));
        builtList.add(new TokenMove(game));

        //Collections.shuffle(builtList);
        return builtList;
    }
}
