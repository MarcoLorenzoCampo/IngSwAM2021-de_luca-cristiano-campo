package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.EndGameMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.IClientHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.LinkedList;

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
        connectPlayers();
        for (RealPlayer iterator: gameManager.getLobbyManager().getRealPlayerList()) {
            iterator.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.SHIELD, 0);
            iterator.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.COIN, 0);
            iterator.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.STONE, 0);
            iterator.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.SERVANT, 0);
        }
    }

    void connectPlayers() {
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

        assertAll(
                () -> assertEquals(2, gameManager.getVirtualViewLog().size()),
                () -> assertNotNull(gameManager.getVirtualViewLog().get(playerTwo)),
                () -> assertEquals(PossibleGameStates.SETUP_RESOURCES, gameManager.getCurrentGame().getCurrentState().getGameState())
        );

    }


    @Test
    void sampleGame() {

        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();

        //First player automatically skips
        gameManager.onMessage(new SetupResourceAnswer(playerOne, 0, null));
        RealPlayer check = gameManager.getCurrentGame().getCurrentPlayer();
        assertAll(
                () -> assertEquals(PossibleGameStates.SETUP_RESOURCES, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> {
                    for (RealPlayer iterator : gameManager.getLobbyManager().getRealPlayerList()) {
                        assertTrue(iterator.getPlayerBoard().getInventoryManager().getBuffer().isEmpty());
                    }
                }
        );
        //Second player first turn
        LinkedList<ResourceType> wanted = new LinkedList<>();
        wanted.add(ResourceType.SHIELD);
        gameManager.onMessage(new SetupResourceAnswer(playerTwo, 1, wanted));
        assertAll(
                () -> assertEquals(PossibleGameStates.SETUP_LEADER, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> {
                    for (RealPlayer iterator : gameManager.getLobbyManager().getRealPlayerList()) {
                        assertTrue(iterator.getPlayerBoard().getInventoryManager().getBuffer().isEmpty());
                    }
                },
                () -> assertEquals(playerTwo, check.getName()),
                () -> assertEquals(1, check.getPlayerBoard().getInventoryManager().getInventory().get(ResourceType.SHIELD)),
                () -> assertEquals(1, check.getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(2).getQuantity()),
                () -> assertEquals(ResourceType.SHIELD, check.getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(2).getType())
        );

        //First player leader setup
        assertEquals(playerOne, gameManager.getCurrentPlayer());
        gameManager.onMessage(new DiscardTwoLeaders(playerOne, 0, 1));
        assertEquals(PossibleGameStates.SETUP_LEADER, gameManager.getCurrentGame().getCurrentState().getGameState());

        //Second Player leader setup
        assertEquals(playerTwo, gameManager.getCurrentPlayer());
        gameManager.onMessage(new DiscardTwoLeaders(playerTwo, 1, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> {
                    for (RealPlayer iterator : gameManager.getLobbyManager().getRealPlayerList()) {
                        assertEquals(2, iterator.getOwnedLeaderCards().size());
                    }
                }
        );

        //First player turn
        assertEquals(playerOne, gameManager.getCurrentPlayer());
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, 6));
        assertNotEquals(0, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size());

        int times = gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size();
        if (times != 0) {
            for (int i = 0; i < times; i++) {
                gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
            }
        }
        else {
            gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));

        }
        assertEquals(0, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size());
        assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new EndTurnMessage(playerOne));
        assertEquals(playerTwo, gameManager.getCurrentPlayer());

        //Second Player
        gameManager.onMessage(new OneIntMessage(playerTwo, PossibleMessages.DISCARD_LEADER, 0));
        assertAll(
                ()->assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState()),
                ()->assertEquals(1, gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().size()),
                ()->assertEquals(1, gameManager.getCurrentGame().getCurrentPlayer().getFaithPosition())
        );

    }

}