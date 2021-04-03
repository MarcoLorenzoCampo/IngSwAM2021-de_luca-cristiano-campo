package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class ResourceMarketTest {

    private ResourceMarket resourceMarket;

    {
        try {
            resourceMarket = new ResourceMarket();
            System.out.println(resourceMarket.getResourceBoard());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void pickResources() {


    }
}