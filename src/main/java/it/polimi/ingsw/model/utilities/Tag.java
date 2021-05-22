package it.polimi.ingsw.model.utilities;

import java.io.Serializable;

abstract class Tag implements Serializable {
    private static final long serialVersionUID = 6258127563979295716L;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ", quantity = " + quantity;
    }
}
