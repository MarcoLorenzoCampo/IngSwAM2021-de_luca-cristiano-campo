package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.eventHandlers.ControllerObserver;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.serverMessages.VaticanReportNotification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to manage players and turns in a single player game.
 */
public class SinglePlayerLobbyManager implements ILobbyManager, ControllerObserver {

    private final IGame currentGame;
    private final List<RealPlayer> realPlayerList;
    private final LorenzoPlayer lorenzo;
    private final GameManager gameManager;
    private boolean first_turn;

    private VirtualView playerVV;

    public SinglePlayerLobbyManager(GameManager gameManager) {
        this.currentGame = PlayingGame.getGameInstance();
        lorenzo = new LorenzoPlayer();
        realPlayerList = new ArrayList<>();
        this.gameManager = gameManager;
        first_turn = true;
    }

    /**
     * Checks if a player can be added.
     * @param nickname: name to add;
     */
    @Override
    public void addNewPlayer(String nickname, VirtualView virtualView) {
        if(realPlayerList.size() == 0) {
            realPlayerList.add(new RealPlayer(nickname));

            setObserver(nickname, virtualView);

            playerVV = virtualView;
            playerVV.showLoginOutput(true, true, false);

        } else {
            playerVV.showError("A single player match has already started!");
        }
    }

    /**
     * Sets the only player as the first one. This will never change during the game.
     * Also call the method to deal LeaderCards.
     */
    @Override
    public void setPlayingOrder() {
        realPlayerList.get(0).setFirstToPlay();

        playerVV.showGenericString("\nGame is starting! Good luck :)");

        currentGame.setCurrentPlayer(realPlayerList.get(0));
        gameManager.setCurrentPlayer(realPlayerList.get(0).getName());

        showStartingUpdates();
        giveLeaderCards();
    }

    /**
     * After each turn, a new {@link LorenzoAction} is generated and run.
     * Lorenzo is modeled as a player but his actions are generated automatically by the controller.
     */
    @Override
    public void setNextTurn() {

        forwardPlayerUpdates();
        if(first_turn) {
            first_turn=false;
        }
        else{
        playerVV.showGenericString("Lorenzo's turn now.");
        lorenzo.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzo));
        }

        gameManager.onStartTurn();
    }

    /**
     * Method to deal leader cards. The only player receives the first 4.
     */
    @Override
    public void giveLeaderCards() {
        List<LeaderCard> leaderCards = LeaderCardsDeckBuilder.deckBuilder();

        realPlayerList.get(0).setOwnedLeaderCards(
                new LinkedList<>(leaderCards
                        .stream()
                        .limit(4)
                        .collect(Collectors.toList())));
    }

    @Override
    public void setObserver(String nickname, VirtualView vv) {

        //Player observes himself.
        realPlayerList.get(0).getPlayerBoard().getInventoryManager().addObserver(vv);
        realPlayerList.get(0).addObserver(vv);
        realPlayerList.get(0).getPlayerBoard().getProductionBoard().addObserver(vv);
        realPlayerList.get(0).getPlayerBoard().getFaithTrack().addObserver(vv);

        //Player observes the model.
        gameManager.getCurrentGame().getGameBoard().getResourceMarket().addObserver(vv);
        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().addObserver(vv);

        //Player observes Lorenzo.
        lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().addObserver(vv);
        lorenzo.getLorenzoPlayerBoard().addObserver(vv);
        lorenzo.getLorenzoPlayerBoard().getLorenzoTokenPile().addObserver(vv);

        //Lobby manager observes the player.
        realPlayerList.get(0).getPlayerBoard().getFaithTrack().addControllerObserver(this);
        realPlayerList.get(0).getPlayerBoard().getInventoryManager().addControllerObserver(this);
        realPlayerList.get(0).getPlayerBoard().addControllerObserver(this);
        realPlayerList.get(0).addControllerObserver(this);

        //Lobby manager observes Lorenzo.
        lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().addControllerObserver(this);

        //Lobby manager observes the model.
        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().addControllerObserver(this);
    }

    @Override
    public void showStartingUpdates() {

        playerVV.printResourceMarket(gameManager.getCurrentGame().getGameBoard().getResourceMarket().getResourceBoard(),
                gameManager.getCurrentGame().getGameBoard().getResourceMarket().getExtraMarble());

        playerVV.printAvailableCards(gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().getAvailableCards());

        playerVV.printFaithTrack(gameManager.getCurrentGame().getCurrentPlayer().getPlayerBoard().getFaithTrack());
    }

    @Override
    public void randomizedResourcesSetup(String disconnectedNickname) { }

    @Override
    public void randomizedLeadersSetup(String disconnectedNickname) { }

    @Override
    public List<RealPlayer> getRealPlayerList() {
        return realPlayerList;
    }

    @Override
    public int getLobbySize() {
        return 1;
    }

    @Override
    public void reconnectPlayer(String nickname, VirtualView vv) { }

    @Override
    public void broadcastGenericMessage(String message) {
        playerVV.showGenericString(message);
    }

    @Override
    public void broadCastWinMessage(String message) {
        playerVV.showWinMatch(message);

        gameManager.resetFSM();
    }

    @Override
    public int turnOfPlayer(String current) {
        return 0;
    }

    public LorenzoPlayer getLorenzo() {
        return lorenzo;
    }

    @Override
    public void disconnectPlayer(String nicknameToDisconnect) {
        gameManager.resetFSM();
    }

    @Override
    public void controllerUpdate(Message message) {
        switch(message.getMessageType()) {
            case VATICAN_REPORT_NOTIFICATION:

                VaticanReportNotification v = (VaticanReportNotification) message;

                int popeTileIndex = v.getPopeTileIndex();

                //Player's faith track.
                FaithTrack ft = realPlayerList.get(0).getPlayerBoard().getFaithTrack();
                //Lorenzo's faith track.
                FaithTrack ftl = lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack();

                //If the player started the vatican report.
                if(ft.isPopeTile(ft.getFaithMarker())) {
                    ftl.checkVaticanCondition(popeTileIndex);

                } else {
                    //If lorenzo started the vatican report.
                    if(ftl.isPopeTile(ftl.getFaithMarker())) {
                        ft.checkVaticanCondition(popeTileIndex);
                    }
                }
                break;

            case DISCARDED_RESOURCE:
                playerVV.showGenericString("\nYou discarded a resource, Lorenzo moves!");
                lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().lorenzoIncreasesFaithMarker();
                break;

            //Faith track only
            case END_GAME:
                FaithTrack playerFt = realPlayerList.get(0).getPlayerBoard().getFaithTrack();
                FaithTrack lorenzoFt = lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack();

                if(playerFt.isLastTile()) {
                    playerVV.showGenericString("\nYou reached the end of the faith track!");
                    endGameScore();
                    playerVV.showWinMatch("You");
                }

                if(lorenzoFt.isLastTile()) {
                    playerVV.showGenericString("\nLorenzo reached the end of the faith track!");
                    endGameScore();
                    playerVV.showWinMatch("Lorenzo");
                }
                break;

            case NO_MORE_CARDS:
                playerVV.showGenericString("\nLorenzo discarded a whole sub-deck!");
                endGameScore();
                playerVV.showWinMatch("Lorenzo");
                break;

            case BOUGHT_7_CARDS:
                playerVV.showGenericString("\nYou bought your 7th card!");
                endGameScore();
                playerVV.showWinMatch("You");
                break;

            default: //Ignore any other message
                break;
        }
    }

    @Override
    public void forwardPlayerUpdates() {

        playerVV.getPeek(
                "Lorenzo",
                lorenzo.getFaithPosition(),
                null,
                null,
                null
        );
    }

    /**
     * Method to compute the player's victory points.
     */
    private void endGameScore() {
        playerVV.showGenericString("\nYour score: "
                + realPlayerList.get(0).computeTotalVictoryPoints());
    }
}
