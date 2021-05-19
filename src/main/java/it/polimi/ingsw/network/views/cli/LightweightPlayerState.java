package it.polimi.ingsw.network.views.cli;

/**
 * Each player has references of other players, making it more accessible.
 */
public class LightweightPlayerState {

    private final String nickname;
    private String reducedLeaderCards;
    private String reducedProductionBoard;
    private String reducedInventory;
    private String reducedFaithTrack;

    public LightweightPlayerState(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String peekStatus() {
        return reducedLeaderCards + "\n" + reducedInventory + "\n" + reducedProductionBoard + "\n" + reducedFaithTrack;
    }
}
