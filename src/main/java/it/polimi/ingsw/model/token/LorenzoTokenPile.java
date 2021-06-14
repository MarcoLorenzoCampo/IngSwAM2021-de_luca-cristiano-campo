package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.utilities.builders.LorenzoTokensBuilder;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.LorenzoTokenMessage;

import java.io.Serializable;
import java.util.List;

public class LorenzoTokenPile extends Observable {

    private int lastTaken;
    private final List<AbstractToken> lorenzoTokens;

    public LorenzoTokenPile() {
        lorenzoTokens = LorenzoTokensBuilder.build();
        lastTaken = 0;
    }

    public void performTokenAction(LorenzoPlayer lorenzo) {

        int nextToken = lastTaken%lorenzoTokens.size();

        notifyObserver(new LorenzoTokenMessage(lorenzoTokens.get(nextToken).toString(), lorenzoTokens.get(nextToken).getColor(), lorenzoTokens.get(nextToken).getQuantity()));

        lorenzoTokens.get(nextToken).tokenAction(lorenzo);
        lastTaken++;
    }

    public List<AbstractToken> getLorenzoTokens() {
        return lorenzoTokens;
    }
}
