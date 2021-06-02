package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SetupLeaderPopUp extends JPanel {

    JButton submit_button;
    JCheckBox[] checkBoxes;

    public SetupLeaderPopUp(List<LeaderCard> available){

        this.setLayout(new BorderLayout());

        JPanel title = new JPanel();
        title.setLayout(new FlowLayout());
        JLabel text = new JLabel("CHOOSE TWO LEADERS TO DISCARD");
        title.add(text);

        JPanel submit = new JPanel();
        submit.setLayout(new FlowLayout());


        submit_button = new JButton("SUBMIT");
        submit_button.setFocusable(false);
        submit_button.setEnabled(false);


        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2, 4,10,0));
        LeaderPanel leader_0 = new LeaderPanel(available.get(0));
        center.add(leader_0);

        LeaderPanel leader_1 = new LeaderPanel(available.get(1));
        center.add(leader_1);

        LeaderPanel leader_2 = new LeaderPanel(available.get(2));
        center.add(leader_2);

        LeaderPanel leader_3 = new LeaderPanel(available.get(3));
        center.add(leader_3);

        checkBoxes = new JCheckBox[4];
        JCheckBox zero = new JCheckBox();
        zero.setFocusable(false);
        zero.setHorizontalAlignment(SwingConstants.CENTER);
        zero.addActionListener(e -> {
            int count = 0;
            for (JCheckBox iterator: checkBoxes) {
                if(iterator.isSelected()) count++;
            }
            if(count==2){
                for (JCheckBox iterator: checkBoxes) {
                    iterator.setEnabled(false);
                }
                submit_button.setEnabled(true);
            }
        });

        JCheckBox one = new JCheckBox();
        one.setFocusable(false);
        one.setHorizontalAlignment(SwingConstants.CENTER);
        one.addActionListener(e -> {
            int count = 0;
            for (JCheckBox iterator: checkBoxes) {
                if(iterator.isSelected()) count++;
            }
            if(count==2){
                for (JCheckBox iterator: checkBoxes) {
                    iterator.setEnabled(false);
                }
                submit_button.setEnabled(true);
            }
        });

        JCheckBox two = new JCheckBox();
        two.setFocusable(false);
        two.setHorizontalAlignment(SwingConstants.CENTER);
        two.addActionListener(e -> {
            int count = 0;
            for (JCheckBox iterator: checkBoxes) {
                if(iterator.isSelected()) count++;
            }
            if(count==2){
                for (JCheckBox iterator: checkBoxes) {
                    iterator.setEnabled(false);
                }
                submit_button.setEnabled(true);
            }
        });


        JCheckBox three = new JCheckBox();
        three.setFocusable(false);
        three.setHorizontalAlignment(SwingConstants.CENTER);
        three.addActionListener(e -> {
            int count = 0;
            for (JCheckBox iterator: checkBoxes) {
                if(iterator.isSelected()) count++;
            }
            if(count==2){
                for (JCheckBox iterator: checkBoxes) {
                    iterator.setEnabled(false);
                }
                submit_button.setEnabled(true);
            }
        });



        checkBoxes[0] = zero;
        checkBoxes[1] = one;
        checkBoxes[2] = two;
        checkBoxes[3] = three;

        JButton delete_button = new JButton("DELETE");
        delete_button.setFocusable(false);
        delete_button.addActionListener(e->{
            for (JCheckBox iterator: checkBoxes) {
                iterator.setEnabled(true);
                iterator.setSelected(false);
            }
            submit_button.setEnabled(false);
        });
        submit.add(delete_button);


        submit.add(submit_button);

        center.add(zero);
        center.add(one);
        center.add(two);
        center.add(three);



        this.add(title,BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(submit,BorderLayout.SOUTH);
    }

    public JButton getSubmit_button() {
        return submit_button;
    }

    public JCheckBox[] getCheckBoxes() {
        return checkBoxes;
    }

}
