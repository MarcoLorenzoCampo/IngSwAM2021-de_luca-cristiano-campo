package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.productionBoard.ProductionSlot;
import it.polimi.ingsw.network.messages.Message;

import java.util.List;

public class ProductionBoardMessage extends Message {

    private static final long serialVersionUID = 2682968447025057568L;
    String productions;
    LeaderCardMessage extra_productions;

    public ProductionBoardMessage(ProductionBoard productionBoard) {
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.PRODUCTION_BOARD);
        extra_productions = new LeaderCardMessage((List) productionBoard.getLeaderProductions());
        ProductionSlot[] slots = productionBoard.getProductionSlots();

        for (int i = 0; i < slots.length ; i++) {
            if(i == 0){
                productions = slots[i].getProductionCard().reduce();
            }
            else
                productions = productions.concat(slots[i].getProductionCard().reduce());
        }
    }

    public LeaderCardMessage getExtra_productions() {
        return extra_productions;
    }

    public String getProductions() {
        return productions;
    }
}
