package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.swing.*;
import java.awt.*;


public class ResourceMarket extends JPanel {
    ResourceType[][] resourceBoard;
    ResourceType extraMarble;

    public ResourceMarket(ResourceType[][] resourceBoard, ResourceType extraMarble){
        this.resourceBoard = resourceBoard;
        this.extraMarble = extraMarble;

    }

    @Override
    public void paint(Graphics g) {
        int x = 10;
        int y = 0;
        DrawFirstLine(g, x, y);
        y +=this.getHeight()/5-25;
        DrawVerticalArrow(g,x,y);
        y +=this.getHeight()/5;
        for (int i = 0; i < 3; i++) {
            DrawMarketLine(g, i, x, y);
            y += this.getHeight()/5+10;
        }
    }


    private void DrawMarketLine(Graphics g, int index, int x, int y){
        Color purple = new Color (128,0,128);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < 4; i++) {
            switch (resourceBoard[index][i]){
                case COIN:
                    g2d.setPaint(Color.yellow);
                    break;
                case SHIELD:
                    g2d.setPaint(Color.blue);
                    break;
                case SERVANT:
                    g2d.setPaint(purple);
                    break;
                case STONE:
                    g2d.setPaint(Color.gray);
                    break;
                case FAITH:
                    g2d.setPaint(Color.red);
                    break;
                case UNDEFINED:
                    g2d.setPaint(Color.white);
                    break;
            }
            g2d.fillOval(x,y,this.getWidth()/6,this.getHeight()/5);
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x,y,this.getWidth()/6,this.getHeight()/5);
            x += (this.getWidth()/6+10);
        }
        DrawHorizontalArrow(g,x, y,index+4);

    }


    private void DrawFirstLine(Graphics g, int x, int y){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Monaco", Font.PLAIN, this.getHeight()/5));
        for (int i = 0; i < 4; i++) {
            g2d.drawString(String.valueOf(i),x + this.getWidth()/24, y+this.getHeight()/5-10);
            x+=this.getWidth()/6+10;
        }

        Color purple = new Color (128,0,128);
        for (int i = 0; i < 4; i++) {
            switch (extraMarble) {
                case COIN:
                    g2d.setPaint(Color.yellow);
                    break;
                case SHIELD:
                    g2d.setPaint(Color.blue);
                    break;
                case SERVANT:
                    g2d.setPaint(purple);
                    break;
                case STONE:
                    g2d.setPaint(Color.gray);
                    break;
                case FAITH:
                    g2d.setPaint(Color.red);
                    break;
                case UNDEFINED:
                    g2d.setPaint(Color.white);
                    break;
            }
            g2d.fillOval(x+20, y+20, this.getWidth() / 6, this.getHeight() / 5);
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x+20, y+20, this.getWidth() / 6, this.getHeight() / 5);
        }
    }

    private  void DrawHorizontalArrow(Graphics g, int x, int y, int number){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.RED);
        g2d.drawLine(x+10,y+this.getHeight()/10,x+this.getWidth()/6,y+this.getHeight()/10);
        int[] x_coordinates = {x,x+10,x+10};
        int[] y_coordinates = {y+this.getHeight()/10,y+this.getHeight()/10+10,y+this.getHeight()/10-10};
        g2d.fillPolygon(x_coordinates,y_coordinates, 3);

        g2d.setFont(new Font("Monaco", Font.PLAIN, this.getHeight()/5));
        g2d.drawString(String.valueOf(number),x+this.getWidth()/6+1, y+this.getHeight()/5-10);
    }

    private void DrawVerticalArrow(Graphics g, int x, int y){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.RED);
        for (int i = 0; i < 4; i++) {
            g2d.drawLine(x+this.getWidth()/12,y+20,x+this.getWidth()/12,y+this.getHeight()/5-15);
            int[] x_coordinates = {x+this.getWidth()/12-10,x+this.getWidth()/12,x+this.getWidth()/12+10};
            int[] y_coordinates = {y+this.getHeight()/5-15,y+this.getHeight()/5-5,y+this.getHeight()/5-15};
            g2d.fillPolygon(x_coordinates,y_coordinates, 3);
            x += this.getWidth()/6+10;
        }
    }

}
