package it.polimi.ingsw.model.faithtrack;

import java.io.Serializable;

public class Tile implements Serializable {
    private static final long serialVersionUID = -1082270924910375418L;
    private int index;
    private final int vaticanSpace;
    private final int checkpoint;

    /**
     * @param index:        index for each of the 24 cards ;
     * @param vaticanSpace: there are 3 vatican spaces;
     * @param checkpoint:   checkpoint for each of the 24 cards
     */
    public Tile(int index, int vaticanSpace, int checkpoint) {
        this.index = index;
        this.vaticanSpace = vaticanSpace;
        this.checkpoint = checkpoint;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getVaticanSpace() {
        return vaticanSpace;
    }

    public int getCheckpoint() {
        return checkpoint;
    }
}