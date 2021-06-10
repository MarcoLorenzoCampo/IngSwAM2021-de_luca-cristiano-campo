package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AvailableLeaderPanel extends JPanel {
    private ArrayList<LeaderCard> available;
    private ArrayList<JButton> buttons;
    private ArrayList<LeaderPanel> drawn_cards;

    AvailableLeaderPanel(){
        available = new ArrayList<>();

        buttons = new ArrayList<>();
        buttons.add(new JButton("ACTIVATE 0"));
        buttons.add(new JButton("DISCARD 0"));
        buttons.add(new JButton("ACTIVATE 1"));
        buttons.add(new JButton("DISCARD 1"));

        drawn_cards = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        if(!available.isEmpty()){

        }
    }

    private void drawBackground(Graphics g) {
    }

    public void updateAvailableLeaderPanel(ArrayList<LeaderCard> available){
        this.removeAll();

        this.available = available;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public ArrayList<LeaderCard> getAvailable() {
        return available;
    }
}
