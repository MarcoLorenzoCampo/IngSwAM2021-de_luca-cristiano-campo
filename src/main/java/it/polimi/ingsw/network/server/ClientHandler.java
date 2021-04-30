package it.polimi.ingsw.network.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final GameServer gameServer;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private final Object outLock = new Object();
    private final Object inLock = new Object();

    public ClientHandler(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;

        try {
            synchronized (inLock) {
                this.in = new ObjectInputStream(socket.getInputStream());
            }
            synchronized (outLock) {
                this.out = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                String line = in.nextLine();
                if(line.equals("quit")) {
                    gameServer.removePlayerFromGame(socket);
                    System.out.println(socket + "removed from game");
                    break;
                } else {
                    sendMessageToClient("Received: " + line);
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessageToClient(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(message);
    }

}
