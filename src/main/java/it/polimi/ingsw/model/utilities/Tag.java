package it.polimi.ingsw.model.utilities;

abstract class Tag {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "quantity=" + quantity +
                '}';
    }
}
