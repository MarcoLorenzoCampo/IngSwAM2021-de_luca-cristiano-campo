package it.polimi.ingsw.parsers;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ResourceMarketParser {

    /**
     *  Parsing function for ResourceMarket's dimension
     * @throws FileNotFoundException -- if incorrect JSON path
     */
    public int[] parseResourceMarketDimensions() throws FileNotFoundException {


        String configPath = "src/main/resources/resourceBoard_dimensions.json";
        Gson resourceBoardConfigReader = new Gson();

        return resourceBoardConfigReader.fromJson(new FileReader(configPath), int[].class);
    }

    /**
     *  Parsing function for ResourceMarket's content
     * @throws FileNotFoundException -- if incorrect JSON path
     */
    public String[] parseResourceMarketContent() throws FileNotFoundException {

        String resourcesPath = "src/main/resources/given_resources.json";
        Gson resourceBoardConfigReader = new Gson();

        return resourceBoardConfigReader.fromJson(new FileReader(resourcesPath), String[].class);
    }
}
