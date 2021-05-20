package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.LorenzoPlayer;

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
    public void tokenAction(LorenzoPlayer lorenzo) {
        //make lorenzo move "moves" times and shuffle if "shuffle" is true;
        for(int i=0; i<moves; i++) {
            lorenzo.getLorenzoPlayerBoard()
                    .getLorenzoFaithTrack()
                    .increaseFaithMarker();
        }

        if(shuffle) {
            lorenzo.getLorenzoPlayerBoard().shuffleTokens();
        }
    }

    @Override
    public String graphicalDraw() {
        if(this.moves == 1){
            return "1 \uD83D\uDD42 \uD83D\uDD04";
        }
        else{
            return "2 \uD83D\uDD42";
        }
    }
}
