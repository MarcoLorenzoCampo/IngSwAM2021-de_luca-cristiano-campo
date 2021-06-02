package it.polimi.ingsw.parsers;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ResourceMarketParser {

    /**
     *  Parsing function for ResourceMarket's dimension
     */
    public static int[] parseResourceMarketDimensions() {

        String configPath = "/resourceBoard_dimensions.json";
        Gson resourceBoardConfigReader = new Gson();

        Reader reader = new InputStreamReader(Objects.requireNonNull(ResourceMarketParser.class.getResourceAsStream(configPath)),
                StandardCharsets.UTF_8);

        return resourceBoardConfigReader.fromJson(reader, int[].class);
    }

    /**
     *  Parsing function for ResourceMarket's content
     */
    public static String[] parseResourceMarketContent() {

        Reader reader;
        String resourcesPath = "/given_resources.json";
        Gson resourceBoardConfigReader = new Gson();

        reader = new InputStreamReader(Objects.requireNonNull(ResourceMarketParser.class.getResourceAsStream(resourcesPath)),
                StandardCharsets.UTF_8);

        return resourceBoardConfigReader.fromJson(reader, String[].class);
    }
}
