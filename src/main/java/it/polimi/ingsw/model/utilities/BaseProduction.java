package it.polimi.ingsw.model.utilities;

import java.util.ArrayList;

public class BaseProduction {

    private ArrayList<ResourceTag> inputResources;
    private ArrayList<ResourceTag> outputResources;

    public ArrayList<ResourceTag> getInputResources() {
        return inputResources;
    }

    public ArrayList<ResourceTag> getOutputResources() {
        return outputResources;
    }

    public void setOutputResources(ArrayList<ResourceTag> outputResources) {
        this.outputResources = outputResources;
    }

    public void setInputResources(ArrayList<ResourceTag> inputResources) {
        this.inputResources = inputResources;
    }
}
