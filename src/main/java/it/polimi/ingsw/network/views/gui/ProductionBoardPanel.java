package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionBoardPanel extends JPanel {

    private HashMap<Integer, ProductionCard> productionBoard;
    private ArrayList<LeaderCard> extra_production;
    private String production_path = "./front/production_";
    private String extra_path = "./front/leader_production_";
    private HashMap<Integer, ArrayList<ProductionCard>> previous_productions;
    private JButton[] buttons = new JButton[7];


    public ProductionBoardPanel(){
        productionBoard = new HashMap<>();
        extra_production = new ArrayList<>();
        previous_productions = new HashMap<>();

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(1,6, 0,50));
        button_panel.setOpaque(false);

        JPanel execute_panel = new JPanel();
        execute_panel.setLayout(new FlowLayout());
        execute_panel.setOpaque(false);



        buttons[0] = new JButton("BASE PRODUCTION");
        buttons[0].setFocusable(false);

        buttons[1] = new JButton("PRODUCTION 0");
        buttons[1].setFocusable(false);

        buttons[2] = new JButton("PRODUCTION 1");
        buttons[2].setFocusable(false);

        buttons[3] = new JButton("PRODUCTION 2");
        buttons[3].setFocusable(false);

        buttons[4] = new JButton("EXTRA PRODUCTION 0");
        buttons[4].setFocusable(false);


        buttons[5] = new JButton("EXTRA PRODUCTION 1");
        buttons[5].setFocusable(false);


        buttons[6] = new JButton("EXECUTE");
        buttons[6].setFocusable(false);


        for (int i = 0; i < 6; i++) {
            button_panel.add(buttons[i]);
        }
        execute_panel.add(buttons[6]);

        this.add(button_panel, BorderLayout.NORTH);
        this.add(execute_panel, BorderLayout.SOUTH);
    }

    public ProductionBoardPanel(HashMap<Integer, ProductionCard> productionBoard){
        this.productionBoard = productionBoard;
        this.extra_production = new ArrayList<>();
        this.previous_productions = new HashMap<>();
        this.setLayout(new BorderLayout());

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(1,6, 0,50));
        button_panel.setOpaque(false);

        JPanel execute_panel = new JPanel();
        execute_panel.setLayout(new FlowLayout());
        execute_panel.setOpaque(false);



        buttons[0] = new JButton("BASE PRODUCTION");
        buttons[0].setFocusable(false);

        buttons[1] = new JButton("PRODUCTION 0");
        buttons[1].setFocusable(false);

        buttons[2] = new JButton("PRODUCTION 1");
        buttons[2].setFocusable(false);

        buttons[3] = new JButton("PRODUCTION 2");
        buttons[3].setFocusable(false);

        buttons[4] = new JButton("EXTRA PRODUCTION 0");
        buttons[4].setFocusable(false);


        buttons[5] = new JButton("EXTRA PRODUCTION 1");
        buttons[5].setFocusable(false);


        buttons[6] = new JButton("EXECUTE");
        buttons[6].setFocusable(false);


        for (int i = 0; i < 6; i++) {
            button_panel.add(buttons[i]);
        }
        execute_panel.add(buttons[6]);

        this.add(button_panel, BorderLayout.NORTH);
        this.add(execute_panel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/12;
        int height = this.getHeight()/5;
        paintBackground(g);
        if(!previous_productions.isEmpty()){
            paintPreviousProductions(g, width, height);
        }
        paintProductions(g, width, height);
        if(!extra_production.isEmpty()){
            paintExtraProductions(g, width, height);
        }
    }

    private void paintPreviousProductions(Graphics g, int width, int height) {
        int x;
        int y;
        ClassLoader cl = this.getClass().getClassLoader();

        for (Map.Entry<Integer, ArrayList<ProductionCard>> iterator : previous_productions.entrySet()) {
            for (ProductionCard inner_iterator: iterator.getValue()) {
                String item = production_path+String.valueOf(inner_iterator.getId());
                if(inner_iterator.getId()<16) y=29*height/24;
                else if (inner_iterator.getId()<32) y=height;
                else y= height/2;

                if(iterator.getKey()==0) x = 8*width/5;
                else if (iterator.getKey()==1) x = 18*width/5;
                else x = 28*width/5;

                InputStream url = cl.getResourceAsStream(item+".png");
                BufferedImage img= null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                g.drawImage(img, x,y, 7*width/4,21*height/8, null);
            }
        }
    }


    private void paintExtraProductions(Graphics g, int width, int height) {
        int x = 39 * width / 5;
        int y = 29 * height / 24;
        ArrayList<LeaderCard> productions =
                (ArrayList<LeaderCard>) extra_production.stream()
                        .filter(leaderCard -> leaderCard.getEffectType().equals(EffectType.EXTRA_PRODUCTION))
                        .filter(LeaderCard::isActive)
                        .collect(Collectors.toList());
        ClassLoader cl = this.getClass().getClassLoader();
        for (LeaderCard iterator : productions) {
            String item = extra_path;
            switch (iterator.getResource()) {

                case COIN:
                    item = item + "coin";
                    break;
                case SHIELD:
                    item = item + "shield";
                    break;
                case SERVANT:
                    item = item + "servant";
                    break;
                case STONE:
                    item = item + "stone";
                    break;

            }
            InputStream url = cl.getResourceAsStream(item + ".png");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x, y, 7 * width / 4, 21 * height / 8, null);
            x += 2 * width;
        }

    }

    private void paintProductions(Graphics g, int width, int height) {
        int x = 8*width/5;
        int y;
        ClassLoader cl = this.getClass().getClassLoader();

        for (Map.Entry<Integer, ProductionCard> iterator : productionBoard.entrySet()) {
            String item = production_path+String.valueOf(iterator.getValue().getId());
            if(iterator.getValue().getId()<16) y=29*height/24;
            else if (iterator.getValue().getId()<32) y=height;
            else y= 19*height/24;
            InputStream url = cl.getResourceAsStream(item+".png");
            BufferedImage img= null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x,y, 7*width/4,21*height/8, null);
            x+= 2*width;
        }

    }

    private void paintBackground(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/production_board.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    public void updateProductionBoardPanel(HashMap<Integer, ProductionCard> productionBoard,
                                           ArrayList<LeaderCard> extra_production){
        for (Map.Entry<Integer, ProductionCard> iterator : this.productionBoard.entrySet()) {
            if(previous_productions.containsKey(iterator.getKey())){
                previous_productions.get(iterator.getKey()).add(iterator.getValue());
            }
            else{
                ArrayList<ProductionCard> cards = new ArrayList<>();
                cards.add(iterator.getValue());
                previous_productions.put(iterator.getKey(),cards);
            }
        }
        this.productionBoard = productionBoard;
        this.extra_production = extra_production;
        this.repaint();
    }

    public JButton[] getButtons() {
        return buttons;
    }
}
