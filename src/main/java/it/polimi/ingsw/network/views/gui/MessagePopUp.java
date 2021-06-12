package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.util.ArrayList;

public class MessagePopUp extends JFrame {
    JLabel showed;
    ArrayList<String> messages;
    public MessagePopUp(String message){
        messages = new ArrayList<>();
        messages.add("<html>");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(420,320);
        this.setTitle("MESSAGE");
        messages.add(message);
        messages.add("</html>");
        String text = "";
        for (String iterator : messages) {
            text = text.concat(iterator);
        }
        showed = new JLabel(text);
        showed.setHorizontalAlignment(SwingConstants.CENTER);
        showed.setVerticalAlignment(SwingConstants.CENTER);
        this.add(showed);
    }

    public void changeMessage(String newMessage){
        if(messages.size()>=16){
            messages.remove(1);
            messages.remove(2);
        }

            messages.remove(messages.size() - 1);
            messages.add("<br>");
            messages.add(newMessage);
            messages.add("</html>");
            String text = "";
            for (String iterator : messages) {
                text = text.concat(iterator);
            }
            showed.setText(text);
            //this.setVisible(true);

    }

}
