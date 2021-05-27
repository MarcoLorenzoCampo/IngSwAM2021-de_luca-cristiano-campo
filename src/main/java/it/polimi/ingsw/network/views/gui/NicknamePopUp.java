package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.security.cert.CertificateEncodingException;

public class NicknamePopUp extends JFrame {
    private JTextField username;
    private JButton submit;

    public NicknamePopUp(GUI gui){


        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(420,320);
        this.setTitle("NICKNAME");
        this.setLayout(null);

        JLabel nickname = new JLabel("Nickname :");
        nickname.setBounds(20,100,200,20);

        username = new JTextField();
        username.setBounds(70,160,200,20);


        submit = new JButton("SUBMIT");
        submit.addActionListener(gui);
        submit.setBounds(160,200,100,30);
        submit.setFocusable(false);


        this.add(submit);
        this.add(nickname);
        this.add(username);
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getUsername() {
        return username;
    }
}
