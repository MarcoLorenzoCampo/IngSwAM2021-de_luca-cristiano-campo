package it.polimi.ingsw.network.views.gui;

import javax.swing.*;

public class OnlineLoginPopUp extends JFrame {

    private  JTextField ip_input;
    private JTextField socket_input;
    private JButton submit;

    public OnlineLoginPopUp (GUI gui){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(null);
        this.setSize(420,320);
        this.setTitle("ONLINE LOGIN");


        JLabel title_1 = new JLabel("INSERT SOCKET PORT NUMBER AND IP ADDRESS");
        title_1.setBounds(20,10,380,20);
        JLabel title_2 = new JLabel("DEFAULT IS   SOCKET = 2200   IP = 0.0.0.0");
        title_2.setBounds(20,40,380,20);

        JLabel socketPort = new JLabel("Socket Port :");
        socketPort.setBounds(20, 100, 100, 20);
        socket_input = new JTextField();
        socket_input.setBounds(120,100,100,20);



        JLabel ipAddress = new JLabel("IP address :");
        ipAddress.setBounds(20, 150, 100, 20);
        ip_input = new JTextField();
        ip_input.setBounds(120,150,100,20);

        submit = new JButton();
        submit.setText("SUBMIT");
        submit.setFocusable(false);
        submit.setBounds(160,200,100,30);
        submit.addActionListener(gui);

        this.add(title_1);
        this.add(title_2);
        this.add(socketPort);
        this.add(socket_input);
        this.add(ipAddress);
        this.add(ip_input);
        this.add(submit);
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getIp_input() {
        return ip_input;
    }

    public JTextField getSocket_input() {
        return socket_input;
    }
}
