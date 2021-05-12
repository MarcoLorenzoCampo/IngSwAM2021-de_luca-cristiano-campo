package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;

public final class MessageHandler {

    private final GameManager gameManager;
    private final IGame game;

    private PlayerState currentPlayerState;

    public MessageHandler(GameManager gameManager) {
        this.gameManager = gameManager;
        this.game = PlayingGame.getGameInstance();
    }

    public void setCurrentPlayerState(PlayerState currentPlayerState) {
        this.currentPlayerState = currentPlayerState;
    }

    public void onMessage(Message message) {

        switch(game.getCurrentState().getGameState()) {
            case SETUP: break;
            case GAME_STARTED: break;

        }
    }
}
