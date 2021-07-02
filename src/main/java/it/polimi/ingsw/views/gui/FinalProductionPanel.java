package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FinalProductionPanel extends JPanel {
    private HashMap<ResourceType, Integer> input;
    private HashMap<ResourceType, Integer> output;
    private final String[] resources;
    JButton clear;

    FinalProductionPanel(){
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        input = new HashMap<>();
        output = new HashMap<>();
        JPanel button = new JPanel();
        button.setOpaque(false);
        button.setLayout(new FlowLayout());
        
        resources = new String[]{
                "punchboard/coin.png",
                "punchboard/shield.png",
                "punchboard/servant.png",
                "punchboard/stone.png",
                "punchboard/faith_marker.png"};

        clear = new JButton("CLEAR");
        clear.setFocusable(false);
        clear.addActionListener(e -> {
            input.clear();
            output.clear();
            this.repaint();
        });

        button.add(clear);
        this.add(button, BorderLayout.SOUTH);
    }

    public void updateFinalProductionPanel(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output){
        this.input = input;
        this.output = output;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        if(!input.isEmpty() && !output.isEmpty()){
            paintProduction(g);
        }
    }

    private void paintProduction(Graphics g) {
        int width = this.getWidth()/20;
        int height = this.getHeight()/10;
        paintInput(g, width, height);
        paintOutput(g, width, height);
    }

    private void paintOutput(Graphics g1D, int width, int height) {
        Graphics2D g = (Graphics2D) g1D;
        int x = 11*width;
        int y = height;
        ClassLoader cl = this.getClass().getClassLoader();
        String item = " ";
        for (Map.Entry<ResourceType, Integer> iterator:output.entrySet()) {
            switch (iterator.getKey()) {
                case COIN:
                    item = resources[0];
                    break;
                case SHIELD:
                    item = resources[1];
                    break;
                case SERVANT:
                    item = resources[2];
                    break;
                case STONE:
                    item = resources[3];
                    break;
                case FAITH:
                    item = resources[4];
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
            g.drawImage(img, x,y, width,height, null);
            g.setStroke(new BasicStroke(height));
            g.drawString("x " + iterator.getValue(), x+3*width/2, y+height);
            y+= 3*height/2;
        }

    }

    private void paintInput(Graphics g1D, int width, int height) {
        Graphics2D g = (Graphics2D) g1D;
        int x = 4*width;
        int y = 3*height/2;
        ClassLoader cl = this.getClass().getClassLoader();
        String item = " ";
        ArrayList<ResourceType> temp = new ArrayList<>();

        for (Map.Entry<ResourceType, Integer> iterator:input.entrySet()) {
            temp.add(iterator.getKey());
        }
        Collections.sort(temp);

        for (ResourceType iterator : temp ) {

            switch (iterator) {
                case COIN:
                    item = resources[0];
                    break;
                case SHIELD:
                    item = resources[1];
                    break;
                case SERVANT:
                    item = resources[2];
                    break;
                case STONE:
                    item = resources[3];
                    break;
                case FAITH:
                    item = resources[4];
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
            g.drawImage(img, x,y, width,height, null);
            g.setStroke(new BasicStroke(height));
            g.drawString("x " + input.get(iterator).toString(), x+3*width/2, y+height);
            y+= 3*height/2;

        }

    }

    private void paintBackground(Graphics g) {
        int width = this.getWidth()/10;
        int height = this.getHeight()/5;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("punchboard/empty_production.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, width/2,0, 9*width,5*height, null);
    }
}
