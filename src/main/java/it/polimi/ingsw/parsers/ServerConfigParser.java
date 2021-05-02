package it.polimi.ingsw.parsers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.utilities.ServerConfigPOJO;

import java.io.FileNotFoundException;
import java.io.FileReader;

public final class ServerConfigParser {

    private static final String serverConfigPath = "src/main/resources/server_config.json";

    public static ServerConfigPOJO readServerConfig() {

        Gson gson = new Gson();
        FileReader reader = null;

        try {
            reader = new FileReader(serverConfigPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;

        return gson.fromJson(reader, ServerConfigPOJO.class);
    }
}
