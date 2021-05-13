package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.network.eventHandlers.IView;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;

public final class MessageHandler {

    private final GameManager gameManager;
    private final IGame game;
    private VirtualView currentVV;
    private boolean firstTurn;

    private PlayerState currentPlayerState;

    public MessageHandler(GameManager gameManager) {
        this.gameManager = gameManager;
        this.game = PlayingGame.getGameInstance();
        firstTurn = true;
    }

    public void setCurrentVirtualView(VirtualView currentVV) {
        this.currentVV = currentVV;
    }


    public void setCurrentPlayerState(PlayerState currentPlayerState) {
        this.currentPlayerState = currentPlayerState;
    }

    public void onMessage(Message message) {

        switch(game.getCurrentState().getGameState()) {
            case SETUP:
                if(message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)){
                    if(firstTurn){
                        game.setCurrentState(PossibleGameStates.SETUP_SIZE);
                        currentVV.askPlayerNumber();
                        firstTurn = false;
                    }
                    else{
                        MultiPlayerLobbyManager current = (MultiPlayerLobbyManager) gameManager.getLobbyManager();
                        if(current.getRealPlayerList().size()==current.getLobbySize()){
                            current.setDefaultResources();
                            game.setCurrentState(PossibleGameStates.SETUP_RESOURCES);
                        }
                    }
                }
                break;

            case SETUP_SIZE:
                if (message.getMessageType().equals(PossibleMessages.GAME_SIZE)){
                    message = (OneIntMessage) message;
                    if(((OneIntMessage) message).getIndex()==1){
                        gameManager.setLobbyManager("singlePlayer");
                        gameManager.getLobbyManager().addNewPlayer(message.getSenderUsername(), currentVV);
                        game.setCurrentState(PossibleGameStates.SETUP_LEADER);
                    }
                    else{
                        gameManager.setLobbyManager("multiPlayer");
                        gameManager.getLobbyManager().addNewPlayer(message.getSenderUsername(), currentVV);
                        game.setCurrentState(PossibleGameStates.SETUP);
                    }

                }
                break;
            case SETUP_RESOURCES:


        }
    }
}
