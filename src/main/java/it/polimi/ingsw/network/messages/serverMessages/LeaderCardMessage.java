package it.polimi.ingsw.network.messages.serverMessages;


import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderCardMessage extends Message {

    private static final long serialVersionUID = -5649155565123346151L;


    private final int size;
    private final ArrayList<EffectType> effects;
    private final ArrayList<ResourceType> resources;
    private final ArrayList<Integer> victoryPoints;
    private final HashMap<Integer, ResourceType> storage;
    private final HashMap<Integer, Integer[]> others;

    public LeaderCardMessage (List<LeaderCard> inModel){

        super.setMessageType(PossibleMessages.AVAILABLE_LEADERS);
        super.setSenderUsername("SERVER_MESSAGE");

        this.size = inModel.size();
        this.storage = new HashMap<>();
        this.others = new HashMap<>();
        this.effects = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.victoryPoints = new ArrayList<>();

        for (LeaderCard iterator : inModel) {
            effects.add(iterator.getEffectType());
            resources.add(iterator.getResource());
            victoryPoints.add(iterator.getVictoryPoints());

            if(iterator.getEffectType().equals(EffectType.EXTRA_INVENTORY)){
                storage.put(inModel.indexOf(iterator), iterator.getRequirementsResource()[0].getType());
            } else {
                Integer[] values = new Integer[4];
                if(iterator.getRequirementsDevCards().length == 1){
                    values[iterator.getRequirementsDevCards()[0].getColor().ordinal()] = 1;
                } else {
                    for (DevelopmentTag innerIterator  : iterator.getRequirementsDevCards()) {
                        values[innerIterator.getColor().ordinal()] = innerIterator.getQuantity();
                    }
                }
                others.put(inModel.indexOf(iterator), values);
            }
        }
    }

    public int getSize() { return size; }

    public ArrayList<EffectType> getEffects() {
        return effects;
    }

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    public ArrayList<Integer> getVictoryPoints() {
        return victoryPoints;
    }

    public HashMap<Integer, ResourceType> getStorage() {
        return storage;
    }

    public HashMap<Integer, Integer[]> getOthers() {
        return others;
    }

}
