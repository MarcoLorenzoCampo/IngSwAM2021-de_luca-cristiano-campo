package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.ActivateProductionAction;
import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraInventoryLeaderCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.server.IClientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static it.polimi.ingsw.enumerations.Color.BLUE;
import static it.polimi.ingsw.enumerations.Color.GREEN;
import static it.polimi.ingsw.enumerations.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

class MultiGameManagerTest {

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
            public void disconnect() {
            }

            @Override
            public void sendMessage(Message message) {
            }

            @Override
            public void sameNameDisconnect() {
            }
        });
        playerTwo = "playerTwo";
        view2 = new VirtualView(new IClientHandler() {
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
        view2 = null;
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


    void fullSetup() {
        connectPlayers();
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
        wanted.add(SHIELD);
        gameManager.onMessage(new SetupResourceAnswer(playerTwo, 1, wanted));
        assertAll(
                () -> assertEquals(PossibleGameStates.SETUP_LEADER, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> {
                    for (RealPlayer iterator : gameManager.getLobbyManager().getRealPlayerList()) {
                        assertTrue(iterator.getPlayerBoard().getInventoryManager().getBuffer().isEmpty());
                    }
                },
                () -> assertEquals(playerTwo, check.getName()),
                () -> assertEquals(1, check.getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(2).getQuantity()),
                () -> assertEquals(SHIELD, check.getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(2).getType())
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
        assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState());
    }

    @Test
    void DiscardAtFirst() {
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();

        assertEquals(playerOne, gameManager.getCurrentPlayer());
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DISCARD_LEADER, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertFalse(gameManager.getCurrentGame().getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction()),
                () -> assertEquals(1, gameManager.getCurrentGame().getCurrentPlayer().getFaithPosition())
        );
    }

    @Test
    void DiscardAtLast() {
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, 6));
        assertEquals(PossibleGameStates.DEPOSIT, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size(); i++) {
            gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertEquals(0, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size()),
                () -> assertTrue(gameManager.getCurrentGame().getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction())
        );
        assertEquals(playerOne, gameManager.getCurrentPlayer());
        assertEquals(playerOne, gameManager.getCurrentPlayer());
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DISCARD_LEADER, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertTrue(gameManager.getCurrentGame().getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction()),
                () -> assertEquals(1, gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().size())
        );


    }

    @Test
    void GetResources() {
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, 6));
        assertEquals(PossibleGameStates.DEPOSIT, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size(); i++) {
            gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertEquals(0, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size()),
                () -> assertTrue(gameManager.getCurrentGame().getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction())
        );
        gameManager.onMessage(new EndTurnMessage(playerOne));
        assertEquals(playerTwo, gameManager.getCurrentPlayer());
    }

    @Test
    void Productions() {
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.onMessage(new BaseProductionMessage(playerOne, SHIELD, SHIELD, SHIELD));
        assertEquals(PossibleGameStates.ACTIVATE_PRODUCTION, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new ExecuteProductionMessage(playerOne));
        assertEquals(PossibleGameStates.REMOVE, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().size(); i++) {
            gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        }
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertEquals(99, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getInventory().get(SHIELD)),
                () -> assertNotNull(gameManager.getCurrentView())
        );
    }

    @Test
    void BuyCard() {
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.onMessage(new TwoIntMessage(playerOne, PossibleMessages.BUY_PRODUCTION, 0, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.BUY_CARD, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertNotNull(gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getProductionSlots()[0].getProductionCard()),
                () -> assertEquals(GREEN, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getProductionSlots()[0].getProductionCard().getColor()),
                () -> assertEquals(Level.ONE, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getProductionSlots()[0].getProductionCard().getLevel())
        );

        assertEquals(PossibleGameStates.BUY_CARD, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().size(); i++) {
            gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        }
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState())
        );
    }

    @Test
    void ExtraProduction(){
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        ResourceTag[] input = new ResourceTag[1];
        input[0] = new ResourceTag(SHIELD, 1);
        ResourceTag[] output = new ResourceTag[2];
        output[0] = new ResourceTag(FAITH, 1);
        output[1] = new ResourceTag(UNDEFINED,1);
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().addLeaderProduction(new ExtraProductionLeaderCard(SHIELD, 1,EffectType.EXTRA_PRODUCTION,null,input, output));

        gameManager.onMessage(new ExtraProductionMessage(playerOne,0 , SHIELD));
        assertEquals(PossibleGameStates.ACTIVATE_PRODUCTION, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new ExecuteProductionMessage(playerOne));
        assertEquals(PossibleGameStates.REMOVE, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));

        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertEquals(100, gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getInventory().get(SHIELD)),
                () -> assertNotNull(gameManager.getCurrentView())
        );
    }

    @Test
    void activateBoughtProduction(){
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        ProductionCard bought = gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().getAvailableCards().get(0);
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().placeProductionCard(0, bought);

        gameManager.onMessage(new OneIntMessage(playerOne,PossibleMessages.ACTIVATE_PRODUCTION, 0));
        assertEquals(PossibleGameStates.ACTIVATE_PRODUCTION, gameManager.getCurrentGame().getCurrentState().getGameState());
        gameManager.onMessage(new ExecuteProductionMessage(playerOne));
        assertEquals(PossibleGameStates.REMOVE, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().size(); i++) {
            gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        }
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        gameManager.onMessage(new SourceStrongboxMessage(playerOne));
        assertAll(
                () -> assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertNotNull(gameManager.getCurrentView())
        );
    }

    @Test
    void placeLeader(){
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().remove(0);
        ResourceTag[] requirements = new ResourceTag[1];
        requirements[0]= new ResourceTag(SERVANT, 5);
        gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().add(0, new ExtraInventoryLeaderCard(1,EffectType.EXTRA_INVENTORY, requirements, null, SHIELD));

        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.ACTIVATE_LEADER, 0));
        assertAll(
                () -> assertEquals(PossibleGameStates.PLAYING, gameManager.getCurrentGame().getCurrentState().getGameState()),
                () -> assertTrue(gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().get(0).isActive()),
                () -> assertEquals(4,gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getWarehouse().getShelves().size()),
                () -> assertEquals(SHIELD,gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getWarehouse().getShelves().get(3).getType())
        );
    }

    @Test
    void changeColor(){
        fullSetup();
        playerOne = gameManager.getLobbyManager().getRealPlayerList().get(0).getName();
        playerTwo = gameManager.getLobbyManager().getRealPlayerList().get(1).getName();
        assertEquals(playerOne, gameManager.getCurrentPlayer());

        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().addExchangeLeader(SHIELD);
        gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().addExchangeLeader(COIN);
        boolean found = false;
        int index=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(!found && gameManager.getCurrentGame().getGameBoard().getResourceMarket().getResourceBoard()[i][j].equals(UNDEFINED)){
                    found=true;
                    index=j;
                }
            }
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.GET_RESOURCES, index));
        assertEquals(PossibleGameStates.CHANGE_COLOR, gameManager.getCurrentGame().getCurrentState().getGameState());
        ArrayList<Integer> exchangeable = new ArrayList<>();
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size(); i++) {
            gameManager.onMessage(new ExchangeResourceMessage(playerOne, SHIELD, i));
        }
        assertEquals(PossibleGameStates.DEPOSIT, gameManager.getCurrentGame().getCurrentState().getGameState());
        for (int i = 0; i < gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().size(); i++) {
            gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        }
        gameManager.onMessage(new OneIntMessage(playerOne, PossibleMessages.DEPOSIT, 0));
        assertEquals(PossibleGameStates.MAIN_ACTION_DONE, gameManager.getCurrentGame().getCurrentState().getGameState());


    }
}