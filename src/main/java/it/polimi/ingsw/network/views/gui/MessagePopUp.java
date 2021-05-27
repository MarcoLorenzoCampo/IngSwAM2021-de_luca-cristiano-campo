package it.polimi.ingsw.network.views.gui;

import javax.swing.*;

public class MessagePopUp extends JFrame {
    JLabel showed;
    public MessagePopUp(String message){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(420,320);
        this.setTitle("MESSAGE");
        showed = new JLabel(message);
        showed.setHorizontalAlignment(SwingConstants.CENTER);
        showed.setVerticalAlignment(SwingConstants.CENTER);
        this.add(showed);
    }

    public void changeMessage(String newMessage){
        showed.setText(newMessage);
        this.setVisible(true);
    }
}
