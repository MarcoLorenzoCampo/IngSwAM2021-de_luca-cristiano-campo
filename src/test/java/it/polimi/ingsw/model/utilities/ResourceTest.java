package it.polimi.ingsw.model.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    Resource materialResource;
    Resource faithResource;

    @BeforeEach
    void setUp() {
        materialResource = new MaterialResource("COIN");
        faithResource = new FaithResource();
    }

    @Test
    void deposit() {

    }

    @Test
    void setResourceType() {

        //Act
        materialResource.setResourceType("SHIELD");

        //Assert

    }

    @Test
    void getResourceType() {
    }
}