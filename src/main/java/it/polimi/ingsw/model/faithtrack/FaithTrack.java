package it.polimi.ingsw.model.faithtrack;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.EndGameMessage;
import it.polimi.ingsw.network.messages.serverMessages.FaithTrackMessage;
import it.polimi.ingsw.network.messages.serverMessages.GenericMessageFromServer;
import it.polimi.ingsw.network.messages.serverMessages.VaticanReportNotification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.*;
import java.util.Map;


public class FaithTrack extends Observable implements Serializable {

    private static final long serialVersionUID = 7136541748211952620L;

    private final List<Tile> faithTrack = new ArrayList<>();
    private int finalPoints;
    private int faithMarker;
    private int currentFavorPoints;
    public Map<Integer, Integer> cardVaticanSpace = new HashMap<>();

    private final List<Integer> checkpoints = new ArrayList<>();

    private final Map<Integer, Integer> ranges = new HashMap<>();

    public FaithTrack() {
        this.faithMarker=23;
        this.currentFavorPoints=0;
        this.finalPoints=0;
        initFaithTrack();
        initCheckpoint();
        initCardVaticanSpace();
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
        if(faithMarker == 24) notifyObserver(new EndGameMessage());

        //Notify all observers, but only the clients will get an updated version.
        notifyObserver(new FaithTrackMessage(this));

        if(isPopeTile(faithMarker)) {

            PopeTile currentTile = (PopeTile) this.faithTrack.get(faithMarker);

            if(currentTile.getIsActive()) {

                pickFavorPoints(currentTile);

                ((PopeTile) this.faithTrack.get(faithMarker)).setIsActive(false);

                //Notifying the controller he needs to start a vatican report session.
                notifyObserver(new VaticanReportNotification(faithMarker, ranges.get(faithMarker)));
            }
        }
    }

    //using of the map and of class constants
    private void initCardVaticanSpace(){
        cardVaticanSpace.put(Constants.NEUTRAL, 0);
        cardVaticanSpace.put(Constants.YELLOW, 2);
        cardVaticanSpace.put(Constants.ORANGE, 3);
        cardVaticanSpace.put(Constants.RED, 4);

        ranges.put(8, 3);
        ranges.put(16, 4);
        ranges.put(24, 5);
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

        notifyObserver(new GenericMessageFromServer("You gained: " + points +
                " points from the latest vatican report!" + "\nYour vatican score is: " + currentFavorPoints + "\n"));

        return currentFavorPoints;
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

    public void setPopeTileInactive(int index) {

        notifyObserver(new GenericMessageFromServer("You didn't gain any points from this vatican report!"));

        PopeTile pt = (PopeTile)faithTrack.get(index);
        pt.setIsActive(false);
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

    public int getFinalPoints() {
        return finalPoints;
    }
}
