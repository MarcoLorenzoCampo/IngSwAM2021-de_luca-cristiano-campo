package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.game.IGame;

public class TokenMove implements IToken{

    private final int moves;
    private final boolean shuffle;

    private final IGame game;

    public TokenMove(int moves, IGame game) {
        this.moves = moves;
        this.shuffle = false;
        this.game = game;
    }

    public TokenMove(IGame game) {
        this.moves = 1;
        this.shuffle = true;
        this.game = game;
    }

    /**
     * Method to increment Lorenzo's position on his faith track.
     */
    @Override
    public void tokenAction() {
        //make lorenzo move "moves" times and shuffle if "shuffle" is true;

    }
}
