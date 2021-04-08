package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.model.market.GameBoard;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Marco Lorenzo Campo
 */
public class Game implements Serializable {

    private GameBoard gameBoard;
    private LinkedList<Player> playerList;
    private static Game gameInstance;
    private GameState currentState;

    /**
     * Initializes the main elements of a game: gameInstance,
     * gameBoard for all players and a empty player list to be filled.
     * @throws FileNotFoundException -- parsing error
     */
    public void startGame() throws FileNotFoundException {
        gameInstance = getGameInstance();
        gameBoard = GameBoard.getGameBoardInstance();
        playerList = new LinkedList<>();
    }

    /**
     *
     * @param player -- winner
     * @throws EndGameException telling the controller game is ending next turn
     */
    public void endGame(Player player) throws EndGameException {
        System.out.println("Game ended, " + player.getName() + " won!");

        throw new EndGameException();
    }

    private Game() { }

    /**
     * @return gameInstance: returns the single instance of the game
     * created for a match
     */
    public Game getGameInstance()  {

        /* Create game instance if not present; */
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public void addPlayer(String newName) throws NameTakenException {

        checkNewName(newName);
        playerList.add(new Player(newName));
    }

    /**
     * @param newName -- potential name to add
     * @throws NameTakenException -- indicates newName is
     * already taken
     */
    private void checkNewName(String newName) throws NameTakenException {

        for(Player player : playerList) {
            if(player.getName().equals(newName))
                throw new NameTakenException(newName);
        }
    }

    /* getters */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public LinkedList<Player> getPlayerList() {
        return playerList;
    }
}
