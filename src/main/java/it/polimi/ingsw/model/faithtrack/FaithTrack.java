package it.polimi.ingsw.model.faithtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.*;
import java.util.Map;


public class FaithTrack {

    List<Tile> faithTrack = new ArrayList<>();
    private int finalPoints;
    private int faithMarker;
    private int currentFavorPoints;
    public Map<Integer, Integer> cardVaticanSpace = new HashMap<Integer, Integer>();

    List<Integer> checkpoints = new ArrayList<>();

    public FaithTrack() {
        this.faithMarker=0;
        this.currentFavorPoints=0;
        this.finalPoints=0;
        initFaithTrack();
        initCheckpoint();
        initCardVaticanSpace();
    }


    private void initFaithTrack() {
        faithTrack.add(new SimpleTile(0, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(1, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(2, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(3, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(4, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(5, Constants.YELLOW));
        faithTrack.add(new SimpleTile(6, Constants.YELLOW));
        faithTrack.add(new SimpleTile(7, Constants.YELLOW));
        faithTrack.add(new PopeTile(8, Constants.YELLOW));
        faithTrack.add(new SimpleTile(9, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(10, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(11, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(12, Constants.ORANGE));
        faithTrack.add(new SimpleTile(13, Constants.ORANGE));
        faithTrack.add(new SimpleTile(14, Constants.ORANGE));
        faithTrack.add(new SimpleTile(15, Constants.ORANGE));
        faithTrack.add(new PopeTile(16, Constants.ORANGE));
        faithTrack.add(new SimpleTile(17, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(18, Constants.NEUTRAL));
        faithTrack.add(new SimpleTile(19, Constants.RED));
        faithTrack.add(new SimpleTile(20, Constants.RED));
        faithTrack.add(new SimpleTile(21, Constants.RED));
        faithTrack.add(new SimpleTile(22, Constants.RED));
        faithTrack.add(new SimpleTile(23, Constants.RED));
        faithTrack.add(new PopeTile(24, Constants.RED));
    }

    private void initCheckpoint() {
        checkpoints.add(0);
        checkpoints.add(1);
        checkpoints.add(2);
        checkpoints.add(4);
        checkpoints.add(6);
        checkpoints.add(9);
        checkpoints.add(12);
        checkpoints.add(16);
        checkpoints.add(20);
    }

    /**
     * @param n: faithmarker advances n times;
     *         it checks the type of tile;
     *         it takes favorpoints;
     *         it eventually activates vatican reports
     */
    public void increaseFaithMarker(int n){
        for(int i=0; i<n; i++) {
            this.faithMarker++;
            if(isPopeTile(faithMarker)) {
                PopeTile currentTile = (PopeTile) this.faithTrack.get(faithMarker);
                if(currentTile.getIsActive()){
                    pickFavorPoints(currentTile);
                    sendControl(currentTile);
                    currentTile.setIsActive(false);
                }
            }
        }
    }

    //using of the map and of class constants
    private void initCardVaticanSpace(){
        cardVaticanSpace.put(Constants.NEUTRAL, 0);
        cardVaticanSpace.put(Constants.YELLOW, 2);
        cardVaticanSpace.put(Constants.ORANGE, 3);
        cardVaticanSpace.put(Constants.RED, 4);
    }

    /**
     * @return points due to checkpoints
     */
    public int checkMarkerPosition(){
        double faithMarkerDouble = this.faithMarker/3;
        double faithMarkerFloor = Math.floor(faithMarkerDouble);
        return checkpoints.get((int)(faithMarkerFloor));
    }

    //updating favor points
    public int pickFavorPoints(PopeTile popeT){
        int indexVaticanSpace = popeT.getVaticanSpace();
        int points = this.cardVaticanSpace.get(indexVaticanSpace);
        currentFavorPoints = currentFavorPoints + points;
        return currentFavorPoints;
    }

    //comparison between players' faithmarker
    public void sendControl(PopeTile popeT){
        // need other classes
    }
    //control that the player is in the Vatican space during Vatican report to earn points
    public void receiveControl(PopeTile popeT){
        if(this.faithTrack.get(this.faithMarker).getVaticanSpace() == popeT.getVaticanSpace()){
            pickFavorPoints(popeT);
        }
    }

    public boolean isPopeTile(int faithMarker){
        return (faithMarker == 8) || (faithMarker == 16) || (faithMarker == 24);
    }

    public List<Tile> getFaithTrack() {
        return faithTrack;
    }

    public void setFaithTrack(List<Tile> faithTrack) {
        this.faithTrack = faithTrack;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    public void setFaithMarker(int faithMarker) {
        this.faithMarker = faithMarker;
    }

    public int getCurrentFavorPoints() {
        return currentFavorPoints;
    }

    public void setCurrentFavorPoints(int currentFavorPoints) {
        this.currentFavorPoints = currentFavorPoints;
    }

    public List<Integer> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Integer> checkpoints) {
        this.checkpoints = checkpoints;
    }
}
