package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.network.client.ClientManager;

import javax.swing.*;
import java.awt.*;

public class MiniGui  {
    int width;
    int height;
    JFrame frame;
    StartPanel panel;
    JLabel label;
    JButton online;
    JButton offline;
    boolean isChosen;
    boolean option;

    public MiniGui(){
        width = 500;
        height = 700;
        isChosen = false;
/*
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



 */
        frame = new JFrame();
        panel = new StartPanel();
        panel.setLayout(new BorderLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setPreferredSize(new Dimension(width, 10*width/11));
        innerPanel.setLayout(new BorderLayout(0, width/5));
        innerPanel.setOpaque(false);

        JPanel inner_buttons = new JPanel();
        inner_buttons.setOpaque(false);


        online = new JButton();
        offline = new JButton();
        label = new JLabel();

        //online.setBounds(50,160,120,40);
        online.setText("ONLINE");
        online.setFocusable(false);
        online.setPreferredSize(new Dimension(3*width/10, width/10));


        //offline.setBounds(230,160,120,40);
        offline.setText("OFFLINE");
        offline.setFocusable(false);
        offline.setPreferredSize(new Dimension(3*width/10, width/10));


        label.setFont(new Font("Monaco", Font.PLAIN, width/10));
        label.setText("<html>Do you want to play<BR> online or offline?</html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        innerPanel.add(label, BorderLayout.NORTH);
        inner_buttons.add(online);
        inner_buttons.add(offline);
        innerPanel.add(inner_buttons, BorderLayout.CENTER);

        panel.update(innerPanel);
        //panel.add(innerPanel, BorderLayout.SOUTH);
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.revalidate();
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-width/2, dim.height/2-4*height/7);
        frame.setSize(width, height);
        frame.repaint();
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
        ClientManager ClientManager = new ClientManager(guiView, false);
        guiView.addObserver(ClientManager);
    }


    public void initializeOfflineGame(){
        System.out.println("OFFLINE");
        this.frame.dispose();
        GUI guiView = new GUI(false);
        ClientManager OfflineClientManager = new ClientManager(guiView, true);
        guiView.addObserver(OfflineClientManager);
    }
}
