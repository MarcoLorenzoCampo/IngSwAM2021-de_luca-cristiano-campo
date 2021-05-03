package it.polimi.ingsw.network.utilities;

/**
 * Auxiliary class to store server configuration parameters after parsing.
 */
public class ServerConfigPOJO {

    private int port;

    private String gameMode;

    public int getPort() {
        return port;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
