package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.OfflineClientManager;
import it.polimi.ingsw.network.client.OnlineClientManager;

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

    public void initializeGame(){
        MiniGui miniGui = new MiniGui();
        miniGui.online.addActionListener( e -> miniGui.initializeOnlineGame());
        miniGui.offline.addActionListener(e -> miniGui.initializeOfflineGame());
        miniGui.frame.setVisible(true);

    }

    public void initializeOnlineGame(){
        System.out.println("ONLINE");
        this.frame.dispose();
        GUI guiView = new GUI(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OnlineClientManager OnlineClientManager = new OnlineClientManager(guiView);
                guiView.addObserver(OnlineClientManager);
            }
        });
    }

    //addicon("production"+productioncard.getid()+".png")
    //String name = new String();
    //if(effecttype equals (extrainvetory) name = extra_inventory_COIN.png
    // addicon(leader.geteffectype()+leader.getmainreosurce+".png");

    public void initializeOfflineGame(){
        System.out.println("OFFLINE");
        this.frame.dispose();
        GUI guiView = new GUI(false);
        OfflineClientManager OfflineClientManager = new OfflineClientManager(guiView);
        guiView.addObserver(OfflineClientManager);
    }
}
