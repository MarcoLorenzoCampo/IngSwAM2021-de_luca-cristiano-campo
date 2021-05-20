package it.polimi.ingsw.model.faithtrack;

import java.io.Serializable;

public class PopeTile extends Tile implements Serializable {

    private static final long serialVersionUID = 4115472816035318109L;
    private int vaticanReportRange;
    boolean isActive;
    private int popeFavorPoints;
    private int checkpoint;

    public PopeTile(int index,int vaticanSpace, int checkpoint) {
        super(index, vaticanSpace, checkpoint);
        isActive = true;
    }

    public int getVaticanReportRange() {
        return vaticanReportRange;
    }

    public void setVaticanReportRange(int vaticanReportRange) {
        this.vaticanReportRange = vaticanReportRange;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public int getPopeFavorPoints() {
        return popeFavorPoints;
    }

    public void setPopeFavorPoints(int popeFavorPoints) {
        this.popeFavorPoints = popeFavorPoints;
    }
}
