package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.PingMessage;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.views.cli.MiniCli;
import it.polimi.ingsw.network.views.cli.CLI;
import it.polimi.ingsw.network.views.gui.MiniGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class to handle client operations.
 */
public class Client extends Observable implements IClient {

    private final IView view;
    /**
     * Socket that connects and communicates with the server's socket.
     */
    private final Socket clientSocket;

    /**
     * Streams to exchange messages {@link Message} with
     * the server's socket.
     */
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Thread to enable reading from server.
     */
    private final ExecutorService serverListener;

    /**
     * Pinger thread.
     */
    private ScheduledExecutorService pinger;

    /**
     * Client constructor. Connects the client to the server's socket.
     *
     * @param port:       server's socket.
     * @param IP_Address: IP address.
     */
    public Client(int port, String IP_Address, IView view) throws IOException {

        this.view = view;

        this.clientSocket = new Socket();

        this.clientSocket.connect(new InetSocketAddress(IP_Address, port));

        this.serverListener = Executors.newSingleThreadExecutor();

        //this.pinger = Executors.newSingleThreadScheduledExecutor();

        try {

            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            //clientLogger.info(() -> "Connection successful.");

        } catch (IOException e) {
            clientLogger.severe(() -> "Couldn't connect to the host.");

        }
    }

    /**
     * Method to run an asynchronous thread to work as input listener, waiting for messages
     * sent by the server.
     */
    @Override
    public void readMessage() {
        serverListener.execute(() -> {

            while (!serverListener.isShutdown()) {
                Message message = null;
                try {
                    message = (Message) input.readObject();
                    //Client.clientLogger.info("Received: " + message);
                } catch (EOFException e) {
                    //Client.clientLogger.info("Stream error.");
                    view.showError("Dropped connection from the server!");
                    disconnect();
                    serverListener.shutdownNow();
                } catch (IOException  e) {
                    view.showError("Dropped connection from the server!");
                    //Client.clientLogger.info("Connection lost with the server.");
                    disconnect();
                    serverListener.shutdownNow();
                } catch (ClassNotFoundException e) {
                    Client.clientLogger.info("Unexpected object.");
                    disconnect();
                    serverListener.shutdownNow();
                }
                notifyObserver(message);
            }
        });
    }

    @Override
    public void sendMessage(Message message) {

        try {
            output.writeObject(message);
            output.reset();

        } catch (IOException e) {

            clientLogger.severe(() -> "Unable to send the message.");
            disconnect();
        }
    }

    @Override
    public void disconnect() {

        if (!clientSocket.isClosed()) {
            try {
                clientSocket.close();
                System.exit(1);

            } catch (IOException e) {

                clientLogger.severe(() -> "Could not disconnect. Critical error.");
            }
        }
    }

    /**
     * Enable a periodic ping message from the client to the server to keep the connection alive
     * and detectable to the server.
     * @param enabled: true if enabled, false if disabled.
     */
    public void startPing(boolean enabled) {
        if (enabled) {
            pinger.scheduleAtFixedRate(
                    () -> sendMessage(new PingMessage()), 0, 1000, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }

    //------------------------------------------ MAIN METHOD -----------------------------------------------

    /**
     * Main method of the client class, it can be launched in both cli or gui mode.
     * The default option is cli, it can be used in gui mode by adding "-gui" when running the jar file.
     *
     * user @ user:$ client.jar -cli
     */
    public static void main(String[] args) {

        //Used only while making the cli.
        String viewType = "-cli";

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-gui")) {
                viewType = "-gui";
                break;
            }
        }

        if (viewType.equalsIgnoreCase("-cli")) {

            MiniCli minicli = new MiniCli();
            boolean isLocal = minicli.askLocal();

            CLI cliView = new CLI(isLocal);

            if(!isLocal) {

                OnlineClientManager OnlineClientManager = new OnlineClientManager(cliView);
                cliView.addObserver(OnlineClientManager);

            } else {

                OfflineClientManager OfflineClientManager = new OfflineClientManager(cliView);
                cliView.addObserver(OfflineClientManager);
            }

            cliView.startCli();

        } else {
            MiniGui miniGui = new MiniGui();
        }
    }
}
