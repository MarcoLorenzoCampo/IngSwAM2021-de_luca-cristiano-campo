package it.polimi.ingsw.network.views;

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
     * Asks the player to replace the grey marble (UNDEFINED resource) with something if he has
     * enough active leader cards.
     */
    void askReplacementResource();

    /**
     * Asks the player to store or discard a resource that has to be deposited;
     */
    void askToDiscard();

    /**
     * Tells the player some action/message sent wasn't validated.
     * @param errorMessage: specific cause of the refusal.
     */
    void showError(String errorMessage);

    /**
     * Asks the player to chose resources during the game setup phase.
     */
    void askSetupResource();
}
