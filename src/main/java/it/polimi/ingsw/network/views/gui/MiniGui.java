package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.network.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniGui  {

    JFrame frame;
    JPanel panel;
    JLabel label;
    JButton online;
    JButton offline;
    boolean isChosen;
    boolean option;

    public MiniGui(){

        isChosen = false;

        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel();
        online = new JButton();
        offline = new JButton();

        frame.setTitle("Local game setup");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(420,320);
        frame.setLayout(null);

        panel.setBounds(0,50,420,20);
        panel.setLayout(new BorderLayout());

        online.setBounds(50,160,120,40);
        online.setText("ONLINE");
        online.setFocusable(false);


        offline.setBounds(230,160,120,40);
        offline.setText("OFFLINE");
        offline.setFocusable(false);



        label.setText("Do you want to play online or offline?");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);


        panel.add(label);

        frame.add(panel);
        frame.add(online);
        frame.add(offline);
    }

    public boolean askLocal(){
        frame.setVisible(true);

        while(!isChosen) {
        }
        return option;
    }

}
