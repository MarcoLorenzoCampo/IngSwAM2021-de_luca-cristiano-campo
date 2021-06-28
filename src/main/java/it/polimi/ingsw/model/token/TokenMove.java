package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.LorenzoPlayer;

public class TokenMove extends AbstractToken {

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
    public void tokenAction(LorenzoPlayer lorenzo) {
        //make lorenzo move "moves" times and shuffle if "shuffle" is true;
        for(int i=0; i<moves; i++) {
            lorenzo.getLorenzoPlayerBoard()
                    .getLorenzoFaithTrack()
                    .lorenzoIncreasesFaithMarker();
        }

        if(shuffle) {
            lorenzo.getLorenzoPlayerBoard().shuffleTokens();
        }
    }

    @Override
    public void graphicalDraw() {
        if(this.moves == 1){
        }
        else{
        }
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int getQuantity() {
        return moves;
    }

    @Override
    public String toString() {
        String reducedToken = "\n----------------------------------------------" +
                "\nLorenzo played a 'Move' token:" +
                "\nLorenzo moves: " + moves + " times!"
                + "\n----------------------------------------------\n";

        if(shuffle) {
            reducedToken = reducedToken.concat("\nLorenzo shuffled his token deck!");
        }

        return reducedToken;
    }
}
