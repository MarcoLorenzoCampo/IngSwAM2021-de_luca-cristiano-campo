package it.polimi.ingsw.network.views.gui;


import javax.swing.*;

public class Executor {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Executor::createAndShowGUI);
    }
    private static void createAndShowGUI() {

        JFrame frame = new JFrame();

        frame.setContentPane(new LorenzoTokenPanel());
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.setSize(500,400);
        frame.setVisible(true);

        JButton update = new JButton("update");
        update.addActionListener(e -> {
        });
    }
}

