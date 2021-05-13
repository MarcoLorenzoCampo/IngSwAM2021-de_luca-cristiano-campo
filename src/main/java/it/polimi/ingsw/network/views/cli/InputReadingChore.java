package it.polimi.ingsw.network.views.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This class is used to read the input stream asynchronously.
 */
public class InputReadingChore implements Callable<String> {

    private final BufferedReader bufferedReader;

    public InputReadingChore() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws IOException, InterruptedException {
        String input;

        while (!bufferedReader.ready()) {
            Thread.sleep(200);
        }

        input = bufferedReader.readLine();
        return input;
    }
}