package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessoryTest {
    Accessory accessory;

    @BeforeEach
    public void accessoryTestSetup() {
        accessory = new Accessory("Bracelet", 1,2,3,4, 1);
    }

    @Test
    public void gettersTest() {
        assertEquals("Bracelet", accessory.getAccessoryName());
        assertEquals(1, accessory.getAddedStrength());
        assertEquals(2, accessory.getAddedDefence());
        assertEquals(3, accessory.getAddedAgility());
        assertEquals(4, accessory.getAddedIntelligence());
        assertEquals(1, accessory.getAccessoryId());
    }

}
