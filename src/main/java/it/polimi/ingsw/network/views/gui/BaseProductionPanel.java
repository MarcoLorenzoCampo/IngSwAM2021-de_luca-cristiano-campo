package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BaseProductionPanel extends JPanel {
    private JButton[] buttons;
    private String path;
    private String[] resources;
    private ArrayList<ResourceType> production;

    public BaseProductionPanel(){
        buttons = new JButton[5];
        path = "./punchboard/empty_production.png";
        resources = new String[]{
                "./punchboard/coin.png",
                "./punchboard/shield.png",
                "./punchboard/stone.png",
                "./punchboard/servant.png"
        };
        production = new ArrayList<>();

        buttons[0] = new JButton("COIN");
        buttons[0].addActionListener(e -> {
            production.add(ResourceType.COIN);
            if(production.size()==3){
                for (int i = 0; i < 3; i++) {
                    buttons[i].setEnabled(false);
                }
            }
            this.repaint();
        });

        buttons[1] = new JButton("SHIELD");
        buttons[1].addActionListener(e -> {
            production.add(ResourceType.SHIELD);
            if(production.size()==3){
                for (int i = 0; i < 3; i++) {
                    buttons[i].setEnabled(false);
                }
            }
            this.repaint();
        });

        buttons[2] = new JButton("STONE");
        buttons[2].addActionListener(e -> {
            production.add(ResourceType.STONE);
            if(production.size()==3){
                for (int i = 0; i < 3; i++) {
                    buttons[i].setEnabled(false);
                }
            }
            this.repaint();
        });

        buttons[3] = new JButton("SERVANT");
        buttons[3].addActionListener(e -> {
            production.add(ResourceType.SERVANT);
            if(production.size()==3){
                for (int i = 0; i < 3; i++) {
                    buttons[i].setEnabled(false);
                }
            }
            this.repaint();
        });

        buttons[4] = new JButton("SUBMIT");

        for (JButton iterator: buttons) {
            iterator.setFocusable(false);
        }

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(146, 123, 91));
        JPanel upper = new JPanel();
        upper.setOpaque(false);
        upper.setLayout(new FlowLayout());
        for (int i = 0; i < 4; i++) {
            upper.add(buttons[i]);
        }

        JPanel lower = new JPanel();
        lower.setOpaque(false);
        lower.setLayout(new FlowLayout());
        lower.add(buttons[4]);

        this.add(upper, BorderLayout.NORTH);
        this.add(lower, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        printProduction(g);
    }

    private void printProduction(Graphics g) {
        int width = this.getWidth()/18;
        int height = this.getHeight()/10;
        String item = "";
        for (ResourceType iterator: production) {
            ClassLoader cl = this.getClass().getClassLoader();

            switch(iterator){

                case COIN:
                    item = resources[0];
                    break;
                case SHIELD:
                    item = resources[1];
                    break;
                case SERVANT:
                    item = resources[3];
                    break;
                case STONE:
                    item = resources[2];
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
            if(production.indexOf(iterator)==0) g.drawImage(img, 3*width,2*height, 2*width,2*height, null);
            else if(production.indexOf(iterator)==1) g.drawImage(img, 3*width,5*height, 2*width,2*height, null);
            else if(production.indexOf(iterator)==2) g.drawImage(img, 11*width,4*height,2*width,2*height, null);
        }
    }

    private void paintBackground(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    public void resetProduction(){
        production.clear();
        for (JButton iterator:buttons) {
            iterator.setEnabled(true);
        }
    }

    public ArrayList<ResourceType> getProduction() {
        return production;
    }

    public JButton getSubmit(){
        return buttons[4];
    }
}
