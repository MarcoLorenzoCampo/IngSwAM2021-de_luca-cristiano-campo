package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.model.token.AbstractToken;

public class GraphicalToken {

    private static final int MAX_VERT_TILES = 3; //rows.
    private static final int MAX_HORIZ_TILES = 3; //cols.
    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    private AbstractToken lorenzoAction;


    public GraphicalToken(AbstractToken lorenzoAction){
        this.lorenzoAction = lorenzoAction;
    }

    public void draw(){
        lorenzoAction.graphicalDraw();
    }


}
