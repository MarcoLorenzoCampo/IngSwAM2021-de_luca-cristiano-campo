package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.network.MultiEchoServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerGameManagerTest {

    @Mock
    private GameManager gameManager;
    @Mock
    private MultiEchoServer multiEchoServer;

    @BeforeEach
    void setUp() {
        resetSingleton();
        gameManager = new GameManager(2, multiEchoServer);
    }

    @Test
    void addPlayerLobbyTest() throws NameTakenException, NoMorePlayersException {
        //Arrange
        String nickname;

        //Act
        nickname = "testPlayer1";

        //Assert
        assertNotNull(gameManager.getLobbyManager().getRealPlayerList());

        assertAll(
                () -> assertNotNull(gameManager.getLobbyManager()),
                () -> assertDoesNotThrow(
                        () ->  gameManager.getLobbyManager().addNewPlayer(nickname)),
                () -> assertThrows(NameTakenException.class,
                        () ->  gameManager.getLobbyManager().addNewPlayer(nickname))
        );

        //Act
        gameManager.getLobbyManager().addNewPlayer("testPlayer2");
        gameManager.getLobbyManager().addNewPlayer("testPlayer3");
        gameManager.getLobbyManager().addNewPlayer("testPlayer4");

        //Assert
        assertThrows(NoMorePlayersException.class,
                () -> gameManager.getLobbyManager().addNewPlayer("testPlayer5"));
    }

    @Test
    void setNextTurnLobbyTest() throws NameTakenException, NoMorePlayersException {
        //Arrange
        String nickname1 = "testPlayer1";
        String nickname2 = "testPlayer2";
        String nickname3 = "testPlayer3";
        String nickname4 = "testPlayer4";

        //Act
        gameManager.getLobbyManager().addNewPlayer(nickname1);
        gameManager.getLobbyManager().addNewPlayer(nickname2);
        gameManager.getLobbyManager().addNewPlayer(nickname3);
        gameManager.getLobbyManager().addNewPlayer(nickname4);

        gameManager.getLobbyManager().setPlayingOrder();
        gameManager.getLobbyManager().setNextTurn();

        //Assert
        assertAll(
                () -> assertEquals(1, gameManager.getLobbyManager().getNumberOfTurns()),
                () -> assertTrue(gameManager.getCurrentGame().getCurrentPlayer().getName().equals(nickname1)
                        || gameManager.getCurrentGame().getCurrentPlayer().getName().equals(nickname2)
                        || gameManager.getCurrentGame().getCurrentPlayer().getName().equals(nickname3)
                        || gameManager.getCurrentGame().getCurrentPlayer().getName().equals(nickname4)
                    )
        );
    }

    @Test
    void leaderDealingTest() throws NameTakenException, NoMorePlayersException {
        //Arrange
        String nickname1 = "testPlayer1";
        String nickname2 = "testPlayer2";

        //Act
        gameManager.getLobbyManager().addNewPlayer(nickname1);
        gameManager.getLobbyManager().addNewPlayer(nickname2);
        gameManager.getLobbyManager().setPlayingOrder();

        //Assert
        assertAll(
                () -> assertNotNull(gameManager.getCurrentGame().getCurrentPlayer()
                        .getOwnedLeaderCards())
        );
    }

    void resetSingleton() {
        IGame game = PlayingGame.getGameInstance();

        game.setCurrentPlayer(null);
        game.getCurrentState().setGameState(PossibleGameStates.SETUP);
    }
}