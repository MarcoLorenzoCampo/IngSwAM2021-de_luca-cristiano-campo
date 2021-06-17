package it.polimi.ingsw.model.faithtrack;

import it.polimi.ingsw.enumerations.Constants;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mario Cristiano
 */

class FaithTrackTest {

    private FaithTrack faithTrack;

    /**
     * Testing basic faith track methods.
     */
    @BeforeEach
    void init() {
        faithTrack = new FaithTrack();
    }

    @Test
    void notNullTrack() {
        assertNotNull(faithTrack);
    }

    /**
     *
     */
    @Test
    void increaseFaithMarker1Test() {
        //Arrange
        faithTrack.setFaithMarker(6);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 7;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertTrue(t1.getIsActive());
    }

    @Test
    void increaseFaithMarker2Test() {
        //Arrange
        faithTrack.setFaithMarker(7);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 8;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertFalse(t1.getIsActive());
    }

    @Test
    void increaseFaithMarker3Test() {
        //Arrange
        faithTrack.setFaithMarker(13);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(16);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 14;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertTrue(t1.getIsActive());
    }

    @Test
    void increaseFaithMarker4Test() {
        //Arrange
        faithTrack.setFaithMarker(15);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(16);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 16;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertFalse(t1.getIsActive());
    }

    @Test
    void increaseFaithMarker5Test() {
        //Arrange
        faithTrack.setFaithMarker(18);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(24);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 19;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertTrue(t1.getIsActive());
    }

    @Test
    void increaseFaithMarker6Test() {
        //Arrange
        faithTrack.setFaithMarker(23);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(24);

        //Act
        faithTrack.increaseFaithMarker();
        int currentMarkerPosition = 24;

        //Assert
        assertEquals(currentMarkerPosition, faithTrack.getFaithMarker());
        assertFalse(t1.getIsActive());
    }

    @Test
    void computeCheckpointsTest1(){
        //Arrange
        this.faithTrack.setFaithMarker(15);

        //Act
        int checkpoint = faithTrack.computeCheckpoints();

        //Assert
        assertEquals(checkpoint, 9);
    }

    @Test
    void computeCheckpointsTest2(){
        //Arrange
        this.faithTrack.setFaithMarker(22);

        //Act
        int checkpoint = faithTrack.computeCheckpoints();

        //Assert
        assertEquals(checkpoint, 16);
    }

    @Test
    void computeCheckpointsTest3(){
        //Arrange
        this.faithTrack.setFaithMarker(8);

        //Act
        int checkpoint = faithTrack.computeCheckpoints();

        //Assert
        assertEquals(checkpoint, 2);
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
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(16);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);

        //Assert
        assertEquals(result1, 3);
    }

    @Test
    void pickFavorPoints3Test() {//Considering that the player has taken first and third favor tiles
        //Arrange
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);
        //Tile t1 = new PopeTile(8, Constants.YELLOW);
        PopeTile t2 = (PopeTile) faithTrack.getFaithTrack().get(24);

        //Act
        int result1 = faithTrack.pickFavorPoints(t1);
        int result2 = faithTrack.pickFavorPoints(t2);

        //Assert
        assertEquals(result1, 2);
        assertEquals(result2, 6);
    }

    @Test //need other classes
    void sendControlTest(){ }

    @Test
    void setPopeTileInactiveTest(){
        //Arrange
        int popeTileIndex = 8;

        //Act
        faithTrack.setPopeTileInactive(popeTileIndex);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(popeTileIndex);

        //Assert
        assertFalse(t1.getIsActive());
    }

    @Test //in this case the player is in the Vatican space
    void receiveControlTest1(){
        //Arrange
        this.faithTrack.setFaithMarker(6);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        this.faithTrack.receiveControl(t1);

        //Assert
        assertEquals(this.faithTrack.getCurrentFavorPoints(), 2);

    }

    @Test //in this case the player isn't in the Vatican space
    void receiveControlTest2(){
        //Arrange
        this.faithTrack.setFaithMarker(3);
        PopeTile t1 = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        this.faithTrack.receiveControl(t1);

        //Assert
        assertEquals(this.faithTrack.getCurrentFavorPoints(), 0);

    }

    @Test
    void calculationFinalPointsTest1(){
        //Arrange
        faithTrack.setFaithMarker(10);

        //Act
        faithTrack.computeCheckpoints(); // it could be return 4 points
        faithTrack.setCurrentFavorPoints(5);
        faithTrack.computeFaithTrackPoints();

        //Assert
        assertEquals(faithTrack.getFinalPoints(), 9);

    }

    @Test
    void calculationFinalPointsTest2(){
        //Arrange
        faithTrack.setFaithMarker(19);

        //Act
        faithTrack.computeCheckpoints(); // it could be return 4 points
        faithTrack.setCurrentFavorPoints(2);
        faithTrack.computeFaithTrackPoints();

        //Assert
        assertEquals(faithTrack.getFinalPoints(), 14);

    }


    @Test
    void lorenzoIncreasesPosition() {

        //Arrange
        PopeTile pt;
        faithTrack.setFaithMarker(7);

        //Act
        faithTrack.lorenzoIncreasesFaithMarker();
        pt = (PopeTile) faithTrack.getFaithTrack().get(faithTrack.getFaithMarker());
        //Assert
        assertAll(
                () -> assertFalse(pt.isActive)
        );
    }

    @Test
    void increaseFaithTest() {
        //Arrange
        PopeTile pt;
        faithTrack.setFaithMarker(7);

        //Act
        faithTrack.increaseFaithMarker();
        pt = (PopeTile) faithTrack.getFaithTrack().get(faithTrack.getFaithMarker());
        //Assert
        assertAll(
                () -> assertFalse(pt.isActive)
        );
    }

    @Test
    void vaticanConditionsTest() {
        //Arrange
        faithTrack.setFaithMarker(7);

        //Act
        faithTrack.increaseFaithMarker();
        faithTrack.checkVaticanCondition(8);

        //Assert
        assertAll(
                () -> assertEquals(2, faithTrack.getCurrentFavorPoints())
        );
    }

    @Test
    void vaticanConditionsNotMatchedTest() {
        //Arrange
        faithTrack.setFaithMarker(7);
        PopeTile pt = (PopeTile) faithTrack.getFaithTrack().get(8);

        //Act
        faithTrack.increaseFaithMarker();
        faithTrack.setFaithMarker(0);
        faithTrack.checkVaticanCondition(8);

        //Assert
        assertAll(
                () -> assertEquals(0, faithTrack.getCurrentFavorPoints())
        );
    }

    @Test
    void lastTile() {
        //Arrange
        faithTrack.setFaithMarker(24);
        assertTrue(faithTrack.isLastTile());
    }

    @Test
    void setTileCheckpoint() {
        //Arrange
        Tile tile = new Tile(0, 0, 0);

        //Act
        tile.setIndex(100);

        //Assert
        assertEquals(tile.getIndex(), 100);
    }
}
