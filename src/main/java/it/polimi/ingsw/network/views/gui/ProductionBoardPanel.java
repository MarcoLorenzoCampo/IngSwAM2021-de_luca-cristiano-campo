package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.RealPlayerBoard;

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
    private JButton[] buttons = new JButton[7];
    private JPanel button_panel;

    public ProductionBoardPanel(HashMap<Integer, ProductionCard> productionBoard){
        this.productionBoard = productionBoard;
        this.extra_production = new ArrayList<>();
        this.setLayout(new BorderLayout());

        button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());

        buttons[0] = new JButton("BASE PRODUCTION");
        buttons[0].setFocusable(false);
        buttons[0].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,this.getHeight()/5,this.getWidth()/24));

        buttons[1] = new JButton("PRODUCTION 0");
        buttons[1].setFocusable(false);
        buttons[1].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,0,this.getWidth()/24));

        buttons[2] = new JButton("PRODUCTION 1");
        buttons[2].setFocusable(false);
        buttons[2].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,this.getHeight()/5,this.getWidth()/24));

        buttons[3] = new JButton("PRODUCTION 2");
        buttons[3].setFocusable(false);
        buttons[3].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,0,this.getWidth()/24));

        buttons[4] = new JButton("EXTRA PRODUCTION 0");
        buttons[4].setFocusable(false);
        buttons[4].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,0,this.getWidth()/24));

        buttons[5] = new JButton("EXTRA PRODUCTION 1");
        buttons[5].setFocusable(false);
        buttons[5].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,0,this.getWidth()/24));

        buttons[6] = new JButton("EXECUTE");
        buttons[6].setFocusable(false);
        buttons[6].setBorder(BorderFactory.createEmptyBorder(0,this.getWidth()/24,0,this.getWidth()/24));

        for (JButton iterator : buttons) {
            button_panel.add(iterator);
        }

        button_panel.setOpaque(false);

        this.add(button_panel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/12;
        int height = this.getHeight()/5;
        paintBackground(g);
        paintProductions(g, width, height);
        if(!extra_production.isEmpty()){
            paintExtraProductions(g, width, height);
        }
        button_panel.setBorder(BorderFactory.createEmptyBorder(this.getHeight()/5, 0,0,0));
    }



    private void paintExtraProductions(Graphics g, int width, int height) {
        int x = 39*width/5;
        int y = 29*height/24;
        ArrayList<LeaderCard> productions =
                (ArrayList<LeaderCard>) extra_production.stream()
                .filter(leaderCard -> leaderCard.getEffectType().equals(EffectType.EXTRA_PRODUCTION))
                .filter(LeaderCard::isActive)
                .collect(Collectors.toList());
        ClassLoader cl = this.getClass().getClassLoader();
        for (LeaderCard iterator: productions) {
            String item = extra_path;
            switch (iterator.getResource()){

                case COIN:
                    item = item+"coin";
                    break;
                case SHIELD:
                    item = item+"shield";
                    break;
                case SERVANT:
                    item = item+"servant";
                    break;
                case STONE:
                    item = item+"stone";
                    break;

            }
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

    private void paintProductions(Graphics g, int width, int height) {
        int x = 8*width/5;
        int y = 29*height/24;
        ClassLoader cl = this.getClass().getClassLoader();

        for (Map.Entry<Integer, ProductionCard> iterator : productionBoard.entrySet()) {
            String item = production_path+String.valueOf(iterator.getValue().getId());
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
}
