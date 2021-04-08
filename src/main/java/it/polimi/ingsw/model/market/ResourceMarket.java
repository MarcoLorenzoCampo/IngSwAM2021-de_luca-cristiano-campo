package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.FaithResource;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.builders.ResourceBoardBuilder;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import it.polimi.ingsw.parsers.ResourceMarketParser;

import java.io.FileNotFoundException;
import java.util.LinkedList;


/**
 * @author Marco Lorenzo Campo
 */
public class ResourceMarket {

    private ResourceType extraMarble;
    private final ResourceType[][] resourceBoard;
    private final int[] dimensions;

    /**
     * @throws FileNotFoundException -- parsing exception
     */
    public ResourceMarket() throws FileNotFoundException {

        ResourceMarketParser resourceMarketParser = new ResourceMarketParser();
        ResourceBoardBuilder boardBuilder = new ResourceBoardBuilder();

        dimensions = resourceMarketParser.parseResourceMarketDimensions();
        String[] jsonResources = resourceMarketParser.parseResourceMarketContent();
        //Collections.shuffle(Arrays.asList(jsonResources));

        extraMarble = ResourceType.valueOf(jsonResources[dimensions[1]*dimensions[0]]);

        resourceBoard = boardBuilder.build(dimensions, jsonResources);
    }

    /**
     * "Places" the extra marble, shifting the content
     * of the board
     * @param index -- selects the dimension to be shifted
     */
    private void placeExtraMarble(int index, ResourceType temp) {

        if((index >=0) && (index < dimensions[1])) {

            /* add to pickedResources every element in position ( xx , index) */
            for (int row = dimensions[0]-1; row>=0; row--) {
                for (int col = 0; col < dimensions[1]; col++) {

                    if (col == index) {

                        for(int i=0; i < dimensions[1]-2; i++) {
                            resourceBoard[row-i][col] = resourceBoard[row-i-1][col];
                        }

                        resourceBoard[0][index] = extraMarble;
                        extraMarble = temp;

                        return;
                    }
                }
            }
        }

        /* pick one of the rows */
        if((index > dimensions[0]) && (index < dimensions[1]+dimensions[0])) {

            /* add to pickedResources every element in position ( index , xx ) */
            for (int row=0; row < resourceBoard.length; row++) {

                if(row == (index-dimensions[0]-1)) {

                    if (resourceBoard[row].length - 1 >= 0)
                        System.arraycopy(resourceBoard[row], 1, resourceBoard[row], 0, resourceBoard[row].length - 1);

                    resourceBoard[row][dimensions[1]-1] = extraMarble;
                    extraMarble = temp;

                    return;
                }
            }

        }

    }


    /**
     * @param index dimension picked
     * @return list of the resources chosen from market
     * @throws IndexOutOfBoundsException if the player's input exceed
     * market's dimension
     */
    private LinkedList<ResourceType> pickResourceLine(int index) throws IndexOutOfBoundsException{

        LinkedList<ResourceType> pickedResources = new LinkedList<>();

        /* pick one of the columns */
        if((index >=0) && (index < dimensions[1])) {

            /* add to pickedResources every element in position ( xx , index) */
            for (ResourceType[] resourceTypes : resourceBoard) {
                for (int col = 0; col < resourceTypes.length; col++) {

                    if (col == index) {
                        pickedResources.addLast(resourceTypes[col]);
                    }
                }
            }
            return pickedResources;
        }

        /* pick one of the rows */
        if((index > dimensions[0]) && (index < dimensions[1]+dimensions[0])) {

            /* add to pickedResources every element in position ( index , xx ) */
            for (int row=0; row < resourceBoard.length; row++) {
                for (int col=resourceBoard[row].length-1; col >=0 ; col--) {

                    if(row == (index-dimensions[0]-1))
                        pickedResources.addLast(resourceBoard[row][col]);
                }
            }
            return pickedResources;
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Resources are collected by the deposit() method
     * {@link MaterialResource}
     * {@link FaithResource}
     */
    public void pickResources(int index) {

        LinkedList<ResourceType> pickedFromMarket = pickResourceLine(index);

        placeExtraMarble(index, pickedFromMarket.getLast());

        ResourceBuilder resourceBuilder = new ResourceBuilder();
        LinkedList<Resource> obtainedResources = resourceBuilder.build(pickedFromMarket);

        obtainedResources.forEach(Resource::deposit);
    }

    public ResourceType[][] getResourceBoard() {
        return resourceBoard;
    }
}
