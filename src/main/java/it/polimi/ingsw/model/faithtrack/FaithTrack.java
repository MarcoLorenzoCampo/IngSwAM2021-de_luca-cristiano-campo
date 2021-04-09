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
        Tile tile0 = new SimpleTile(0, Constants.NEUTRAL);
        Tile tile1 = new SimpleTile(1, Constants.NEUTRAL);
        Tile tile2 = new SimpleTile(2, Constants.NEUTRAL);
        Tile tile3 = new SimpleTile(3, Constants.NEUTRAL);
        Tile tile4 = new SimpleTile(4, Constants.NEUTRAL);
        Tile tile5 = new SimpleTile(5, Constants.YELLOW);
        Tile tile6 = new SimpleTile(6, Constants.YELLOW);
        Tile tile7 = new SimpleTile(7, Constants.YELLOW);
        Tile tile8 = new PopeTile(8, Constants.YELLOW);
        Tile tile9 = new SimpleTile(9, Constants.NEUTRAL);
        Tile tile10 = new SimpleTile(10, Constants.NEUTRAL);
        Tile tile11 = new SimpleTile(11, Constants.NEUTRAL);
        Tile tile12 = new SimpleTile(12, Constants.ORANGE);
        Tile tile13 = new SimpleTile(13, Constants.ORANGE);
        Tile tile14 = new SimpleTile(14, Constants.ORANGE);
        Tile tile15 = new SimpleTile(15, Constants.ORANGE);
        Tile tile16 = new PopeTile(16, Constants.ORANGE);
        Tile tile17 = new SimpleTile(17, Constants.NEUTRAL);
        Tile tile18 = new SimpleTile(18, Constants.NEUTRAL);
        Tile tile19 = new SimpleTile(19, Constants.RED);
        Tile tile20 = new SimpleTile(20, Constants.RED);
        Tile tile21 = new SimpleTile(21, Constants.RED);
        Tile tile22 = new SimpleTile(22, Constants.RED);
        Tile tile23 = new SimpleTile(23, Constants.RED);
        Tile tile24 = new PopeTile(24, Constants.RED);

        faithTrack.add(tile0);
        faithTrack.add(tile1);
        faithTrack.add(tile2);
        faithTrack.add(tile3);
        faithTrack.add(tile4);
        faithTrack.add(tile5);
        faithTrack.add(tile6);
        faithTrack.add(tile7);
        faithTrack.add(tile8);
        faithTrack.add(tile9);
        faithTrack.add(tile10);
        faithTrack.add(tile11);
        faithTrack.add(tile12);
        faithTrack.add(tile13);
        faithTrack.add(tile14);
        faithTrack.add(tile15);
        faithTrack.add(tile16);
        faithTrack.add(tile17);
        faithTrack.add(tile18);
        faithTrack.add(tile19);
        faithTrack.add(tile20);
        faithTrack.add(tile21);
        faithTrack.add(tile22);
        faithTrack.add(tile23);
        faithTrack.add(tile24);


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
        if((faithMarker == 8) || (faithMarker == 16) || (faithMarker == 24)){
            return true;
        }
        return false;
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
