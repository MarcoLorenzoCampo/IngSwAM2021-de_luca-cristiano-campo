package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ExtraProductionPanel extends JPanel {
    private JButton[] buttons;
    private String path;
    private final String[] resources;
    private ResourceType chosen;

    ExtraProductionPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground((new Color(146, 123, 91)));
        buttons = new JButton[6];
        path = "";
        buttons = new JButton[6];
        resources = new String[]{
                "punchboard/coin.png",
                "punchboard/shield.png",
                "punchboard/stone.png",
                "punchboard/servant.png"
        };


        buttons[0] = new JButton("COIN");
        buttons[0].addActionListener(e -> {
            chosen=ResourceType.COIN;
            for (int i = 0; i < 4; i++) {
                buttons[i].setEnabled(false);
            }
            buttons[4].setEnabled(true);
            this.repaint();
        });

        buttons[1] = new JButton("SHIELD");
        buttons[1].addActionListener(e -> {
            chosen=ResourceType.SHIELD;
            for (int i = 0; i < 4; i++) {
                buttons[i].setEnabled(false);
            }
            buttons[4].setEnabled(true);
            this.repaint();
        });

        buttons[2] = new JButton("STONE");
        buttons[2].addActionListener(e -> {
            chosen=ResourceType.STONE;
            for (int i = 0; i < 4; i++) {
                buttons[i].setEnabled(false);
            }
            buttons[4].setEnabled(true);
            this.repaint();
        });

        buttons[3] = new JButton("SERVANT");
        buttons[3].addActionListener(e -> {
            chosen=ResourceType.SERVANT;
            for (int i = 0; i < 4; i++) {
                buttons[i].setEnabled(false);
            }
            buttons[4].setEnabled(true);
            this.repaint();
        });

        buttons[4] = new JButton("SUBMIT");
        buttons[4].setEnabled(false);

        buttons[5] = new JButton("DELETE");
        buttons[5].addActionListener(e -> {
            chosen = null;
            for (int i = 0; i < 4; i++) {
                buttons[i].setEnabled(true);
            }
            buttons[4].setEnabled(false);
            this.repaint();
        });

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
        lower.add(buttons[5]);

        this.add(upper, BorderLayout.NORTH);
        this.add(lower, BorderLayout.SOUTH);
    }

    public JButton getSubmit(){
        return buttons[4];
    }

    public void updateExtraProductionPanel(LeaderCard extraprodleader){
        if(extraprodleader != null) {
            switch (extraprodleader.getResource()) {
                case COIN:
                    path = "front/reduced_leader_production_coin.jpg";
                    break;
                case SHIELD:
                    path = "front/reduced_leader_production_shield.jpg";
                    break;
                case SERVANT:
                    path = "front/reduced_leader_production_servant.jpg";
                    break;
                case STONE:
                    path = "front/reduced_leader_production_stone.jpg";
                    break;
            }
            for (JButton iterator: buttons) {
                iterator.setEnabled(true);
            }
        }
        else{
            for (JButton iterator: buttons) {
                iterator.setEnabled(false);
            }
        }
        this.repaint();
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/15;
        int height = this.getHeight()/6;

        if(!path.isEmpty()){
            drawCard(g, width, height);
        }
        if(chosen != null){
            drawChosen(g, width, height);
        }
    }





    private void drawChosen(Graphics g, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        String item = " ";
        switch(chosen){
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
        g.drawImage(img, 15*width/2,2*height, 3*width/2,3*height/2, null);
    }

    private void drawCard(Graphics g, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 5*width/2,height, 10*width,4*height, null);
    }

    public void clearSelection(){
        chosen = null;
    }

    public ResourceType getChosen() {
        return chosen;
    }
}
