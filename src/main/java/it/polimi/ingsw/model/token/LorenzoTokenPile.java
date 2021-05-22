package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.utilities.builders.LorenzoTokensBuilder;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.LorenzoTokenMessage;

import java.io.Serializable;
import java.util.List;

public class LorenzoTokenPile extends Observable implements Serializable {

    private static final long serialVersionUID = -5070654741566730370L;
    private int lastTaken;
    private final List<AbstractToken> lorenzoTokens;

    public LorenzoTokenPile() {
        lorenzoTokens = LorenzoTokensBuilder.build();
        lastTaken = 0;
    }

    public void performTokenAction(LorenzoPlayer lorenzo) {

        notifyObserver(new LorenzoTokenMessage(lorenzoTokens.get(lastTaken)));

        lorenzoTokens.get(lastTaken%lorenzoTokens.size()).tokenAction(lorenzo);
        lastTaken++;
    }

    public List<AbstractToken> getLorenzoTokens() {
        return lorenzoTokens;
    }
}
