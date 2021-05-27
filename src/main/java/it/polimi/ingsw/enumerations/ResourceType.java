package it.polimi.ingsw.enumerations;

import it.polimi.ingsw.model.utilities.MaterialResource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public enum ResourceType {
    COIN,
    SHIELD,
    SERVANT,
    STONE,
    FAITH,
    UNDEFINED;

    /**
     * Method to return a random resource from the accepted ones: SHIELD, SERVANT, STONE.
     * @return: SHIELD || SERVANT || STONE.
     */
    public static MaterialResource randomizedMaterialResource() {
        Random rand = new Random();
        int value = rand.nextInt(3);

        return new MaterialResource(Arrays.stream(ResourceType.values())
                .collect(Collectors.toList())
                .get(value));
    }
}

