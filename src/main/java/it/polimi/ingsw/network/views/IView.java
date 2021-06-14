package it.polimi.ingsw.network.views;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.eventHandlers.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Interface to implement every possible action asked/performed by the player. It can be implemented
 * by CLI and GUI. Also implemented by {@link VirtualView} to hide server implementation from the
 * controller.
 */
public interface IView {

    /**
     * Asks the client to chose a username;
     */
    void askNickname();

    /**
     * Asks the number of players that will join the game;
     */
    void askPlayerNumber();

    /**
     * Shows login output;
     * @param connectionSuccess: outcome of the connection try.
     * @param nicknameAccepted: outcome of the nickname validation.
     * @param reconnected: true if the player has been recognized.
     */
    void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected);

    /**
     * Prints a generic message sent by the server (eg: a client was disconnected, a player won);
     * @param genericMessage: generic string to show.
     */
    void showGenericString(String genericMessage);

    /**
     * Method to show the player an action performed was denied because not valid.
     * @param errorMessage: reason behind the refusal.
     */
    void showInvalidAction(String errorMessage);


    /**
     * Asks the player to store or discard a resource that has to be deposited;
     */
    void askToDiscard() throws ExecutionException;


    /**
     *
     * @param cards: available leader cards
     */
    void showLeaderCards(List<LeaderCard> cards);

    /**
     * Tells the player some action/message sent wasn't validated.
     * @param errorMessage: specific cause of the refusal.
     */
    void showError(String errorMessage);

    /**
     * Tells the current player it's his turn now and enables inputs.
     * @param message: generic message.
     */
    void currentTurn(String message);

    /**
     * Tells the player his turn has ended and disables his inputs.
     * @param message: generic message.
     */
    void turnEnded(String message);

    /**
     * Asks the player to chose resources during the game setup phase.
     * @param number: number of resources to set
     */
    void askSetupResource(int number) throws ExecutionException;

    /**
     * Shows the match info if asked (eg: number and name of active players, number of turns passed...)
     */
    void showMatchInfo(List<String> playingNames);

    /**
     * Method to end the game and the clients' views.
     * @param winner: winner of the game.
     */
    void showWinMatch(String winner);

    /**
     * Method to show the resource market.
     *
     * {@link it.polimi.ingsw.model.market.ResourceMarket}
     */
    void printResourceMarket(ResourceType[][] resourceMarket, ResourceType extraMarble);

    /**
     * Method to show the available production cards.
     *
     * @param available: available production cards.
     */
    void printAvailableCards(List<ProductionCard> available);

    /**
     * Prints a faith track regarding the player.
     * @param faithTrack: faith track of the player.
     */
    void printFaithTrack(FaithTrack faithTrack);

    /**
     * Prints the favor points obtained by the player
     * @param pope_favor: points obtained
     * @param current_points: current points
     */
    void printPopeFavor(int pope_favor, int current_points);

    /**
     * Prints the action token lorenzo has just played.
     * @param lorenzoTokenReduced: played token.
     */
    void printLorenzoToken(String lorenzoTokenReduced, Color color, int quantity);

    /**
     * Prints the position of lorenzo's Faithmarker on the faithttrack
     * @param faithmarker: position of the faithmarker
     */
    void printLorenzoFaithTrack(int faithmarker);

    /**
     * Prints a list of all the available leaders, both place and available ones.
     * @param leaderCards: a list of owned leader cards.
     */
    void printLeaders(List<LeaderCard> leaderCards);


    /**
     * Prints the player's buffer
     * @param buffer: list of resource types in buffer
     */
    void printBuffer(ArrayList<ResourceType> buffer);

    /**
     * Method to send a reduced String only version of an enemy status.
     */
    void getPeek(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards, List<ResourceType> resourceTypes);

    /**
     * Prints a reduced version of a player's inventory.
     */
    void printInventory(Map<ResourceType, Integer> inventory);

    /**
     * Prints the strongbox
     * @param strongbox: map with the strongbox inventory
     */
    void printStrongbox(Map<ResourceType, Integer> strongbox);

    void printWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras);

    void printProductionBoard(HashMap<Integer, ProductionCard> productionBoard);

    void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output);
}
