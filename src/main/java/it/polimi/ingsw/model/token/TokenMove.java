package it.polimi.ingsw.model.token;

public class TokenMove implements IToken{

    int moves;
    boolean shuffle;

    public TokenMove(int moves) {
        this.moves = moves;
        this.shuffle = false;
    }

    public TokenMove() {
        this.moves = 1;
        this.shuffle = true;
    }

    /**
     * Method to increment Lorenzo's position on his faith track.
     */
    @Override
    public void tokenAction() {

    }
}
