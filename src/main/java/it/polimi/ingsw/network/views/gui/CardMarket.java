package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.market.ProductionCard;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardMarket extends JPanel {
    String[] images = new String[]{
            "production_0","production_1","production_2","production_3",
            "production_4", "production_5","production_6","production_7",
            "production_8","production_9", "production_10", "production_11"};
    JPanel[] row_1 = new JPanel[4];
    JPanel[] row_2 = new JPanel[4];
    JPanel[] row_3 = new JPanel[4];


    public CardMarket(List<ProductionCard> available){
        this.setLayout(new GridLayout(3,4,10,10));
        for (int i = 0; i < 4; i++) {
            row_1[i] = new JPanel();
            row_2[i] = new JPanel();
            row_3[i] = new JPanel();
        }
        
        createCardMarket(available);
        addRow(row_1);
        addRow(row_2);
        addRow(row_3);
    }

    private void createCardMarket(List<ProductionCard> available) {
        ArrayList<ProductionCard> one = (ArrayList<ProductionCard>) available.stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                .collect(Collectors.toList());
        ArrayList<ProductionCard> two= (ArrayList<ProductionCard>) available.stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.TWO))
                .collect(Collectors.toList());
        ArrayList<ProductionCard> three = (ArrayList<ProductionCard>) available.stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.THREE))
                .collect(Collectors.toList());

        for (ProductionCard iterator_one : one) {
            switch(iterator_one.getColor()){
                case GREEN:
                    fillSlot(available, row_1[0], iterator_one);
                    break;
                case BLUE:
                    fillSlot(available, row_1[1], iterator_one);
                    break;
                case YELLOW:
                    fillSlot(available, row_1[2], iterator_one);
                    break;
                case PURPLE:
                    fillSlot(available, row_1[3], iterator_one);
                    break;
            }
        }

        for (ProductionCard iterator_two: two) {
            switch (iterator_two.getColor()) {
                case GREEN:
                    fillSlot(available, row_2[0], iterator_two);
                    break;
                case BLUE:
                    fillSlot(available, row_2[1], iterator_two);
                    break;
                case YELLOW:
                    fillSlot(available, row_2[2], iterator_two);
                    break;
                case PURPLE:
                    fillSlot(available, row_2[3], iterator_two);
                    break;
            }
        }

        for (ProductionCard iterator_three : three) {
            switch(iterator_three.getColor()){
                case GREEN:
                    fillSlot(available, row_3[0], iterator_three);
                    break;
                case BLUE:
                    fillSlot(available, row_3[1], iterator_three);
                    break;
                case YELLOW:
                    fillSlot(available, row_3[2], iterator_three);
                    break;
                case PURPLE:
                    fillSlot(available, row_3[3], iterator_three);
                    break;
            }
        }

    }

    private void fillSlot(List<ProductionCard> available, JPanel slot, ProductionCard iterator) {
        slot.setLayout( new BorderLayout());
        JLabel label = new JLabel(String.valueOf(available.indexOf(iterator)));
        label.setFont(new Font("serif", Font.PLAIN, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        slot.add(label, BorderLayout.SOUTH);
        slot.add(new ProductionPanel(iterator.getId()),BorderLayout.CENTER);
    }

    private void addRow(JPanel[] row) {
        for (JPanel iterator: row) {
            this.add(iterator);
        }
    }

}
