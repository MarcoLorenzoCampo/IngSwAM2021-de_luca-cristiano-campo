package it.polimi.ingsw.views.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourcesPopUp extends JPanel implements ActionListener {
    private final JPanel mainPanel;
    private final JPanel titlePanel;
    private final JLabel title;
    private final JPanel centralPanel;
    private final JPanel buttonPanel;
    private final JButton STONEButton;
    private final JButton SHIELDButton;
    private final JButton SERVANTButton;
    private final JButton COINButton;
    private final JPanel inputPanel;
    private final JTextField textField1;
    private final JPanel firstRow;
    private final JPanel bottomPanel;
    private final JTextField textField2;
    private final JLabel firstResource;
    private final JLabel secondResource;
    private final JPanel submitPanel;
    private final JPanel secondRow;
    private final JButton SUBMITButton;
    private final JButton DELETEButton;
    private final int number;

    public ResourcesPopUp( int number){
        this.number = number;

        mainPanel = new JPanel();
        titlePanel = new JPanel();
        title = new JLabel();
        centralPanel = new JPanel();
        buttonPanel = new JPanel();
        STONEButton = new JButton("STONE");
        SHIELDButton = new JButton("SHIELD");
        SERVANTButton = new JButton("SERVANT");
        COINButton = new JButton("COIN");
        inputPanel = new JPanel();
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(200, 30));
        firstRow = new JPanel();
        bottomPanel = new JPanel();
        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(200, 30));
        firstResource = new JLabel("first resource");
        secondResource = new JLabel("second resource");
        submitPanel = new JPanel();
        secondRow = new JPanel();
        SUBMITButton = new JButton("SUBMIT");
        DELETEButton = new JButton("DELETE");


        mainPanel.setOpaque(false);
        titlePanel.setOpaque(false);
        centralPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        inputPanel.setOpaque(false);
        firstRow.setOpaque(false);
        bottomPanel.setOpaque(false);
        secondRow.setOpaque(false);
        submitPanel.setOpaque(false);

        title.setText("YOU HAVE " + number + " RESOURCES TO PICK");

        mainPanel.setLayout(new BorderLayout());
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        submitPanel.add(SUBMITButton);
        submitPanel.add(DELETEButton);
        secondRow.add(secondResource);
        secondRow.add(textField2);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(secondRow, BorderLayout.CENTER);
        bottomPanel.add(submitPanel, BorderLayout.SOUTH);

        firstRow.add(firstResource);
        firstRow.add(textField1);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(firstRow, BorderLayout.CENTER);
        inputPanel.add(bottomPanel, BorderLayout.SOUTH);

        buttonPanel.add(STONEButton);
        buttonPanel.add(SHIELDButton);
        buttonPanel.add(COINButton);
        buttonPanel.add(SERVANTButton);

        centralPanel.setLayout(new BorderLayout());
        centralPanel.add(buttonPanel, BorderLayout.NORTH);
        centralPanel.add(inputPanel, BorderLayout.CENTER);

        mainPanel.add(centralPanel, BorderLayout.CENTER);








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
        mainPanel.setVisible(true);
    }

    public int getNumber() {
        return number;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }


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
