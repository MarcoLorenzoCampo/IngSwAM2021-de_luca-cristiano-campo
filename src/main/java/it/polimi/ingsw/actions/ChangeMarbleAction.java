package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

import static it.polimi.ingsw.enumerations.ResourceType.UNDEFINED;

/**
 * Action to change a white marble when the choice is given (two exchange powers active)
 * it has the color in which to change and the position of the white marble to change in the buffer
 */
public class ChangeMarbleAction extends Action{
    private final PossibleAction actionTag = PossibleAction.CHANGE_COLOR;

    private final ResourceType color;
    private final int index;



    public ChangeMarbleAction(String sender, ResourceType type, int number){
        super.setActionSender(sender);
        this.color = type;
        this.index = number;
    }

    public int getIndex() {
        return index;
    }

    public ResourceType getColor() {
        return color;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
