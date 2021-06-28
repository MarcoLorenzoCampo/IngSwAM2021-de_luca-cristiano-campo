package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.Visitor;

public class LorenzoAction extends Action {

    private final PossibleAction actionTag = PossibleAction.LORENZO_ACTION;
    private final LorenzoPlayer lorenzo;

    public LorenzoAction(LorenzoPlayer lorenzo) {
        this.lorenzo = lorenzo;
    }

    public void isValid() {
        lorenzo.getLorenzoPlayerBoard()
                .getLorenzoTokenPile()
                .performTokenAction(lorenzo);
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
