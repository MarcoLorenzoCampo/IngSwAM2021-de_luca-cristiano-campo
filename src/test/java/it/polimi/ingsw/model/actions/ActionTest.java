package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("underTest");
    }


}