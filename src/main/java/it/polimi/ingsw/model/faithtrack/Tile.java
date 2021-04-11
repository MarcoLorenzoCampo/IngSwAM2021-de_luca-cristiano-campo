package it.polimi.ingsw.model.faithtrack;

public class Tile {
    private int index;
    private int vaticanSpace;

    /**
     * @param index: there are 24 tiles;
     * @param vaticanSpace: there are 3 vatican spaces;
     */
    public Tile(int index, int vaticanSpace) {
        this.index = index;
        this.vaticanSpace = vaticanSpace;
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
}