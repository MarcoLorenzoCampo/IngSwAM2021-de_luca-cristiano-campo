package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.IClientHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;
    private String playerOne;
    private VirtualView view1;
    private VirtualView view2;
    private String playerTwo;



    @BeforeEach
    void setUp() {
        playerOne = "playerOne";
        view1 = new VirtualView(new IClientHandler() {
            @Override
            public void disconnect() { }
            @Override
            public void sendMessage(Message message) { }
            @Override
            public void sameNameDisconnect() { }
        });
        playerTwo = "playerTwo";
        view2 = new VirtualView(new IClientHandler() {
            @Override
            public void disconnect() { }
            @Override
            public void sendMessage(Message message) { }
            @Override
            public void sameNameDisconnect() { }
        });
        gameManager = new GameManager();

    }

    @Test
    void connectFirstPlayer() {
        gameManager.onMessage(new NicknameRequest(playerOne));
        gameManager.addVirtualView(playerOne, view1);
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GAME_SIZE, 2));

        //ASSERT
        assertAll(
                () -> assertNotNull(gameManager.getLobbyManager()),
                () -> assertInstanceOf(MultiPlayerLobbyManager.class, gameManager.getLobbyManager()),
                () -> assertEquals(1, gameManager.getVirtualViewLog().size()),
                () -> assertNotNull(gameManager.getVirtualViewLog().get(playerOne)),
                () -> assertEquals(2, gameManager.getLobbyManager().getLobbySize()),
                () -> assertEquals(PossibleGameStates.SETUP, gameManager.getCurrentGame().getCurrentState().getGameState())
        );

        gameManager.addVirtualView(playerTwo, view2);
        gameManager.onMessage(new NicknameRequest(playerTwo));

        //ASSERT
    }


    @Test
    void sampleGame(){

    }

    @Test
    void setCurrentPlayer() {
    }

    @Test
    void setLobbyManager() {
    }

    @Test
    void getCurrentGame() {
    }

    @Test
    void getLobbyManager() {
    }

    @Test
    void addVirtualView() {
    }

    @Test
    void endGame() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void setGameStarted() {
    }

    @Test
    void isGameStarted() {
    }

    @Test
    void onMessage() {
    }

    @Test
    void onStartTurn() {
    }

    @Test
    void resetFSM() {
    }

    @Test
    void prepareForNextTurn() {
    }

    @Test
    void getVirtualViewLog() {
    }
}