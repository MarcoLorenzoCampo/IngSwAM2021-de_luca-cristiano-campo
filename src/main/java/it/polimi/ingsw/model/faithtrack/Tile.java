package it.polimi.ingsw.model.faithtrack;

import java.io.Serializable;

public class Tile implements Serializable {
    private static final long serialVersionUID = -1082270924910375418L;
    private int index;
    private int vaticanSpace;
    private int checkpoint;

    /**
     * @param index:        there are 24 tiles;
     * @param vaticanSpace: there are 3 vatican spaces;
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

    public void setVaticanSpace(int vaticanSpace) {
        this.vaticanSpace = vaticanSpace;
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }
}