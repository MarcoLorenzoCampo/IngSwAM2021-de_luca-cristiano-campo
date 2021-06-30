package it.polimi.ingsw.model.productionBoard;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.market.ProductionCard;

/**
 * Slot that holds only one owned production card at a time, the slot
 * can change the card only if the new one is exactly one level above the current one.
 * At the start its's empty and can only hold cards of level one
 */
public class ProductionSlot {
    private ProductionCard productionCard;
    private boolean selected = false;
    private Level level = Level.ANY;

    public ProductionCard getProductionCard() {
        return productionCard;
    }

    public void setProductionCard(ProductionCard productionCard) {
        this.productionCard = productionCard;
        this.level = productionCard.getLevel();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return !selected;
    }

    public Level getLevel() {
        return level;
    }

    public boolean canPlaceAnotherCard(ProductionCard newCard){
        return (newCard.getLevel().ordinal() == (level.ordinal() + 1));
    }



}
