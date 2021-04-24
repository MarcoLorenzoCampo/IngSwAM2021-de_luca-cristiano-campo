package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerGameManagerTest {

    @Mock
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        resetSingleton();
        gameManager = new GameManager(true);
    }

    @Test
    void ILobbyInstance() {
        //Assert
        assertTrue(gameManager.getLobbyManager() instanceof SinglePlayerLobbyManager);
    }

    @Test
    void lorenzoTest() {
        //Arrange
        SinglePlayerLobbyManager lobbyManagerTest = (SinglePlayerLobbyManager) gameManager.getLobbyManager();

        //Assert
        assertAll(
                () -> assertNotNull(lobbyManagerTest.getLorenzo())
        );
    }

    @Test
    void addPlayer() {
        //Arrange
        String nickname;

        //Act
        nickname = "testPlayer";

        //Assert
        assertAll(
                () -> assertDoesNotThrow(
                        () -> gameManager.getLobbyManager().addNewPlayer(nickname)),
                () -> assertThrows(NoMorePlayersException.class,
                        () -> gameManager.getLobbyManager().addNewPlayer(nickname))
        );
    }

    @Test
    void setPlayingOrderTest() throws NameTakenException, NoMorePlayersException {
        //Arrange
        String nickname = "testPlayer";

        //Act
        gameManager.getLobbyManager().addNewPlayer(nickname);
        gameManager.getLobbyManager().setPlayingOrder();

        //Assert
        assertAll(
                () -> assertEquals(gameManager.getCurrentGame().getCurrentPlayer().getName(), nickname)
        );
    }

    void resetSingleton() {
        IGame game = PlayingGame.getGameInstance();

        game.setCurrentPlayer(null);
        game.getCurrentState().setGameState(PossibleGameStates.SETUP);
    }
}