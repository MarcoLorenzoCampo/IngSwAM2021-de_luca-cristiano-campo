package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.*;

public class PlayerNumberPopUp extends JFrame {
    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;

    PlayerNumberPopUp(GUI gui){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(420,320);
        this.setTitle("NUMBER OF PLAYERS");

        JLabel numberOfPlayers = new JLabel("Select the number of players");
        numberOfPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfPlayers.setVerticalAlignment(SwingConstants.TOP);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(420,120);

        one = new JButton("1");
        one.setSize(105,60);
        one.setFocusable(false);
        one.addActionListener(gui);

        two = new JButton("2");
        two.setSize(105,60);
        two.setFocusable(false);
        two.addActionListener(gui);

        three = new JButton("3");
        three.setSize(105,60);
        three.setFocusable(false);
        three.addActionListener(gui);

        four = new JButton("4");
        four.setSize(105,60);
        four.setFocusable(false);
        four.addActionListener(gui);


        buttonPanel.add(one);
        buttonPanel.add(two);
        buttonPanel.add(three);
        buttonPanel.add(four);

        this.add(numberOfPlayers);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public JButton getOne() {
        return one;
    }

    public JButton getTwo() {
        return two;
    }

    public JButton getThree() {
        return three;
    }

    public JButton getFour() {
        return four;
    }
}
