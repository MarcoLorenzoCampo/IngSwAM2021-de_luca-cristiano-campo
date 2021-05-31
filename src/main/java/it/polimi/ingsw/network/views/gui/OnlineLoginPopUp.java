package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.network.utilities.NetworkInfoValidator;

import javax.swing.*;
import java.awt.*;

public class OnlineLoginPopUp extends JPanel {

    private  JTextField ip_input;
    private JTextField socket_input;
    private JButton submit;
    private GUI gui;

    public OnlineLoginPopUp (GUI gui){
        this.gui = gui;
        this.setLayout(new BorderLayout(5, 30));

        JPanel title = new JPanel();

        JPanel central = new JPanel();
        central.setLayout(new BorderLayout(5, 30));
        JPanel upperCentral = new JPanel();
        JPanel lowerCentral = new JPanel();
        JPanel bottom = new JPanel();


        JLabel title_1 = new JLabel("<html>INSERT SOCKET PORT NUMBER AND IP ADDRESS<br>DEFAULT IS   SOCKET = 2200   IP = 0.0.0.0</html>");
        title.setLayout(new FlowLayout());
        title.add(title_1);


        upperCentral.setLayout(new FlowLayout());
        JLabel socketPort = new JLabel("Socket Port :");
        socket_input = new JTextField();
        socket_input.setPreferredSize(new Dimension(200, 30));
        upperCentral.add(socketPort);
        upperCentral.add(socket_input);


        lowerCentral.setLayout(new FlowLayout());
        JLabel ipAddress = new JLabel("IP address :");
        ip_input = new JTextField();
        ip_input.setPreferredSize(new Dimension(200, 30));
        lowerCentral.add(ipAddress);
        lowerCentral.add(ip_input);

        bottom.setLayout(new FlowLayout());
        submit = new JButton();
        submit.setText("SUBMIT");
        submit.setFocusable(false);
        submit.setBounds(160,200,100,30);
        submit.addActionListener(gui);
        bottom.add(submit);

        socket_input.setText("2200");
        ip_input.setText("0.0.0.0");

        this.add(title, BorderLayout.NORTH);
        central.add(upperCentral, BorderLayout.NORTH);
        central.add(lowerCentral, BorderLayout.CENTER);
        this.add(central, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        this.setVisible(true);
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
