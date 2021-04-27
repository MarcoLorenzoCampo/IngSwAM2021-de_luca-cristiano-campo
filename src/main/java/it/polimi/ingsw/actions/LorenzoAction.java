package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.player.LorenzoPlayer;

public class LorenzoAction extends Action {

    private final PossibleAction actionTag = PossibleAction.LORENZO_ACTION;
    private final LorenzoPlayer lorenzo;

    public LorenzoAction(LorenzoPlayer lorenzo) {
        this.lorenzo = lorenzo;
    }

    @Override
    public void isValid() {
        lorenzo.getLorenzoPlayerBoard()
                .getLorenzoActionToken()
                .performTokenAction(lorenzo);
    }
}
