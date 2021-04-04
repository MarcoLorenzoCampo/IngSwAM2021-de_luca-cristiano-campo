package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.FaithResource;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.parsers.ResourceMarketParser;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Marco Lorenzo Campo
 */
public class ResourceMarket {

    private Resource extraMarble;
    private final ArrayList<LinkedList<Resource>> resourceBoard;

    /**
     * Data Structure: ArrayList of LinkedLists, each one contains
     * either a row or column of the real marketBoard.
     *
     * It allows to pick resources based on a single int
     * @throws FileNotFoundException -- parsing exception
     */
    public ResourceMarket() throws FileNotFoundException {

        String[] JSONResources;
        List<Resource> realResources = new LinkedList<>();

        ResourceMarketParser resourceMarketParser = new ResourceMarketParser();

        int[] dimensions = resourceMarketParser.parseResourceMarketDimensions();
        JSONResources = resourceMarketParser.parseResourceMarketContent();

        /* creating a list of resources from JSON file */
        for (String jsonResource : JSONResources) {
            if (jsonResource.equals(ResourceType.FAITH.toString())) {
                realResources.add(new FaithResource());
            } else {
                realResources.add(new MaterialResource(jsonResource));
            }
        }

        Collections.shuffle(realResources);

        extraMarble = realResources.get(dimensions[0]*dimensions[1]);


        resourceBoard = new ArrayList<>();

        for(int i=0; i < dimensions[1]; i++) {
            LinkedList<Resource> temp = new LinkedList<>();
            for(int j=0; j < realResources.toArray().length - 1; j++) {

                /* adds columns meaning first 4 elements */
                if(j % dimensions[1] == i) {
                    temp.add(realResources.get(j));
                }
            }
            resourceBoard.add(temp);
        }

        int cicles = 0;
        for(int i = dimensions[1]; i < dimensions[1]+dimensions[0]; i++) {
            LinkedList<Resource> temp = new LinkedList<>();
            for(int j = dimensions[1]*cicles; j < dimensions[1]*(cicles+1); j++) {
                temp.add(realResources.get(j));
            }
            Collections.reverse(temp);
            resourceBoard.add(temp);
            cicles++;
        }
    }


    /**
     * Places the extra marble, shifting the content
     * of the board
     * @param index -- selects the dimension to be shifted
     */
    private void placeExtraMarble(int index) {

        Resource temp = resourceBoard.get(index).getLast();

        for(int i=resourceBoard.get(index).toArray().length - 1; i > 0; i--) {
            resourceBoard.get(index).get(i)
                    .setResourceType(resourceBoard.get(index).get(i-1).getResourceType());
        }

        resourceBoard.get(index).get(0).setResourceType(extraMarble.getResourceType());
        extraMarble = temp;
    }

    /**
     * Resources are collected by the deposit() method
     * {@link MaterialResource}
     * {@link FaithResource}
     */
    public void pickResources(int index) throws IndexOutOfBoundsException {

        LinkedList<Resource> obtainedResources = resourceBoard.get(index);

        placeExtraMarble(index);

        obtainedResources.forEach(Resource::deposit);
    }

    public ArrayList<LinkedList<Resource>> getResourceBoard() {
        return resourceBoard;
    }
}
