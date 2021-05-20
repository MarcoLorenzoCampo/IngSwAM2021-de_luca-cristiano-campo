package it.polimi.ingsw.model.faithtrack;

import java.io.Serializable;

public class SimpleTile extends Tile implements Serializable {

    private static final long serialVersionUID = -8019587487474198661L;

    public SimpleTile(int index, int vaticanSpace, int checkpoint) {
        super(index, vaticanSpace, checkpoint);
    }
}
