package it.polimi.ingsw.model.faithtrack;

import java.io.Serializable;

public class PopeTile extends Tile implements Serializable {

    private static final long serialVersionUID = 4115472816035318109L;
    boolean isActive;

    public PopeTile(int index,int vaticanSpace, int checkpoint) {
        super(index, vaticanSpace, checkpoint);
        isActive = true;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
