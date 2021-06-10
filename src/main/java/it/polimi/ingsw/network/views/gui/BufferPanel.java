package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BufferPanel extends JPanel {

    private ArrayList<ResourceType> buffer;
    private JButton[] deposit_buttons;
    private JButton[] change_buttons;
    private String[] paths = {
            "./punchboard/coin.png",
            "./punchboard/shield.png",
            "./punchboard/servant.png",
            "./punchboard/stone.png",
            "./punchboard/marble_white.png",
    };

    public BufferPanel(){
        buffer = new ArrayList<>();
        deposit_buttons = new JButton[4];
        change_buttons = new JButton[4];


        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(2,1));
        button_panel.setOpaque(false);

        JPanel deposit_line = new JPanel();
        deposit_line.setLayout(new FlowLayout());
        deposit_line.setOpaque(false);

        JPanel change_line = new JPanel();
        change_line.setLayout(new FlowLayout());
        change_line.setOpaque(false);

        for (int i = 0; i < deposit_buttons.length; i++) {
            deposit_buttons[i] = new JButton("DEPOSIT "+String.valueOf(i));
            deposit_buttons[i].setFocusable(false);
            deposit_buttons[i].setVisible(true);
            deposit_buttons[i].setEnabled(false);
            deposit_line.add(deposit_buttons[i]);
        }

        for (int i = 0; i < change_buttons.length; i++) {
            change_buttons[i] = new JButton("CHANGE "+String.valueOf(i));
            change_buttons[i].setFocusable(false);
            change_buttons[i].setVisible(true);
            change_buttons[i].setEnabled(false);

            change_line.add(change_buttons[i]);
        }

        button_panel.add(deposit_line);
        button_panel.add(change_line);
        this.add(button_panel, BorderLayout.SOUTH);


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!buffer.isEmpty()){
            DrawBuffer(g);
        }
    }

    private void DrawBuffer(Graphics g) {
        int width = this.getWidth()/5;
        int height = this.getHeight()/13;
        int x = width/2;
        int y = height;
        ClassLoader cl = this.getClass().getClassLoader();
        String item = "";
        for (ResourceType iterator:buffer) {
            switch (iterator){

                case COIN:
                    item = paths[0];
                    break;
                case SHIELD:
                    item = paths[1];
                    break;
                case SERVANT:
                    item = paths[2];
                    break;
                case STONE:
                    item = paths[3];
                    break;
                case UNDEFINED:
                    item = paths[4];
                    break;
            }
            InputStream url = cl.getResourceAsStream(item);
            BufferedImage img= null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x,y, width/2,2*height, null);
            x+=width;
        }

    }

    public void updateBufferPanel(ArrayList<ResourceType> buffer){
        this.buffer = buffer;
        for (JButton iterator : deposit_buttons) {
            iterator.setEnabled(false);
        }

        for (JButton iterator : change_buttons) {
            iterator.setEnabled(false);
        }
        if(buffer.isEmpty()){
            deposit_buttons[0].setEnabled(true);
        }
        else {
            for (int i = 0; i < buffer.size(); i++) {
                if (buffer.get(i).equals(ResourceType.UNDEFINED)) change_buttons[i].setEnabled(true);
                else deposit_buttons[i].setEnabled(true);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public JButton[] getDeposit_buttons() {
        return deposit_buttons;
    }

    public JButton[] getChange_buttons() {
        return change_buttons;
    }
}
