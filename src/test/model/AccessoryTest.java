package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessoryTest {
    Accessory accessory;

    @BeforeEach
    public void accessoryTestSetup() {
        accessory = new Accessory("Bracelet", 1,2,3,4);
    }

    @Test
    public void getAccessoryNameTest() {
        assertEquals("Bracelet", accessory.getAccessoryName());
    }

    @Test
    public void getAddedStrengthTest() {
        assertEquals(1, accessory.getAddedStrength());
    }

    @Test
    public void getAddedDefenceTest() {
        assertEquals(2, accessory.getAddedDefence());
    }

    @Test
    public void getAddedAgilityTest() {
        assertEquals(3, accessory.getAddedAgility());
    }

    @Test
    public void getAddedIntelligenceTest() {
        assertEquals(4, accessory.getAddedIntelligence());
    }


}
