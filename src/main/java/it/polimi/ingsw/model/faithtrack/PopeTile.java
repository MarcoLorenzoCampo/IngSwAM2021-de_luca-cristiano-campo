package it.polimi.ingsw.model.faithtrack;

public class PopeTile extends Tile {
    private int vaticanReportRange;
    boolean isActive;
    private int popeFavorPoints;


    public PopeTile(int index,int vaticanSpace) {
        super(index, vaticanSpace);
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
