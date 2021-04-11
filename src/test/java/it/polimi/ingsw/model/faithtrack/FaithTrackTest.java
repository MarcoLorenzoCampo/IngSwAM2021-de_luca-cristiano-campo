package it.polimi.ingsw.model.faithtrack;

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
    void checkMarkerPositionTest(){
        //Arrange
        Tile t1 = new PopeTile(15, Constants.ORANGE);
        Tile t2 = new PopeTile(20, Constants.NEUTRAL);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        int result1 = faithTrack.checkMarkerPosition();
        faithTrack.setFaithMarker(t2.getIndex());
        int result2 = faithTrack.checkMarkerPosition();

        //Assert
        assertEquals(result1, 9);
        assertEquals(result2, 12);
    }

    @Test
    void pickFavorPointsTest() {
        //Arrange
        PopeTile t1 = new PopeTile(8, Constants.YELLOW);
        PopeTile t2 = new PopeTile(16, Constants.ORANGE);
        PopeTile t3 = new PopeTile(24, Constants.RED);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);
        int result2 = faithTrack.pickFavorPoints(t2);
        int result3 = faithTrack.pickFavorPoints(t3);

        //Considering that the player has taken all 3 favor points.
        //Assert
        assertEquals(result1, 2);
        assertEquals(result2, 5);
        assertEquals(result3, 9);
    }

    @Test
    void pickFavorPoints2Test() {
        //Arrange
        PopeTile t1 = new PopeTile(3, Constants.NEUTRAL);
        PopeTile t2 = new PopeTile(14, Constants.ORANGE);
        PopeTile t3 = new PopeTile(18, Constants.NEUTRAL);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);
        int result2 = faithTrack.pickFavorPoints(t2);
        int result3 = faithTrack.pickFavorPoints(t3);

        //Considering that the player has taken only the second favor tile
        //Assert
        assertEquals(result1, 0);
        assertEquals(result2, 3);
        assertEquals(result3, 3);
    }

    @Test
    void pickFavorPoints3Test() {
        //Arrange
        PopeTile t1 = new PopeTile(6, Constants.YELLOW);
        PopeTile t2 = new PopeTile(9, Constants.NEUTRAL);
        PopeTile t3 = new PopeTile(23, Constants.RED);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);
        int result2 = faithTrack.pickFavorPoints(t2);
        int result3 = faithTrack.pickFavorPoints(t3);

        //Considering that the player has taken first and third favor tiles
        //Assert
        assertEquals(result1, 2);
        assertEquals(result2, 2);
        assertEquals(result3, 6);
    }

    @Test //need other classes
    void sendControlTest(){ }

    @Test //need other classes
    void receiveControlTest(){ }

    @Test
    void isPopeTileTest() {
        //Arrange
        Tile t1 = new PopeTile(8, Constants.YELLOW);
        Tile t2 = new PopeTile(9, Constants.NEUTRAL);

        //Act
        faithTrack.setFaithMarker(t1.getIndex());
        boolean result1 = faithTrack.isPopeTile(faithTrack.getFaithMarker());
        faithTrack.setFaithMarker(t2.getIndex());
        boolean result2 = faithTrack.isPopeTile(faithTrack.getFaithMarker());

        //Assert
        assertTrue(result1);
        assertFalse(result2);
    }


}
