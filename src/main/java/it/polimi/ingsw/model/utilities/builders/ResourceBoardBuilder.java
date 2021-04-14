package it.polimi.ingsw.model.utilities.builders;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marco Lorenzo Campo
 *
 * Utility class needed to clean the constructor for the
 * resourceMarket
 */
public final class ResourceBoardBuilder {

    public static ResourceType[][] build(int[] dimensions, String[] content) {

        ResourceType[][] resourceBoard = new ResourceType[dimensions[0]][dimensions[1]];

        int cycles = 0;
        for (int row=0; row < resourceBoard.length; row++)
        {
            for (int col=resourceBoard[row].length-1; col >=0 ; col--)
            {
                resourceBoard[row][col] = ResourceType.valueOf(content[cycles]);
                cycles++;
            }
        }

        return resourceBoard;
    }
}
