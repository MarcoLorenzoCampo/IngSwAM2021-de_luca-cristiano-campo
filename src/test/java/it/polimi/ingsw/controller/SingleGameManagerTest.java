package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.server.IClientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enumerations.ResourceType.UNDEFINED;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleGameManagerTest {

    private GameManager gameManager;
    private String playerOne;
    private VirtualView view1;



    @BeforeEach
    void setUp() {
        playerOne = "playerOne";
        view1 = new VirtualView(new IClientHandler() {
            @Override
            public void disconnect() {
            }

            @Override
            public void sendMessage(Message message) {
            }

            @Override
            public void sameNameDisconnect() {
            }
        });

        gameManager = new GameManager();
    }

    @AfterEach
    public void tearDown() {
        gameManager.resetFSM();
        view1 = null;
    }

    @Test
    void singleGameSetup(){
        gameManager.onMessage(new NicknameRequest(playerOne));
        gameManager.addVirtualView(playerOne, view1);
        assertEquals(PossibleGameStates.SETUP_SIZE, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GAME_SIZE, 1));

        for(RealPlayer realPlayer : gameManager.getLobbyManager().getRealPlayerList()) {
            realPlayer.getPlayerBoard().testSetup();
        }

        //ASSERT
        assertAll(
                () -> assertNotNull(gameManager.getLobbyManager()),
                () -> assertInstanceOf(SinglePlayerLobbyManager.class, gameManager.getLobbyManager()),
                () -> assertEquals(1, gameManager.getVirtualViewLog().size()),
                () -> assertNotNull(gameManager.getVirtualViewLog().get(playerOne)),
                () -> assertEquals(1, gameManager.getLobbyManager().getLobbySize()),
                () -> assertEquals(PossibleGameStates.SETUP_LEADER, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertEquals(playerOne, gameManager.getCurrentPlayer())
        );
        gameManager.onMessage(new DiscardTwoLeaders(playerOne, 0, 1));
        assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState());
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        //Do generic action
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, 0));
        int size = gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size();
        if(size == 0) gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        else {
            for (int i = 0; i < size; i++) {
                gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
            }
        }
        gameManager.onMessage(new EndTurnMessage(playerOne));
        assertEquals(playerOne, gameManager.getCurrentPlayer());
        assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState());
    }

    @Test
    void inGameUpdates(){
        gameManager.onMessage(new NicknameRequest(playerOne));
        gameManager.addVirtualView(playerOne, view1);
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GAME_SIZE, 1));
        gameManager.onMessage(new DiscardTwoLeaders(playerOne, 0, 1));

        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(0).setElement(new ResourceTag(ResourceType.SHIELD, 1));
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(1).setElement(new ResourceTag(ResourceType.COIN, 2));
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(2).setElement(new ResourceTag(ResourceType.SERVANT, 3));
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getFaithTrack().setFaithMarker(7);
        for (int i = 0; i < 6; i++) {
            gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().increaseBoughCardsCount();
        }

        boolean found = false;
        int index=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(!found && !gameManager.getCurrentGame().getGameBoard().getResourceMarket().getResourceBoard()[i][j].equals(UNDEFINED)){
                    found=true;
                    index=j;
                }
            }
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, index));
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size(); i++) {
            gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getFaithTrack().increaseFaithMarker();
        gameManager.onMessage(new EndTurnMessage(playerOne));

        gameManager.onMessage(new TwoIntMessage(playerOne, PossibleMessages.BUY_PRODUCTION, 0, 0));

    }
}
