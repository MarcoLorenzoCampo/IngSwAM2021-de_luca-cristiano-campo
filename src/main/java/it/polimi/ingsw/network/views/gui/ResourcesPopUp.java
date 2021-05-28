package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourcesPopUp extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JLabel title;
    private JPanel centralPanel;
    private JPanel buttonPanel;
    private JButton STONEButton;
    private JButton SHIELDButton;
    private JButton SERVANTButton;
    private JButton COINButton;
    private JPanel inputPanel;
    private JTextField textField1;
    private JPanel firstRow;
    private JPanel bottomPanel;
    private JTextField textField2;
    private JLabel firstResource;
    private JLabel secondResource;
    private JPanel submitPanel;
    private JPanel secondRow;
    private JButton SUBMITButton;
    private JButton DELETEButton;
    private int number;

    public ResourcesPopUp(GUI gui, int number){
        super("SETUP RESOURCES");


        this.number = number;
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setContentPane(mainPanel);

        title.setText("YOU HAVE " + number + " RESOURCES TO PICK");

        STONEButton.setFocusable(false);
        STONEButton.addActionListener(this);

        SHIELDButton.setFocusable(false);
        SHIELDButton.addActionListener(this);

        SERVANTButton.setFocusable(false);
        SERVANTButton.addActionListener(this);

        COINButton.setFocusable(false);
        COINButton.addActionListener(this);

        DELETEButton.setFocusable(false);
        DELETEButton.addActionListener(this);

        SUBMITButton.setFocusable(false);
        //SUBMITButton.addActionListener(gui);

        textField1.setEditable(false);
        textField2.setEditable(false);

        if(number<2){
            secondRow.setVisible(false);
        }
        this.pack();
    }

    public int getNumber() {
        return number;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    //public static void main(String[] args) {
    //    ResourcesPopUp prova = new ResourcesPopUp(new GUI(true), 2);
    //    prova.setVisible(true);
    //}


    public JButton getSUBMITButton() {
        return SUBMITButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(STONEButton)) {
            if (textField1.getText().isEmpty()) {
                textField1.setText("STONE");
            } else if (textField2.getText().isEmpty() && secondRow.isVisible()) {
                textField2.setText("STONE");
            }
        }
        if(e.getSource().equals(COINButton)) {
            if (textField1.getText().isEmpty()) {
                textField1.setText("COIN");
            } else if (textField2.getText().isEmpty() && secondRow.isVisible()) {
                textField2.setText("COIN");
            }
        }
        if(e.getSource().equals(SHIELDButton)) {
            if (textField1.getText().isEmpty()) {
                textField1.setText("SHIELD");
            } else if (textField2.getText().isEmpty() && secondRow.isVisible()) {
                textField2.setText("SHIELD");
            }
        }
        if(e.getSource().equals(SERVANTButton)) {
            if (textField1.getText().isEmpty()) {
                textField1.setText("SERVANT");
            } else if (textField2.getText().isEmpty() && secondRow.isVisible()) {
                textField2.setText("SERVANT");
            }
        }
        if(e.getSource().equals(DELETEButton)){
           textField1.setText("");
           textField2.setText("");
        }
    }
}