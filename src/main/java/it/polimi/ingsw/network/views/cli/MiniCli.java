package it.polimi.ingsw.network.views.cli;

import java.util.Scanner;

public class MiniCli {

    /**
     * Asks the player if the game will be performed in local or online.
     */
    public boolean askLocal() {

        final Scanner scanner;

        printLogo();

        String choice;
        scanner = new Scanner(System.in);

        System.out.println("\nWant to play an online game or a local single player game? \n[OFF = offline, ON = online]");
        System.out.print(">>> ");

        while (true) {

            choice = scanner.nextLine();

            //it's an online game
            if(choice.equalsIgnoreCase("on")) {
                return false;
            }

            //it's an offline game
            if (choice.equalsIgnoreCase("off")) {
                return true;
            }

            System.out.println("\nInvalid input! [OFF = offline, ON = online]");
            System.out.print(">>> ");
        }
    }

    private void printLogo() {
        System.out.println(UsefulStrings.getLogo() + UsefulStrings.getGreetings());
    }
}
