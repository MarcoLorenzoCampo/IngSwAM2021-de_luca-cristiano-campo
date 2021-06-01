package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.serverMessages.VaticanReportNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to manage players and turns in a single player game.
 */
public class SinglePlayerLobbyManager implements ILobbyManager, Observer {

    private final IGame currentGame;
    private final List<RealPlayer> realPlayerList;
    private final LorenzoPlayer lorenzo;
    private final GameManager gameManager;

    private VirtualView playerVV;

    public SinglePlayerLobbyManager(GameManager gameManager) {
        this.currentGame = PlayingGame.getGameInstance();
        lorenzo = new LorenzoPlayer();
        realPlayerList = new ArrayList<>();
        this.gameManager = gameManager;
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

        playerVV.showGenericString("\nYou'll move first, Lorenzo second.");

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

        playerVV.showGenericString("Lorenzo's turn now.");

        lorenzo.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzo));

        gameManager.onStartTurn();
    }

    /**
     * Method to deal leader cards. The only player receives the first 4.
     */
    @Override
    public void giveLeaderCards() {
        List<LeaderCard> leaderCards = LeaderCardsDeckBuilder.deckBuilder();

        realPlayerList.get(0).setOwnedLeaderCards(
                leaderCards
                        .stream()
                        .limit(4)
                        .collect(Collectors.toList()));
    }

    @Override
    public void setObserver(String nickname, VirtualView vv) {

        realPlayerList.get(0).getPlayerBoard().getFaithTrack().addObserver(this);

        realPlayerList.get(0).getPlayerBoard().getInventoryManager().addObserver(this);

        gameManager.getCurrentGame().getGameBoard().getResourceMarket().addObserver(vv);

        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().addObserver(vv);

        realPlayerList.get(0).getPlayerBoard().getInventoryManager().addObserver(vv);

        realPlayerList.get(0).getPlayerBoard().getFaithTrack().addObserver(vv);

        realPlayerList.get(0).getPlayerBoard().getProductionBoard().addObserver(vv);

        realPlayerList.get(0).addObserver(vv);

        lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().addObserver(vv);

        lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().addObserver(this);

        lorenzo.getLorenzoPlayerBoard().addObserver(vv);

        lorenzo.getLorenzoPlayerBoard().getLorenzoTokenPile().addObserver(vv);
    }

    @Override
    public void showStartingUpdates() {

        playerVV.printResourceMarket(PlayingGame.getGameInstance().getGameBoard().getResourceMarket().getResourceBoard(),
                PlayingGame.getGameInstance().getGameBoard().getResourceMarket().getExtraMarble());

        playerVV.printAvailableCards(PlayingGame.getGameInstance().getGameBoard().getProductionCardMarket().getAvailableCards());

        playerVV.printFaithTrack(PlayingGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getFaithTrack());
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
    public void update(Message message) {
        switch(message.getMessageType()) {
            case VATICAN_REPORT_NOTIFICATION:

                VaticanReportNotification v = (VaticanReportNotification) message;

                int popeTileIndex = v.getPopeTileIndex();
                int rangeToCheck = v.getRange();

                FaithTrack ft = realPlayerList.get(0).getPlayerBoard().getFaithTrack();

                if(!ft.isPopeTile(ft.getFaithMarker())) {
                    if (ft.getFaithMarker() < (popeTileIndex - rangeToCheck)) {
                        ft.setPopeTileInactive(popeTileIndex);
                    }
                }
                break;

            case DISCARDED_RESOURCE:
                lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().increaseFaithMarker();
                break;

            case END_GAME:
                String winner;

                if(lorenzo.getLorenzoPlayerBoard().getLorenzoFaithTrack().getFaithMarker() == 24
                    || currentGame.getGameBoard().getProductionCardMarket().getAvailableCards().size() == 11) {
                    winner = "Lorenzo";
                    playerVV.showWinMatch(winner);
                }
                if(realPlayerList.get(0).getPlayerBoard().getFaithTrack().getFaithMarker() == 24) {

                }
                break;

            default: //Ignore any other message
                break;
        }
    }
}
