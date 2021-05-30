package it.polimi.ingsw.parsers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.utilities.ServerConfigPOJO;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ServerConfigParser {

    private static final String serverConfigPath = "/server_config.json";

    public static ServerConfigPOJO readServerConfig() {

        Gson gson = new Gson();
        Reader reader;

        reader = new InputStreamReader(Objects.requireNonNull(ServerConfigParser.class.getResourceAsStream(serverConfigPath)),
                StandardCharsets.UTF_8);

        return gson.fromJson(reader, ServerConfigPOJO.class);
    }
}
