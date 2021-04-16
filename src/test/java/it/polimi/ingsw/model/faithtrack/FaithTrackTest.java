package it.polimi.ingsw.model.faithtrack;

import it.polimi.ingsw.enumerations.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mario Cristiano
 */

class FaithTrackTest {

    private FaithTrack faithTrack;

    @BeforeEach
    void init(){
        faithTrack = new FaithTrack();
    }

    @Test
    void notNullTrack() {
        assertNotNull(faithTrack);
    }

    @Test
    void increaseFaithMarkerTest(){
        //Arrange
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.setFaithMarker(7);
        faithTrack.increaseFaithMarker();
        int currentFavorPoints = 2;
        int currentMarkerPosition = 8;

        assertEquals(currentFavorPoints, faithTrack.getCurrentFavorPoints());
        assertFalse(t1.getIsActive());
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
    }

    @Test
    void increaseFaithMarker2Test(){
        //Arrange
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.setFaithMarker(7);
        faithTrack.increaseFaithMarker();
        int currentFavorPoints = 2;
        int currentMarkerPosition = 8;

        //Assert
        assertEquals(currentFavorPoints, faithTrack.getCurrentFavorPoints());
        assertFalse(t1.getIsActive());
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
    }

    @Test
    void increaseFaithMarker3Test() {
        //Arrange
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);
        t1.setIsActive(false);

        //Act
        faithTrack.setFaithMarker(7);
        faithTrack.increaseFaithMarker();
        int currentFavorPoints = 0;
        int currentMarkerPosition = 8;

        //Assert
        assertEquals(currentFavorPoints, faithTrack.getCurrentFavorPoints());
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
    }

    @Test
    void calculationCheckPointsTest(){
        //Arrange
        Tile t1 = faithTrack.getFaithTrack().get(15);
        //Tile t1 = new PopeTile(15, Constants.ORANGE);
        Tile t2 = faithTrack.getFaithTrack().get(20);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        int currentPosition1 = faithTrack.calculationCheckPoints();
        faithTrack.setFaithMarker(t2.getIndex());
        int currentPosition2 = faithTrack.calculationCheckPoints();

        //Assert
        assertEquals(currentPosition1, 9);
        assertEquals(currentPosition2, 12);
    }

    @Test
    void pickFavorPointsTest1() {  //Considering that the player has taken all 3 favor points.
        //Arrange
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);
        //Tile t1 = new PopeTile(8, Constants.YELLOW);
        PopeTile t2 = (PopeTile) faithTrack.getFaithTrack().get(16);
        PopeTile t3 = (PopeTile) faithTrack.getFaithTrack().get(24);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);
        int result2 = faithTrack.pickFavorPoints(t2);
        int result3 = faithTrack.pickFavorPoints(t3);

        //Assert
        assertEquals(result1, 2);
        assertEquals(result2, 5);
        assertEquals(result3, 9);
    }

    @Test
    void pickFavorPoints2Test() { //Considering that the player has taken only the second favor tile
        //Arrange
        Tile t1 = new PopeTile(3, Constants.NEUTRAL);
        Tile t2 = new PopeTile(14, Constants.ORANGE);
        Tile t3 = new PopeTile(18, Constants.NEUTRAL);

        //Act
        int result1 = faithTrack.pickFavorPoints((PopeTile) t1);
        int result2 = faithTrack.pickFavorPoints((PopeTile) t2);
        int result3 = faithTrack.pickFavorPoints((PopeTile) t3);

        //Assert
        assertEquals(result1, 0);
        assertEquals(result2, 3);
        assertEquals(result3, 3);
    }

    @Test
    void pickFavorPoints3Test() {//Considering that the player has taken first and third favor tiles
        //Arrange
        Tile t1 = new PopeTile(6, Constants.YELLOW);
        Tile t2 = new PopeTile(9, Constants.NEUTRAL);
        Tile t3 = new PopeTile(23, Constants.RED);

        //Act
        int result1 = faithTrack.pickFavorPoints((PopeTile) t1);
        int result2 = faithTrack.pickFavorPoints((PopeTile) t2);
        int result3 = faithTrack.pickFavorPoints((PopeTile) t3);

        //Assert
        assertEquals(result1, 2);
        assertEquals(result2, 2);
        assertEquals(result3, 6);
    }

    @Test
    void pickFavorPoints4Test() {//Considering that the player hasn't taken any points
        //Arrange
        Tile t1 = new PopeTile(3, Constants.NEUTRAL);
        Tile t2 = new PopeTile(10, Constants.NEUTRAL);
        Tile t3 = new PopeTile(17, Constants.NEUTRAL);

        //Act
        int result1 = faithTrack.pickFavorPoints((PopeTile) t1);
        int result2 = faithTrack.pickFavorPoints((PopeTile) t2);
        int result3 = faithTrack.pickFavorPoints((PopeTile) t3);

        //Assert
        assertEquals(result1, 0);
        assertEquals(result2, 0);
        assertEquals(result3, 0);
    }

    @Test //need other classes
    void sendControlTest(){ }

    @Test //in this case the player is in the Vatican space
    void receiveControlTest(){
        //Arrange
        Tile t1 = faithTrack.getFaithTrack().get(6);
        PopeTile t2 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        faithTrack.receiveControl(t2);

        //Assert
        assertEquals(faithTrack.getCurrentFavorPoints(), 2);

    }

    @Test //in this case the player isn't in the Vatican space
    void receiveControlTest2(){
        //Arrange
        Tile t1 = faithTrack.getFaithTrack().get(3);
        PopeTile t2 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        faithTrack.receiveControl(t2);

        //Assert
        assertEquals(faithTrack.getCurrentFavorPoints(), 0);

    }

    @Test
    void isPopeTileTest() {
        //Arrange
        Tile t1 = new PopeTile(8, Constants.YELLOW);
        Tile t2 = new PopeTile(9, Constants.NEUTRAL);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        boolean popeTile1 = faithTrack.isPopeTile(faithTrack.getFaithMarker());
        faithTrack.setFaithMarker(t2.getIndex());
        boolean simpleTile1 = faithTrack.isPopeTile(faithTrack.getFaithMarker());

        //Assert
        assertTrue(popeTile1);
        assertFalse(simpleTile1);
    }

    @Test
    void calculationFinalPointsTest(){
        //Arrange
        Tile t1 = faithTrack.getFaithTrack().get(10);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        faithTrack.calculationCheckPoints(); // it could be return 4 points
        faithTrack.setCurrentFavorPoints(5);
        faithTrack.calculationFinalPoints();

        //Assert
        assertEquals(faithTrack.getFinalPoints(), 9);

    }

    @Test
    void calculationFinalPointsTest2(){
        //Arrange
        Tile t1 = faithTrack.getFaithTrack().get(19);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        faithTrack.calculationCheckPoints(); // it could be return 4 points
        faithTrack.setCurrentFavorPoints(2);
        faithTrack.calculationFinalPoints();

        //Assert
        assertEquals(faithTrack.getFinalPoints(), 14);

    }


}
