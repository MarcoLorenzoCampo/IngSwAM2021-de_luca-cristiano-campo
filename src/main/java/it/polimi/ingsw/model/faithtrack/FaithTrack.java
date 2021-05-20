package it.polimi.ingsw.model.faithtrack;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.model.player.RealPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.*;
import java.util.Map;


public class FaithTrack {

    GameManager gameManager;

    List<Tile> faithTrack = new ArrayList<>();
    private int finalPoints;
    private int faithMarker;
    private int currentFavorPoints;
    public Map<Integer, Integer> cardVaticanSpace = new HashMap<>();

    List<Integer> checkpoints = new ArrayList<>();

    public FaithTrack() {
        this.faithMarker=0;
        this.currentFavorPoints=0;
        this.finalPoints=0;
        initFaithTrack();
        initCheckpoint();
        initCardVaticanSpace();
        //this.gameManager = gameManager;
    }


    private void initFaithTrack() {
        faithTrack.add(new SimpleTile(0, Constants.NEUTRAL, 0));
        faithTrack.add(new SimpleTile(1, Constants.NEUTRAL, 0));
        faithTrack.add(new SimpleTile(2, Constants.NEUTRAL, 0));
        faithTrack.add(new SimpleTile(3, Constants.NEUTRAL, 1));
        faithTrack.add(new SimpleTile(4, Constants.NEUTRAL, 1));
        faithTrack.add(new SimpleTile(5, Constants.YELLOW, 1));
        faithTrack.add(new SimpleTile(6, Constants.YELLOW, 2));
        faithTrack.add(new SimpleTile(7, Constants.YELLOW, 2));
        faithTrack.add(new PopeTile(8, Constants.YELLOW, 2));
        faithTrack.add(new SimpleTile(9, Constants.NEUTRAL, 4));
        faithTrack.add(new SimpleTile(10, Constants.NEUTRAL, 4));
        faithTrack.add(new SimpleTile(11, Constants.NEUTRAL, 4));
        faithTrack.add(new SimpleTile(12, Constants.ORANGE, 6));
        faithTrack.add(new SimpleTile(13, Constants.ORANGE, 6));
        faithTrack.add(new SimpleTile(14, Constants.ORANGE, 6));
        faithTrack.add(new SimpleTile(15, Constants.ORANGE, 9));
        faithTrack.add(new PopeTile(16, Constants.ORANGE, 9));
        faithTrack.add(new SimpleTile(17, Constants.NEUTRAL, 9));
        faithTrack.add(new SimpleTile(18, Constants.NEUTRAL, 12));
        faithTrack.add(new SimpleTile(19, Constants.RED, 12));
        faithTrack.add(new SimpleTile(20, Constants.RED, 12));
        faithTrack.add(new SimpleTile(21, Constants.RED, 16));
        faithTrack.add(new SimpleTile(22, Constants.RED, 16));
        faithTrack.add(new SimpleTile(23, Constants.RED, 16));
        faithTrack.add(new PopeTile(24, Constants.RED, 20));
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
     * it checks the type of tile;
     * if the player is the first to enter in a Vatican space, the player makes it inactive
     * it eventually takes favor points;
     * it eventually activates vatican reports.
     */
    public void increaseFaithMarker(){
        this.faithMarker++;
        if(isPopeTile(faithMarker)) {
            PopeTile currentTile = (PopeTile) this.faithTrack.get(faithMarker);
            if(currentTile.getIsActive()){
                pickFavorPoints(currentTile);
                checkOtherPlayerPosition(currentTile);
                ((PopeTile) this.faithTrack.get(faithMarker)).setIsActive(false);
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
    public int calculationCheckPoints(){

        return faithTrack.get(this.faithMarker).getCheckpoint();

    }

    //updating favor points
    public int pickFavorPoints(PopeTile popeT){
        int indexVaticanSpace = popeT.getVaticanSpace();
        int points = this.cardVaticanSpace.get(indexVaticanSpace);
        currentFavorPoints = currentFavorPoints + points;
        return currentFavorPoints;
    }








    //comparison between players' faith marker
    public void checkOtherPlayerPosition(PopeTile popeT) {
        /*List<RealPlayer> temp = gameManager.getLobbyManager().getRealPlayerList();

        for(RealPlayer player : temp) {
            player.getPlayerBoard().getFaithTrack().receiveControl(popeT);
        }*/
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

    public void calculationFinalPoints(){
        this.finalPoints = this.currentFavorPoints + calculationCheckPoints();
    }

    public List<Tile> getFaithTrack() {
        return faithTrack;
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

    public int getFinalPoints() {
        return finalPoints;
    }

    public void setFinalPoints(int finalPoints) {
        this.finalPoints = finalPoints;
    }

    public Map<Integer, Integer> getCardVaticanSpace() {
        return cardVaticanSpace;
    }

    public void setCardVaticanSpace(Map<Integer, Integer> cardVaticanSpace) {
        this.cardVaticanSpace = cardVaticanSpace;
    }
}
