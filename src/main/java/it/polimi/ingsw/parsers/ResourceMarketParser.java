package it.polimi.ingsw.parsers;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public final class ResourceMarketParser {

    /**
     *  Parsing function for ResourceMarket's dimension
     */
    public static int[] parseResourceMarketDimensions() {

        FileReader reader = null;
        String configPath = "src/main/resources/resourceBoard_dimensions.json";
        Gson resourceBoardConfigReader = new Gson();

        try {
            reader = new FileReader(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        return resourceBoardConfigReader.fromJson(reader, int[].class);
    }

    /**
     *  Parsing function for ResourceMarket's content
     */
    public static String[] parseResourceMarketContent() {

        FileReader reader = null;
        String resourcesPath = "src/main/resources/given_resources.json";
        Gson resourceBoardConfigReader = new Gson();

        try {
            reader = new FileReader(resourcesPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        return resourceBoardConfigReader.fromJson(reader, String[].class);
    }
}
